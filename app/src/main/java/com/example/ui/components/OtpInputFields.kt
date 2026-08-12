package com.example.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MauritaniaGreen
import com.example.ui.theme.MauritaniaRed

/**
 * RTL 6-Digit Email OTP Input Fields
 * In RTL layout, Index 0 is laid out at the FAR RIGHT, moving left to Index 5.
 */
@Composable
fun OtpInputFields(
  otpLength: Int = 6,
  otpCode: String,
  onOtpCodeChanged: (String) -> Unit,
  isError: Boolean = false,
  modifier: Modifier = Modifier
) {
  val focusRequesters = remember { List(otpLength) { FocusRequester() } }

  // Ensure RTL layout for OTP boxes so index 0 appears on the far right!
  CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    Row(
      modifier = modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      for (index in 0 until otpLength) {
        val char = if (index < otpCode.length) otpCode[index].toString() else ""
        val isFocused = otpCode.length == index || (index == otpLength - 1 && otpCode.length == otpLength)

        val borderColor = when {
          isError -> MauritaniaRed
          isFocused -> MauritaniaGreen
          char.isNotEmpty() -> MauritaniaGreen
          else -> MaterialTheme.colorScheme.outline
        }

        Box(
          modifier = Modifier
            .width(48.dp)
            .height(56.dp)
            .border(
              width = if (isFocused || isError) 2.dp else 1.dp,
              color = borderColor,
              shape = RoundedCornerShape(10.dp)
            ),
          contentAlignment = Alignment.Center
        ) {
          BasicTextField(
            value = char,
            onValueChange = { newValue ->
              if (newValue.length > 1) {
                // Pasted full 6-digit code or multi-digits
                val cleaned = newValue.filter { it.isDigit() }
                if (cleaned.length == otpLength) {
                  onOtpCodeChanged(cleaned)
                  focusRequesters[otpLength - 1].requestFocus()
                } else if (cleaned.isNotEmpty()) {
                  val updated = otpCode.take(index) + cleaned.first()
                  onOtpCodeChanged(updated)
                  if (index < otpLength - 1) {
                    focusRequesters[index + 1].requestFocus()
                  }
                }
              } else if (newValue.isEmpty()) {
                // Backspace pressed
                val updated = if (index < otpCode.length) {
                  otpCode.substring(0, index) + otpCode.substring(index + 1)
                } else {
                  otpCode.dropLast(1)
                }
                onOtpCodeChanged(updated)
                if (index > 0) {
                  focusRequesters[index - 1].requestFocus()
                }
              } else if (newValue.first().isDigit()) {
                // Digit typed
                val builder = StringBuilder(otpCode)
                if (index < otpCode.length) {
                  builder.setCharAt(index, newValue.first())
                } else {
                  builder.append(newValue.first())
                }
                val newCode = builder.toString().take(otpLength)
                onOtpCodeChanged(newCode)
                if (index < otpLength - 1) {
                  focusRequesters[index + 1].requestFocus()
                }
              }
            },
            modifier = Modifier.focusRequester(focusRequesters[index]),
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Number,
              imeAction = if (index == otpLength - 1) ImeAction.Done else ImeAction.Next
            ),
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(
              fontSize = 22.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
        }
      }
    }
  }

  // Request focus on the first box initially
  LaunchedEffect(Unit) {
    if (otpCode.isEmpty()) {
      focusRequesters[0].requestFocus()
    }
  }
}
