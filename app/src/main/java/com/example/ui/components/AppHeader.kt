package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MauritaniaGold
import com.example.ui.theme.MauritaniaGreen

@Composable
fun AppHeader(
  currentRoute: String,
  isLoggedIn: Boolean,
  schoolName: String?,
  onNavigateHome: () -> Unit,
  onNavigateAbout: () -> Unit,
  onNavigateFeatures: () -> Unit,
  onNavigateRegister: () -> Unit,
  onNavigateLogin: () -> Unit,
  onNavigateDashboard: () -> Unit,
  onLogout: () -> Unit,
  modifier: Modifier = Modifier
) {
  var menuExpanded by remember { mutableStateOf(false) }

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .shadow(2.dp),
    color = MaterialTheme.colorScheme.surface,
    tonalElevation = 2.dp
  ) {
    Column {
      // Top Green & Red Stripe Bar representing Mauritanian identity
      Row(modifier = Modifier.fillMaxWidth().height(4.dp)) {
        Box(modifier = Modifier.weight(1f).height(4.dp).background(MauritaniaGreen))
        Box(modifier = Modifier.weight(0.2f).height(4.dp).background(MauritaniaGold))
        Box(modifier = Modifier.weight(1f).height(4.dp).background(MauritaniaGreen))
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        // Logo + App Title + Compact Mauritania Flag
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.clickable { onNavigateHome() }
        ) {
          // Mauritania Flag Icon (Compact & elegant on top right in RTL)
          MauritaniaFlag(
            width = 32.dp,
            height = 22.dp
          )

          Spacer(modifier = Modifier.width(10.dp))

          Icon(
            imageVector = Icons.Default.School,
            contentDescription = null,
            tint = MauritaniaGreen,
            modifier = Modifier.padding(end = 4.dp)
          )

          Column {
            Text(
              text = "مدارس موريتانيا",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "الجمهورية الإسلامية الموريتانية",
              fontSize = 10.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        // Action Buttons / Dropdown Menu
        Row(verticalAlignment = Alignment.CenterVertically) {
          if (isLoggedIn) {
            Button(
              onClick = onNavigateDashboard,
              colors = ButtonDefaults.buttonColors(containerColor = MauritaniaGreen),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.padding(end = 6.dp)
            ) {
              Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = schoolName ?: "مساحة المدرسة",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
              )
            }
          } else {
            OutlinedButton(
              onClick = onNavigateLogin,
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.padding(end = 6.dp)
            ) {
              Text("تسجيل الدخول", fontSize = 13.sp)
            }

            Button(
              onClick = onNavigateRegister,
              colors = ButtonDefaults.buttonColors(containerColor = MauritaniaGreen),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("تسجيل مدرسة", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
          }

          // Navigation Menu Button
          IconButton(onClick = { menuExpanded = true }) {
            Icon(
              imageVector = Icons.Default.Menu,
              contentDescription = "القائمة الرئيسية"
            )
          }

          DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false }
          ) {
            DropdownMenuItem(
              text = { Text("الرئيسية", fontWeight = FontWeight.Medium) },
              onClick = {
                menuExpanded = false
                onNavigateHome()
              }
            )
            DropdownMenuItem(
              text = { Text("عن المنصة", fontWeight = FontWeight.Medium) },
              onClick = {
                menuExpanded = false
                onNavigateAbout()
              }
            )
            DropdownMenuItem(
              text = { Text("المزايا والنظام", fontWeight = FontWeight.Medium) },
              onClick = {
                menuExpanded = false
                onNavigateFeatures()
              }
            )

            if (isLoggedIn) {
              DropdownMenuItem(
                text = { Text("مساحة المدرسة الحالية", fontWeight = FontWeight.Bold, color = MauritaniaGreen) },
                onClick = {
                  menuExpanded = false
                  onNavigateDashboard()
                }
              )
              DropdownMenuItem(
                text = { Text("تسجيل الخروج", color = Color(0xFFC8102E)) },
                onClick = {
                  menuExpanded = false
                  onLogout()
                }
              )
            } else {
              DropdownMenuItem(
                text = { Text("تسجيل مدرسة جديدة", fontWeight = FontWeight.Bold, color = MauritaniaGreen) },
                onClick = {
                  menuExpanded = false
                  onNavigateRegister()
                }
              )
              DropdownMenuItem(
                text = { Text("تسجيل الدخول") },
                onClick = {
                  menuExpanded = false
                  onNavigateLogin()
                }
              )
            }
          }
        }
      }
    }
  }
}
