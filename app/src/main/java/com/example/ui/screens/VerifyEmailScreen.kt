package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.OtpInputFields
import com.example.ui.theme.MauritaniaGreen
import com.example.ui.theme.MauritaniaGreenDark
import com.example.ui.theme.MauritaniaGreenLight

@Composable
fun VerifyEmailScreen(
  email: String,
  isLoading: Boolean,
  resendCountdown: Int,
  onVerifySubmit: (otpCode: String) -> Unit,
  onResendOtp: () -> Unit,
  modifier: Modifier = Modifier
) {
  var otpCode by remember { mutableStateOf("") }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = MauritaniaGreenLight,
      modifier = Modifier.padding(bottom = 20.dp)
    ) {
      Icon(
        imageVector = Icons.Outlined.MarkEmailRead,
        contentDescription = null,
        tint = MauritaniaGreen,
        modifier = Modifier.padding(18.dp)
      )
    }

    Text(
      text = "تحقق من بريدك الإلكتروني",
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      color = MauritaniaGreenDark,
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = "أرسلنا رمز تحقق مكونًا من 6 أرقام إلى بريدك الإلكتروني:",
      fontSize = 13.sp,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      textAlign = TextAlign.Center
    )

    Text(
      text = email.ifBlank { "بريدك الإلكتروني" },
      fontSize = 14.sp,
      fontWeight = FontWeight.Bold,
      color = MauritaniaGreen,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(vertical = 4.dp)
    )

    Spacer(modifier = Modifier.height(28.dp))

    // 6-DIGIT RTL OTP INPUT FIELDS
    OtpInputFields(
      otpLength = 6,
      otpCode = otpCode,
      onOtpCodeChanged = { otpCode = it }
    )

    Spacer(modifier = Modifier.height(32.dp))

    // VERIFY BUTTON
    Button(
      onClick = { onVerifySubmit(otpCode) },
      enabled = !isLoading && otpCode.length == 6,
      colors = ButtonDefaults.buttonColors(containerColor = MauritaniaGreen),
      shape = RoundedCornerShape(10.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
    ) {
      if (isLoading) {
        CircularProgressIndicator(color = Color.White, modifier = Modifier.padding(4.dp))
      } else {
        Text("تأكيد البريد والوصول لمساحة المدرسة", fontSize = 15.sp, fontWeight = FontWeight.Bold)
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // RESEND BUTTON WITH COUNTDOWN
    OutlinedButton(
      onClick = onResendOtp,
      enabled = !isLoading && resendCountdown == 0,
      shape = RoundedCornerShape(10.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      if (resendCountdown > 0) {
        Text("إعادة إرسال الرمز خلال ($resendCountdown ثانية)", fontSize = 13.sp)
      } else {
        Text("إعادة إرسال الرمز", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
      }
    }
  }
}
