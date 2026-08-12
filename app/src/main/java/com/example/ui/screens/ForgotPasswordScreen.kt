package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LockReset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MauritaniaGreen
import com.example.ui.theme.MauritaniaGreenDark
import com.example.ui.theme.MauritaniaGreenLight

@Composable
fun ForgotPasswordScreen(
  isLoading: Boolean,
  onSubmitReset: (email: String) -> Unit,
  onNavigateLogin: () -> Unit,
  modifier: Modifier = Modifier
) {
  var email by remember { mutableStateOf("") }

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
      modifier = Modifier.padding(bottom = 16.dp)
    ) {
      Icon(
        imageVector = Icons.Outlined.LockReset,
        contentDescription = null,
        tint = MauritaniaGreen,
        modifier = Modifier.padding(18.dp)
      )
    }

    Text(
      text = "استعادة كلمة المرور",
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      color = MauritaniaGreenDark
    )

    Text(
      text = "أدخل البريد الإلكتروني لمسؤول المدرسة لإرسال تعليمات إعادة تعيين كلمة المرور.",
      fontSize = 13.sp,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
    )

    OutlinedTextField(
      value = email,
      onValueChange = { email = it },
      label = { Text("البريد الإلكتروني المسجل") },
      leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
      modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
      singleLine = true
    )

    Button(
      onClick = { onSubmitReset(email) },
      enabled = !isLoading && email.isNotBlank(),
      colors = ButtonDefaults.buttonColors(containerColor = MauritaniaGreen),
      shape = RoundedCornerShape(10.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
    ) {
      if (isLoading) {
        CircularProgressIndicator(color = Color.White, modifier = Modifier.padding(4.dp))
      } else {
        Text("إرسال رابط الاستعادة", fontSize = 15.sp, fontWeight = FontWeight.Bold)
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    TextButton(onClick = onNavigateLogin) {
      Text("العودة إلى صفحة تسجيل الدخول", color = MauritaniaGreen, fontWeight = FontWeight.Bold)
    }
  }
}
