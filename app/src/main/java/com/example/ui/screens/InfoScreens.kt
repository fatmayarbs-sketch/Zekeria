package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MauritaniaGreenDark

@Composable
fun AboutScreen(
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
      .padding(20.dp)
  ) {
    Text(
      text = "عن منصة مدارس موريتانيا",
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      color = MauritaniaGreenDark
    )

    Spacer(modifier = Modifier.height(12.dp))

    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp)
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Text(
          text = "الرؤية والهدف",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "تعد منصة 'مدارس موريتانيا' بوابة رقمية وطنية موجهة لتحديث وإدارة البيانات التربوية والإدارية لمؤسسات التعليم في الجمهورية الإسلامية الموريتانية.",
          fontSize = 14.sp,
          lineHeight = 22.sp,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "استقلالية البيانات وأمان المؤسسات",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "تضمن المنصة لكل مؤسسة تعليمية مساحة آمنة ومعزولة كليًا بفضل تقنيات حماية البيانات الحقيقية Row Level Security (RLS) وقواعد البيانات المشفرة، بحيث لا يمكن لأي طرف الاطلاع على بيانات المدارس الأخرى.",
          fontSize = 14.sp,
          lineHeight = 22.sp,
          color = MaterialTheme.colorScheme.onSurface
        )
      }
    }
  }
}

@Composable
fun PrivacyScreen(
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
      .padding(20.dp)
  ) {
    Text(
      text = "سياسة الخصوصية وحماية البيانات",
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      color = MauritaniaGreenDark
    )

    Spacer(modifier = Modifier.height(12.dp))

    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp)
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Text(
          text = "1. جمع البيانات واستخدامها",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "يتم جمع بيانات المسؤول والمؤسسة التعليمية لأغراض التوثيق وإنشاء مساحة العمل الخاصة بالمدرسة فقط. لا نقوم ببيع أو مشاركة البيانات مع أي أطراف تجارية خارجيّة.",
          fontSize = 13.sp,
          lineHeight = 20.sp,
          modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        Text(
          text = "2. التوثيق الإلكتروني",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "يتم التحقق من الحسابات عبر البريد الإلكتروني الرسمي لضمان أمان المسؤولين وتفادي الحسابات غير المخولة.",
          fontSize = 13.sp,
          lineHeight = 20.sp,
          modifier = Modifier.padding(top = 4.dp)
        )
      }
    }
  }
}

@Composable
fun TermsScreen(
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
      .padding(20.dp)
  ) {
    Text(
      text = "الشروط والأحكام",
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      color = MauritaniaGreenDark
    )

    Spacer(modifier = Modifier.height(12.dp))

    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp)
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Text(
          text = "الالتزام بدقة البيانات",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "يتعهد المسؤول بتقديم معلومات صحيحة ودقيقة تخص المؤسسة التعليمية في الجمهورية الإسلامية الموريتانية وإدخال بيانات حقيقية دون تزوير.",
          fontSize = 13.sp,
          lineHeight = 20.sp,
          modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        Text(
          text = "الاستخدام المصرح به",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "يقتصر استخدام النظام على الأغراض التعليمية والإدارية المعتمدة فقط.",
          fontSize = 13.sp,
          lineHeight = 20.sp,
          modifier = Modifier.padding(top = 4.dp)
        )
      }
    }
  }
}

@Composable
fun LegalScreen(
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
      .padding(20.dp)
  ) {
    Text(
      text = "التواصل والتحقق القانوني",
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      color = MauritaniaGreenDark
    )

    Spacer(modifier = Modifier.height(12.dp))

    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp)
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Text(
          text = "الجمهورية الإسلامية الموريتانية",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        )
        Text(
          text = "منصة 'مدارس موريتانيا' مخصصة لخدمة قطاع التعليم الوطني الموريتاني. للتحقق والتواصل مع الدعم الفني والإداري للمؤسسات المسجلة، يرجى المراسلة عبر بريد الدعم المعتمد.",
          fontSize = 13.sp,
          lineHeight = 20.sp,
          modifier = Modifier.padding(top = 8.dp)
        )
      }
    }
  }
}
