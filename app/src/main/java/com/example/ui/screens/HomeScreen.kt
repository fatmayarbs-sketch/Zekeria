package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.MauritaniaFlag
import com.example.ui.theme.MauritaniaGold
import com.example.ui.theme.MauritaniaGreen
import com.example.ui.theme.MauritaniaGreenDark
import com.example.ui.theme.MauritaniaGreenLight

@Composable
fun HomeScreen(
  onNavigateRegister: () -> Unit,
  onNavigateLogin: () -> Unit,
  onNavigateAbout: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .background(MaterialTheme.colorScheme.background)
  ) {
    // HERO SECTION
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(
              MauritaniaGreenDark,
              MauritaniaGreen
            )
          )
        )
        .padding(horizontal = 24.dp, vertical = 36.dp)
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        // Flag Badge
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0x33FFFFFF))
            .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
          MauritaniaFlag(width = 24.dp, height = 16.dp)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "منصة تعليمية موريتانية مستقلة",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
          text = "مرحبًا بكم في مدارس موريتانيا",
          color = Color.White,
          fontSize = 26.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "منصة رقمية حديثة للمؤسسات التعليمية في الجمهورية الإسلامية الموريتانية",
          color = MauritaniaGold,
          fontSize = 16.sp,
          fontWeight = FontWeight.SemiBold,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "تتيح المنصة لكل مدرسة أو مؤسسة تعليمية إنشاء حساب حقيقي خاص بها، وتسجيل بيانات المؤسسة، وإدارة المساحة التعليمية والإدارية بشكل آمن ومنفصل.",
          color = Color(0xFFE0F2E7),
          fontSize = 14.sp,
          lineHeight = 22.sp,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // CTA Buttons
        Row(
          horizontalArrangement = Arrangement.Center,
          modifier = Modifier.fillMaxWidth()
        ) {
          Button(
            onClick = onNavigateRegister,
            colors = ButtonDefaults.buttonColors(containerColor = MauritaniaGold),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .height(48.dp)
              .padding(end = 8.dp)
          ) {
            Text(
              text = "تسجيل مدرسة",
              color = Color(0xFF2C2000),
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
          }

          OutlinedButton(
            onClick = onNavigateLogin,
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White),
            modifier = Modifier.height(48.dp)
          ) {
            Text(
              text = "تسجيل الدخول",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
          }
        }
      }
    }

    // CORE ADVANTAGES / FEATURES
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
    ) {
      Text(
        text = "ماذا تقدم المنصة؟",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(bottom = 16.dp)
      )

      FeatureCard(
        icon = Icons.Outlined.Shield,
        title = "مساحة بيانات آمنة ومستقلة",
        description = "تأمين بيانات كل مؤسسة بعزل تام وحماية وفق أعلى معايير أمان قواعد البيانات (RLS)."
      )

      Spacer(modifier = Modifier.height(12.dp))

      FeatureCard(
        icon = Icons.Outlined.MenuBook,
        title = "متوافق مع الهيكل التعليمي الموريتاني",
        description = "دعم كامل للمراحل الابتدائية (1-6)، الإعدادية (1-4)، والثانوية (1-3) والشعب الدراسية مثل الشعبة الرياضية وشعبة الأدب."
      )

      Spacer(modifier = Modifier.height(12.dp))

      FeatureCard(
        icon = Icons.Outlined.AccountBalance,
        title = "تغطية كافة الولايات والمقاطعات",
        description = "دعم كامل للتصنيف الجغرافي والإداري لولاية ومقاطعة وبلدية كل مؤسسة تعليمية في موريتانيا."
      )
    }

    // MAURITANIAN ACADEMIC SYSTEM OVERVIEW
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp),
      shape = RoundedCornerShape(16.dp),
      color = MauritaniaGreenLight
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.School,
            contentDescription = null,
            tint = MauritaniaGreen,
            modifier = Modifier.size(28.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "الهيكل الأكاديمي المعتمد",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MauritaniaGreenDark
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        AcademicLevelRow("التعليم الابتدائي", "6 سنوات (الابتدائي 1 إلى الابتدائي 6)")
        AcademicLevelRow("التعليم الإعدادي", "4 سنوات (الإعدادي 1 إلى الإعدادي 4)")
        AcademicLevelRow("التعليم الثانوي", "3 سنوات (الثانوي 1 إلى الثانوي 3)")
        AcademicLevelRow("الشعب الثانوية", "الشعبة الرياضية، شعبة الأدب، شعبة العلوم الطبيعية")
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}

@Composable
private fun FeatureCard(
  icon: ImageVector,
  title: String,
  description: String
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.Top
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(MauritaniaGreenLight),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = MauritaniaGreen)
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = description,
          fontSize = 13.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 18.sp
        )
      }
    }
  }
}

@Composable
private fun AcademicLevelRow(
  stage: String,
  details: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = Icons.Default.CheckCircle,
      contentDescription = null,
      tint = MauritaniaGreen,
      modifier = Modifier.size(18.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = "$stage: ",
      fontWeight = FontWeight.Bold,
      fontSize = 13.sp,
      color = MauritaniaGreenDark
    )
    Text(
      text = details,
      fontSize = 13.sp,
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}
