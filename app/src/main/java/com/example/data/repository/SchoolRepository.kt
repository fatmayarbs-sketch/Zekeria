package com.example.data.repository

import com.example.data.local.SchoolDao
import com.example.data.model.EducationLevelEntity
import com.example.data.model.FinanceRecordEntity
import com.example.data.model.SchoolEntity
import com.example.data.remote.AuthResult
import com.example.data.remote.SupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SchoolRepository(
  private val schoolDao: SchoolDao,
  private val supabaseClient: SupabaseClient = SupabaseClient()
) {

  fun getSchoolProfile(schoolId: String): Flow<SchoolEntity?> {
    return schoolDao.getSchoolById(schoolId)
  }

  fun getEducationLevels(schoolId: String): Flow<List<EducationLevelEntity>> {
    return schoolDao.getEducationLevels(schoolId)
  }

  fun getFinanceRecords(schoolId: String): Flow<List<FinanceRecordEntity>> {
    return schoolDao.getFinanceRecords(schoolId)
  }

  suspend fun registerSchool(
    adminName: String,
    adminEmail: String,
    adminPhone: String,
    pass: String,
    schoolName: String,
    institutionEmail: String,
    institutionPhone: String,
    wilaya: String,
    moughataa: String,
    commune: String,
    address: String
  ): AuthResult = withContext(Dispatchers.IO) {
    val remoteResult = supabaseClient.signUp(adminEmail, pass, adminName, schoolName)
    if (remoteResult is AuthResult.Success) {
      val schoolEntity = SchoolEntity(
        id = remoteResult.userId,
        adminName = adminName,
        adminEmail = adminEmail,
        adminPhone = adminPhone,
        schoolName = schoolName,
        institutionEmail = institutionEmail,
        institutionPhone = institutionPhone,
        wilaya = wilaya,
        moughataa = moughataa,
        commune = commune,
        address = address,
        isEmailVerified = remoteResult.isVerified
      )
      schoolDao.insertOrUpdateSchool(schoolEntity)
      initializeDefaultEducationLevels(remoteResult.userId)
    }
    remoteResult
  }

  suspend fun verifyEmailOtp(email: String, otpCode: String): AuthResult = withContext(Dispatchers.IO) {
    val remoteResult = supabaseClient.verifyEmailOtp(email, otpCode)
    if (remoteResult is AuthResult.Success) {
      val localSchool = schoolDao.getSchoolByEmail(email)
      if (localSchool != null) {
        schoolDao.markEmailVerified(localSchool.id)
      }
    }
    remoteResult
  }

  suspend fun resendEmailOtp(email: String): AuthResult = withContext(Dispatchers.IO) {
    supabaseClient.resendEmailOtp(email)
  }

  suspend fun loginSchool(email: String, pass: String): AuthResult = withContext(Dispatchers.IO) {
    val result = supabaseClient.signIn(email, pass)
    if (result is AuthResult.Success) {
      var localSchool = schoolDao.getSchoolByEmail(email)
      if (localSchool == null) {
        localSchool = SchoolEntity(
          id = result.userId,
          adminName = "المسؤول الإداري",
          adminEmail = email,
          adminPhone = "+222",
          schoolName = "مؤسسة تعليمية",
          institutionEmail = email,
          institutionPhone = "+222",
          wilaya = "نواكشوط الغربية",
          moughataa = "لكصر",
          commune = "المركز",
          address = "نواكشوط",
          isEmailVerified = result.isVerified
        )
        schoolDao.insertOrUpdateSchool(localSchool)
        initializeDefaultEducationLevels(result.userId)
      }
    }
    result
  }

  suspend fun requestPasswordReset(email: String): AuthResult = withContext(Dispatchers.IO) {
    supabaseClient.requestPasswordReset(email)
  }

  suspend fun addFinanceRecord(record: FinanceRecordEntity) = withContext(Dispatchers.IO) {
    schoolDao.insertFinanceRecord(record)
  }

  private suspend fun initializeDefaultEducationLevels(schoolId: String) {
    val levels = mutableListOf<EducationLevelEntity>()

    // Primary (التعليم الابتدائي 1-6)
    for (i in 1..6) {
      levels.add(
        EducationLevelEntity(
          schoolId = schoolId,
          stage = "التعليم الابتدائي",
          levelName = "الابتدائي $i"
        )
      )
    }

    // Middle (التعليم الإعدادي 1-4)
    for (i in 1..4) {
      levels.add(
        EducationLevelEntity(
          schoolId = schoolId,
          stage = "التعليم الإعدادي",
          levelName = "الإعدادي $i"
        )
      )
    }

    // Secondary (التعليم الثانوي 1-3) with Mauritanian Branches
    levels.add(EducationLevelEntity(schoolId = schoolId, stage = "التعليم الثانوي", levelName = "الثانوي 1"))
    levels.add(EducationLevelEntity(schoolId = schoolId, stage = "التعليم الثانوي", levelName = "الثانوي 2"))
    levels.add(EducationLevelEntity(schoolId = schoolId, stage = "التعليم الثانوي", levelName = "الثانوي 3", branchName = "الشعبة الرياضية"))
    levels.add(EducationLevelEntity(schoolId = schoolId, stage = "التعليم الثانوي", levelName = "الثانوي 3", branchName = "شعبة الأدب"))
    levels.add(EducationLevelEntity(schoolId = schoolId, stage = "التعليم الثانوي", levelName = "الثانوي 3", branchName = "شعبة العلوم الطبيعية"))

    schoolDao.insertEducationLevels(levels)
  }
}
