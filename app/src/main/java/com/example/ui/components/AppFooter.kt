package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MauritaniaGreenDark

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppFooter(
  onNavigateHome: () -> Unit,
  onNavigateAbout: () -> Unit,
  onNavigateRegister: () -> Unit,
  onNavigateLogin: () -> Unit,
  onNavigatePrivacy: () -> Unit,
  onNavigateTerms: () -> Unit,
  onNavigateLegal: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(MauritaniaGreenDark)
      .padding(horizontal = 20.dp, vertical = 24.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(bottom = 8.dp)
    ) {
      MauritaniaFlag(width = 28.dp, height = 18.dp)
      Spacer(modifier = Modifier.width(10.dp))
      Text(
        text = "مدارس موريتانيا",
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Text(
      text = "منصة رقمية مخصصة للمؤسسات التعليمية في الجمهورية الإسلامية الموريتانية لتسهيل التحول الرقمي وإدارة البيانات الإدارية والتربوية.",
      color = Color(0xFFC0D8C8),
      fontSize = 13.sp,
      lineHeight = 20.sp,
      modifier = Modifier.padding(bottom = 16.dp)
    )

    Divider(color = Color(0xFF1B5332), thickness = 1.dp, modifier = Modifier.padding(vertical = 12.dp))

    // Links Section
    FlowRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      FooterLink("الرئيسية", onNavigateHome)
      FooterLink("عن المنصة", onNavigateAbout)
      FooterLink("تسجيل مدرسة", onNavigateRegister)
      FooterLink("تسجيل الدخول", onNavigateLogin)
      FooterLink("سياسة الخصوصية", onNavigatePrivacy)
      FooterLink("الشروط والأحكام", onNavigateTerms)
      FooterLink("التواصل والتحقق", onNavigateLegal)
    }

    Divider(color = Color(0xFF1B5332), thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

    Text(
      text = "جميع الحقوق محفوظة © 2026 منصة مدارس موريتانيا - الجمهورية الإسلامية الموريتانية",
      color = Color(0xFF80A88D),
      fontSize = 11.sp,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
private fun FooterLink(
  label: String,
  onClick: () -> Unit
) {
  Text(
    text = label,
    color = Color.White,
    fontSize = 13.sp,
    fontWeight = FontWeight.Medium,
    modifier = Modifier.clickable { onClick() }
  )
}
