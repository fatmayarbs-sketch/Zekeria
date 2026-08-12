package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.AppFooter
import com.example.ui.components.AppHeader
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.ForgotPasswordScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LegalScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.PrivacyScreen
import com.example.ui.screens.RegisterSchoolScreen
import com.example.ui.screens.SchoolDashboardScreen
import com.example.ui.screens.TermsScreen
import com.example.ui.screens.VerifyEmailScreen
import com.example.ui.theme.MauritaniaSchoolsTheme
import com.example.ui.viewmodel.SchoolViewModel
import com.example.ui.viewmodel.UiMessage
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MauritaniaSchoolsTheme {
        // RTL Application Wrapper
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
          MauritaniaSchoolsApp()
        }
      }
    }
  }
}

@Composable
fun MauritaniaSchoolsApp() {
  val navController = rememberNavController()
  val schoolViewModel: SchoolViewModel = viewModel()

  val activeSchoolId by schoolViewModel.activeSchoolId.collectAsState()
  val activeEmail by schoolViewModel.activeEmail.collectAsState()
  val schoolProfile by schoolViewModel.schoolProfile.collectAsState()
  val educationLevels by schoolViewModel.educationLevels.collectAsState()
  val financeRecords by schoolViewModel.financeRecords.collectAsState()
  val isLoading by schoolViewModel.isLoading.collectAsState()
  val resendCountdown by schoolViewModel.resendCountdown.collectAsState()

  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route ?: "home"

  val snackbarHostState = remember { SnackbarHostState() }

  // Observe UI messages and show Snackbars
  LaunchedEffect(Unit) {
    schoolViewModel.uiMessage.collectLatest { msg ->
      val text = when (msg) {
        is UiMessage.Success -> msg.text
        is UiMessage.Error -> msg.text
        is UiMessage.Info -> msg.text
      }
      snackbarHostState.showSnackbar(text)
    }
  }

  Scaffold(
    contentWindowInsets = WindowInsets.safeDrawing,
    snackbarHost = { SnackbarHost(snackbarHostState) },
    topBar = {
      AppHeader(
        currentRoute = currentRoute,
        isLoggedIn = activeSchoolId != null,
        schoolName = schoolProfile?.schoolName,
        onNavigateHome = { navController.navigate("home") },
        onNavigateAbout = { navController.navigate("about") },
        onNavigateFeatures = { navController.navigate("home") },
        onNavigateRegister = { navController.navigate("register") },
        onNavigateLogin = { navController.navigate("login") },
        onNavigateDashboard = { navController.navigate("dashboard") },
        onLogout = {
          schoolViewModel.logout {
            navController.navigate("home") {
              popUpTo(0)
            }
          }
        }
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      Box(modifier = Modifier.weight(1f)) {
        NavHost(
          navController = navController,
          startDestination = "home",
          modifier = Modifier.fillMaxSize()
        ) {
          composable("home") {
            HomeScreen(
              onNavigateRegister = { navController.navigate("register") },
              onNavigateLogin = { navController.navigate("login") },
              onNavigateAbout = { navController.navigate("about") }
            )
          }

          composable("register") {
            RegisterSchoolScreen(
              isLoading = isLoading,
              onRegisterSubmit = { adminName, adminEmail, adminPhone, pass, confirmPass, schoolName, instEmail, instPhone, wilaya, moughataa, commune, address ->
                schoolViewModel.registerSchool(
                  adminName = adminName,
                  adminEmail = adminEmail,
                  adminPhone = adminPhone,
                  pass = pass,
                  confirmPass = confirmPass,
                  schoolName = schoolName,
                  institutionEmail = instEmail,
                  institutionPhone = instPhone,
                  wilaya = wilaya,
                  moughataa = moughataa,
                  commune = commune,
                  address = address,
                  onSuccessNavToVerify = { navController.navigate("verify-email") }
                )
              },
              onNavigateLogin = { navController.navigate("login") }
            )
          }

          composable("verify-email") {
            VerifyEmailScreen(
              email = activeEmail ?: "",
              isLoading = isLoading,
              resendCountdown = resendCountdown,
              onVerifySubmit = { code ->
                schoolViewModel.verifyEmailOtp(
                  email = activeEmail ?: "",
                  otpCode = code,
                  onSuccessNavToDashboard = { navController.navigate("dashboard") }
                )
              },
              onResendOtp = {
                schoolViewModel.resendEmailOtp(activeEmail ?: "")
              }
            )
          }

          composable("login") {
            LoginScreen(
              isLoading = isLoading,
              onLoginSubmit = { email, pass ->
                schoolViewModel.loginSchool(
                  email = email,
                  pass = pass,
                  onSuccessNav = { isVerified ->
                    if (isVerified) {
                      navController.navigate("dashboard")
                    } else {
                      navController.navigate("verify-email")
                    }
                  }
                )
              },
              onNavigateRegister = { navController.navigate("register") },
              onNavigateForgotPassword = { navController.navigate("forgot-password") }
            )
          }

          composable("forgot-password") {
            ForgotPasswordScreen(
              isLoading = isLoading,
              onSubmitReset = { email ->
                schoolViewModel.requestPasswordReset(email)
              },
              onNavigateLogin = { navController.navigate("login") }
            )
          }

          composable("dashboard") {
            SchoolDashboardScreen(
              school = schoolProfile,
              educationLevels = educationLevels,
              financeRecords = financeRecords,
              onAddFinanceRecord = { title, amount, type ->
                schoolViewModel.addFinanceRecord(title, amount, type)
              },
              onLogout = {
                schoolViewModel.logout { navController.navigate("home") }
              }
            )
          }

          composable("about") { AboutScreen() }
          composable("privacy") { PrivacyScreen() }
          composable("terms") { TermsScreen() }
          composable("legal") { LegalScreen() }
        }
      }

      // SHOW FOOTER ON ALL NON-DASHBOARD SCREENS OR AT BOTTOM
      if (currentRoute != "dashboard") {
        AppFooter(
          onNavigateHome = { navController.navigate("home") },
          onNavigateAbout = { navController.navigate("about") },
          onNavigateRegister = { navController.navigate("register") },
          onNavigateLogin = { navController.navigate("login") },
          onNavigatePrivacy = { navController.navigate("privacy") },
          onNavigateTerms = { navController.navigate("terms") },
          onNavigateLegal = { navController.navigate("legal") }
        )
      }
    }
  }
}
