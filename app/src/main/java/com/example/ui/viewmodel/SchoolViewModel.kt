package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.EducationLevelEntity
import com.example.data.model.FinanceRecordEntity
import com.example.data.model.SchoolEntity
import com.example.data.remote.AuthResult
import com.example.data.repository.SchoolRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class UiMessage {
  data class Success(val text: String) : UiMessage()
  data class Error(val text: String) : UiMessage()
  data class Info(val text: String) : UiMessage()
}

class SchoolViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: SchoolRepository

  init {
    val database = AppDatabase.getDatabase(application)
    repository = SchoolRepository(database.schoolDao())
  }

  // Active User / School Session
  val activeSchoolId = MutableStateFlow<String?>(null)
  val activeEmail = MutableStateFlow<String?>(null)

  // Auth & Screen States
  val isLoading = MutableStateFlow(false)

  // Resend OTP Countdown (seconds)
  val resendCountdown = MutableStateFlow(0)
  private var timerJob: Job? = null

  // UI Toast/Snackbar Message Stream
  private val _uiMessage = MutableSharedFlow<UiMessage>()
  val uiMessage: SharedFlow<UiMessage> = _uiMessage.asSharedFlow()

  // School Profile reactive state
  val schoolProfile: StateFlow<SchoolEntity?> = activeSchoolId
    .flatMapLatest { id ->
      if (id != null) repository.getSchoolProfile(id) else flowOf(null)
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

  // Education Levels reactive state
  val educationLevels: StateFlow<List<EducationLevelEntity>> = activeSchoolId
    .flatMapLatest { id ->
      if (id != null) repository.getEducationLevels(id) else flowOf(emptyList())
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Finance Records reactive state
  val financeRecords: StateFlow<List<FinanceRecordEntity>> = activeSchoolId
    .flatMapLatest { id ->
      if (id != null) repository.getFinanceRecords(id) else flowOf(emptyList())
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  /**
   * Register School Account
   */
  fun registerSchool(
    adminName: String,
    adminEmail: String,
    adminPhone: String,
    pass: String,
    confirmPass: String,
    schoolName: String,
    institutionEmail: String,
    institutionPhone: String,
    wilaya: String,
    moughataa: String,
    commune: String,
    address: String,
    onSuccessNavToVerify: () -> Unit
  ) {
    if (adminName.isBlank() || adminEmail.isBlank() || pass.isBlank() || schoolName.isBlank()) {
      viewModelScope.launch { _uiMessage.emit(UiMessage.Error("يرجى ملء جميع الحقول المطلوبة.")) }
      return
    }

    if (pass != confirmPass) {
      viewModelScope.launch { _uiMessage.emit(UiMessage.Error("كلمة المرور وتأكيدها غير متطابقين.")) }
      return
    }

    if (pass.length < 6) {
      viewModelScope.launch { _uiMessage.emit(UiMessage.Error("كلمة المرور يجب أن تتكون من 6 أحرف على الأقل.")) }
      return
    }

    viewModelScope.launch {
      isLoading.value = true
      val result = repository.registerSchool(
        adminName = adminName,
        adminEmail = adminEmail,
        adminPhone = adminPhone,
        pass = pass,
        schoolName = schoolName,
        institutionEmail = institutionEmail,
        institutionPhone = institutionPhone,
        wilaya = wilaya,
        moughataa = moughataa,
        commune = commune,
        address = address
      )
      isLoading.value = false

      when (result) {
        is AuthResult.Success -> {
          activeSchoolId.value = result.userId
          activeEmail.value = adminEmail
          startResendTimer(60)
          _uiMessage.emit(UiMessage.Success(result.message ?: "تم إنشاء الحساب بنجاح."))
          onSuccessNavToVerify()
        }
        is AuthResult.Error -> {
          _uiMessage.emit(UiMessage.Error(result.message))
        }
      }
    }
  }

  /**
   * Verify 6-digit Email OTP
   */
  fun verifyEmailOtp(
    email: String,
    otpCode: String,
    onSuccessNavToDashboard: () -> Unit
  ) {
    if (otpCode.length != 6) {
      viewModelScope.launch { _uiMessage.emit(UiMessage.Error("يرجى إدخال الرمز المكون من 6 أرقام كاملًا.")) }
      return
    }

    viewModelScope.launch {
      isLoading.value = true
      val result = repository.verifyEmailOtp(email, otpCode)
      isLoading.value = false

      when (result) {
        is AuthResult.Success -> {
          activeSchoolId.value = result.userId
          activeEmail.value = email
          _uiMessage.emit(UiMessage.Success(result.message ?: "تم تأكيد بريدك الإلكتروني بنجاح."))
          onSuccessNavToDashboard()
        }
        is AuthResult.Error -> {
          _uiMessage.emit(UiMessage.Error(result.message))
        }
      }
    }
  }

  /**
   * Resend Email OTP Code
   */
  fun resendEmailOtp(email: String) {
    if (resendCountdown.value > 0) return

    viewModelScope.launch {
      isLoading.value = true
      val result = repository.resendEmailOtp(email)
      isLoading.value = false

      when (result) {
        is AuthResult.Success -> {
          startResendTimer(60)
          _uiMessage.emit(UiMessage.Success(result.message ?: "تمت إعادة إرسال رمز التحقق."))
        }
        is AuthResult.Error -> {
          _uiMessage.emit(UiMessage.Error(result.message))
        }
      }
    }
  }

  /**
   * Login with Email & Password
   */
  fun loginSchool(
    email: String,
    pass: String,
    onSuccessNav: (isVerified: Boolean) -> Unit
  ) {
    if (email.isBlank() || pass.isBlank()) {
      viewModelScope.launch { _uiMessage.emit(UiMessage.Error("يرجى إدخال البريد الإلكتروني وكلمة المرور.")) }
      return
    }

    viewModelScope.launch {
      isLoading.value = true
      val result = repository.loginSchool(email, pass)
      isLoading.value = false

      when (result) {
        is AuthResult.Success -> {
          activeSchoolId.value = result.userId
          activeEmail.value = email
          _uiMessage.emit(UiMessage.Success("تم تسجيل الدخول بنجاح."))
          onSuccessNav(result.isVerified)
        }
        is AuthResult.Error -> {
          _uiMessage.emit(UiMessage.Error(result.message))
        }
      }
    }
  }

  /**
   * Request Password Reset
   */
  fun requestPasswordReset(email: String) {
    if (email.isBlank()) {
      viewModelScope.launch { _uiMessage.emit(UiMessage.Error("يرجى إدخال البريد الإلكتروني.")) }
      return
    }

    viewModelScope.launch {
      isLoading.value = true
      val result = repository.requestPasswordReset(email)
      isLoading.value = false

      when (result) {
        is AuthResult.Success -> {
          _uiMessage.emit(UiMessage.Success(result.message ?: "تم إرسال تعليمات إعادة تعيين كلمة المرور."))
        }
        is AuthResult.Error -> {
          _uiMessage.emit(UiMessage.Error(result.message))
        }
      }
    }
  }

  /**
   * Add Real Finance Record (MRU)
   */
  fun addFinanceRecord(title: String, amountStr: String, type: String) {
    val schoolId = activeSchoolId.value ?: return
    val amount = amountStr.toDoubleOrNull()
    if (title.isBlank() || amount == null || amount <= 0) {
      viewModelScope.launch { _uiMessage.emit(UiMessage.Error("يرجى إدخال عنوان ومبلغ مالي صحيح.")) }
      return
    }

    viewModelScope.launch {
      repository.addFinanceRecord(
        FinanceRecordEntity(
          schoolId = schoolId,
          title = title,
          amountMru = amount,
          type = type,
          date = "2026-08-12"
        )
      )
      _uiMessage.emit(UiMessage.Success("تم تسجيل العملية المالية بنجاح."))
    }
  }

  /**
   * Logout
   */
  fun logout(onNavToHome: () -> Unit) {
    activeSchoolId.value = null
    activeEmail.value = null
    viewModelScope.launch {
      _uiMessage.emit(UiMessage.Info("تم تسجيل الخروج."))
      onNavToHome()
    }
  }

  private fun startResendTimer(seconds: Int) {
    timerJob?.cancel()
    resendCountdown.value = seconds
    timerJob = viewModelScope.launch {
      while (resendCountdown.value > 0) {
        delay(1000)
        resendCountdown.value -= 1
      }
    }
  }
}
