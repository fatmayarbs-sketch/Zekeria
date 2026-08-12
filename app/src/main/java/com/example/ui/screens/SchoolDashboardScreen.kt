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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Class
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EducationLevelEntity
import com.example.data.model.FinanceRecordEntity
import com.example.data.model.SchoolEntity
import com.example.ui.components.EmptyStateView
import com.example.ui.components.MauritaniaFlag
import com.example.ui.theme.MauritaniaGold
import com.example.ui.theme.MauritaniaGreen
import com.example.ui.theme.MauritaniaGreenDark
import com.example.ui.theme.MauritaniaGreenLight
import com.example.ui.theme.MauritaniaRed

@Composable
fun SchoolDashboardScreen(
  school: SchoolEntity?,
  educationLevels: List<EducationLevelEntity>,
  financeRecords: List<FinanceRecordEntity>,
  onAddFinanceRecord: (title: String, amountStr: String, type: String) -> Unit,
  onLogout: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) }
  val tabs = listOf("نظرة عامة", "الهيكل التعليمي", "الطلاب والملفات", "المالية (MRU)", "التقارير")

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    // DASHBOARD TOP BANNER
    Surface(
      color = MauritaniaGreenDark,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            MauritaniaFlag(width = 28.dp, height = 18.dp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = school?.schoolName ?: "مساحة المؤسسة",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Text(
                text = "${school?.wilaya ?: "المملكة"} - مقاطعة ${school?.moughataa ?: ""}",
                fontSize = 12.sp,
                color = Color(0xFFC0D8C8)
              )
            }
          }

          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (school?.isEmailVerified == true) Color(0xFF008040) else MauritaniaGold
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = if (school?.isEmailVerified == true) "بريد موثق" else "بانتظار التوثيق",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            }
          }
        }
      }
    }

    // TABS BAR
    ScrollableTabRow(
      selectedTabIndex = selectedTab,
      edgePadding = 16.dp,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = MauritaniaGreen
    ) {
      tabs.forEachIndexed { index, title ->
        Tab(
          selected = selectedTab == index,
          onClick = { selectedTab = index },
          text = {
            Text(
              text = title,
              fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
              fontSize = 13.sp
            )
          }
        )
      }
    }

    // TAB CONTENT
    Box(modifier = Modifier.weight(1f).padding(16.dp)) {
      when (selectedTab) {
        0 -> OverviewTab(school)
        1 -> AcademicStructureTab(educationLevels)
        2 -> EmptyStateView(
          message = "لا توجد بيانات مسجلة بعد.",
          subtitle = "لم يتم تسجيل أي طلاب في مساحة المدرسة بعد. يمكنك إضافة كشوفات الطلاب لاحقًا."
        )
        3 -> FinanceTab(financeRecords, onAddFinanceRecord)
        4 -> EmptyStateView(
          message = "لا توجد تقارير مسجلة بعد.",
          subtitle = "ستظهر التقارير الإدارية والمالية والأكاديمية تلقائيًا فور بدء العمليات."
        )
      }
    }
  }
}

@Composable
private fun OverviewTab(school: SchoolEntity?) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
  ) {
    Card(
      modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
      shape = RoundedCornerShape(12.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Text(
          text = "معلومات المؤسسة التعليمية",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = MauritaniaGreen
        )
        Divider(modifier = Modifier.padding(vertical = 12.dp))

        DetailRow(Icons.Outlined.School, "اسم المدرسة:", school?.schoolName ?: "-")
        DetailRow(Icons.Outlined.LocationOn, "الولاية:", school?.wilaya ?: "-")
        DetailRow(Icons.Outlined.LocationOn, "المقاطعة:", school?.moughataa ?: "-")
        DetailRow(Icons.Outlined.LocationOn, "البلدية / المكان:", school?.commune ?: "-")
        DetailRow(Icons.Outlined.LocationOn, "العنوان التفصيلي:", school?.address ?: "-")
        DetailRow(Icons.Outlined.Email, "البريد الإلكتروني للمؤسسة:", school?.institutionEmail ?: "-")
        DetailRow(Icons.Outlined.Phone, "هاتف المؤسسة:", school?.institutionPhone ?: "-")
      }
    }

    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Text(
          text = "بيانات المسؤول المخول",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = MauritaniaGreen
        )
        Divider(modifier = Modifier.padding(vertical = 12.dp))

        DetailRow(Icons.Outlined.Person, "اسم المسؤول:", school?.adminName ?: "-")
        DetailRow(Icons.Outlined.Email, "البريد الشخصي:", school?.adminEmail ?: "-")
        DetailRow(Icons.Outlined.Phone, "رقم الهاتف:", school?.adminPhone ?: "-")
      }
    }
  }
}

@Composable
private fun AcademicStructureTab(educationLevels: List<EducationLevelEntity>) {
  if (educationLevels.isEmpty()) {
    EmptyStateView(
      message = "لا توجد بيانات مسجلة بعد.",
      subtitle = "جاري تهيئة المراحل الأكاديمية للمؤسسة."
    )
  } else {
    val grouped = educationLevels.groupBy { it.stage }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
      grouped.forEach { (stage, levels) ->
        item {
          Text(
            text = stage,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MauritaniaGreenDark,
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
          )
        }

        items(levels) { level ->
          Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Outlined.Class, contentDescription = null, tint = MauritaniaGreen)
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = level.levelName,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold
                )
                if (level.branchName != null) {
                  Text(
                    text = "الشعبة: ${level.branchName}",
                    fontSize = 12.sp,
                    color = MauritaniaGold
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun FinanceTab(
  financeRecords: List<FinanceRecordEntity>,
  onAddRecord: (title: String, amountStr: String, type: String) -> Unit
) {
  var title by remember { mutableStateOf("") }
  var amountStr by remember { mutableStateOf("") }
  var type by remember { mutableStateOf("INCOME") }

  Column(modifier = Modifier.fillMaxSize()) {
    // ADD TRANSACTION FORM
    Card(
      modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = MauritaniaGreenLight)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "تسجيل عملية مالية جديدة (بالأوقية الموريتانية MRU)",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = MauritaniaGreenDark
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          label = { Text("بيان العملية (مثال: رسوم تسجيل، مستلزمات)") },
          modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
          singleLine = true
        )

        Row(modifier = Modifier.fillMaxWidth()) {
          OutlinedTextField(
            value = amountStr,
            onValueChange = { amountStr = it },
            label = { Text("المبلغ (MRU)") },
            modifier = Modifier.weight(1f).padding(end = 8.dp),
            singleLine = true
          )

          Button(
            onClick = {
              onAddRecord(title, amountStr, type)
              title = ""
              amountStr = ""
            },
            colors = ButtonDefaults.buttonColors(containerColor = MauritaniaGreen),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.height(56.dp)
          ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Text("إضافة", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // LIST OR EMPTY STATE
    if (financeRecords.isEmpty()) {
      EmptyStateView(
        message = "لا توجد عمليات مالية بعد.",
        subtitle = "لم تقم المؤسسة بتسجيل أي فواتير أو رسوم دراسية بالأوقية الموريتانية (MRU) بعد.",
        icon = Icons.Outlined.ReceiptLong
      )
    } else {
      LazyColumn(modifier = Modifier.weight(1f)) {
        items(financeRecords) { record ->
          Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            shape = RoundedCornerShape(8.dp)
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(text = record.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = record.date, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
              Text(
                text = "${record.amountMru} MRU",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (record.type == "INCOME") MauritaniaGreen else MauritaniaRed
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun DetailRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  value: String
) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = MauritaniaGreen,
      modifier = Modifier.size(18.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = label,
      fontSize = 13.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = value,
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}
