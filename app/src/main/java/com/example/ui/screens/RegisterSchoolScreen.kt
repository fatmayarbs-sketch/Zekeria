package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MauritaniaWilayas
import com.example.ui.theme.MauritaniaGreen
import com.example.ui.theme.MauritaniaGreenDark

@Composable
fun RegisterSchoolScreen(
  isLoading: Boolean,
  onRegisterSubmit: (
    adminName: String,
    adminEmail: String,
    adminPhone: String,
    pass: String,
    confirmPass: String,
    schoolName: String,
    institutionEmail: String,
    institutionPhone: String,
    wilaya: String,
    moughataa: String,
    commune: String,
    address: String
  ) -> Unit,
  onNavigateLogin: () -> Unit,
  modifier: Modifier = Modifier
) {
  var adminName by remember { mutableStateOf("") }
  var adminEmail by remember { mutableStateOf("") }
  var adminPhone by remember { mutableStateOf("+222 ") }
  var pass by remember { mutableStateOf("") }
  var confirmPass by remember { mutableStateOf("") }

  var schoolName by remember { mutableStateOf("") }
  var institutionEmail by remember { mutableStateOf("") }
  var institutionPhone by remember { mutableStateOf("+222 ") }

  var selectedWilaya by remember { mutableStateOf(MauritaniaWilayas[0]) }
  var wilayaExpanded by remember { mutableStateOf(false) }

  var moughataa by remember { mutableStateOf("") }
  var commune by remember { mutableStateOf("") }
  var address by remember { mutableStateOf("") }

  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
      .padding(20.dp)
  ) {
    Text(
      text = "تسجيل مدرسة جديدة",
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      color = MauritaniaGreenDark
    )

    Text(
      text = "أنشئ حسابًا حقيقيًا لمؤسستك التعليمية لبدء إدارة بيانات المدرسة في مساحتك الخاصة.",
      fontSize = 13.sp,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    // SECTION 1: ADMIN DETAILS
    SectionHeader("أولًا: بيانات المسؤول عن التسجيل")

    OutlinedTextField(
      value = adminName,
      onValueChange = { adminName = it },
      label = { Text("الاسم الكامل للمسؤول *") },
      leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
      singleLine = true
    )

    OutlinedTextField(
      value = adminEmail,
      onValueChange = { adminEmail = it },
      label = { Text("البريد الإلكتروني الشخصي *") },
      leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
      singleLine = true
    )

    OutlinedTextField(
      value = adminPhone,
      onValueChange = { adminPhone = it },
      label = { Text("رقم الهاتف (رمز موريتانيا +222)") },
      leadingIcon = { Icon(Icons.Outlined.Phone, contentDescription = null) },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
      singleLine = true
    )

    OutlinedTextField(
      value = pass,
      onValueChange = { pass = it },
      label = { Text("كلمة المرور *") },
      leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
      visualTransformation = PasswordVisualTransformation(),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
      singleLine = true
    )

    OutlinedTextField(
      value = confirmPass,
      onValueChange = { confirmPass = it },
      label = { Text("تأكيد كلمة المرور *") },
      leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
      visualTransformation = PasswordVisualTransformation(),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
      singleLine = true
    )

    // SECTION 2: INSTITUTION DETAILS
    SectionHeader("ثانيًا: بيانات المدرسة أو المؤسسة التعليمية")

    OutlinedTextField(
      value = schoolName,
      onValueChange = { schoolName = it },
      label = { Text("اسم المدرسة / المؤسسة *") },
      leadingIcon = { Icon(Icons.Outlined.School, contentDescription = null) },
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
      singleLine = true
    )

    OutlinedTextField(
      value = institutionEmail,
      onValueChange = { institutionEmail = it },
      label = { Text("البريد الإلكتروني للمؤسسة") },
      leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
      singleLine = true
    )

    OutlinedTextField(
      value = institutionPhone,
      onValueChange = { institutionPhone = it },
      label = { Text("هاتف المؤسسة (+222)") },
      leadingIcon = { Icon(Icons.Outlined.Phone, contentDescription = null) },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
      singleLine = true
    )

    // WILAYA DROPDOWN
    Box(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
      OutlinedTextField(
        value = selectedWilaya,
        onValueChange = {},
        readOnly = true,
        label = { Text("الولاية *") },
        leadingIcon = { Icon(Icons.Outlined.LocationOn, contentDescription = null) },
        trailingIcon = {
          IconButton(onClick = { wilayaExpanded = true }) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
          }
        },
        modifier = Modifier.fillMaxWidth()
      )

      DropdownMenu(
        expanded = wilayaExpanded,
        onDismissRequest = { wilayaExpanded = false }
      ) {
        MauritaniaWilayas.forEach { w ->
          DropdownMenuItem(
            text = { Text(w) },
            onClick = {
              selectedWilaya = w
              wilayaExpanded = false
            }
          )
        }
      }
    }

    OutlinedTextField(
      value = moughataa,
      onValueChange = { moughataa = it },
      label = { Text("المقاطعة *") },
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
      singleLine = true
    )

    OutlinedTextField(
      value = commune,
      onValueChange = { commune = it },
      label = { Text("البلدية / المكان") },
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
      singleLine = true
    )

    OutlinedTextField(
      value = address,
      onValueChange = { address = it },
      label = { Text("العنوان التفصيلي") },
      modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
    )

    // SUBMIT BUTTON
    Button(
      onClick = {
        onRegisterSubmit(
          adminName,
          adminEmail,
          adminPhone,
          pass,
          confirmPass,
          schoolName,
          institutionEmail,
          institutionPhone,
          selectedWilaya,
          moughataa,
          commune,
          address
        )
      },
      enabled = !isLoading,
      colors = ButtonDefaults.buttonColors(containerColor = MauritaniaGreen),
      shape = RoundedCornerShape(10.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
    ) {
      if (isLoading) {
        CircularProgressIndicator(color = Color.White, modifier = Modifier.padding(6.dp))
      } else {
        Text(
          text = "إنشاء حساب المؤسسة وتأكيد البريد",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
      Text("لديك حساب مدرسة مسجل بالفعل؟", fontSize = 13.sp)
      TextButton(onClick = onNavigateLogin) {
        Text("تسجيل الدخول", fontWeight = FontWeight.Bold, color = MauritaniaGreen)
      }
    }
  }
}

@Composable
private fun SectionHeader(title: String) {
  Text(
    text = title,
    fontSize = 15.sp,
    fontWeight = FontWeight.Bold,
    color = MauritaniaGreen,
    modifier = Modifier.padding(vertical = 8.dp)
  )
}
