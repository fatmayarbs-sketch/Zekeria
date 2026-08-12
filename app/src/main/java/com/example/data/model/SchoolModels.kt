package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * School Entity storing real school administrator and institution profile data
 * Isolated per school workspace.
 */
@Entity(tableName = "school_profiles")
data class SchoolEntity(
  @PrimaryKey val id: String, // UUID / User ID
  val adminName: String,
  val adminEmail: String,
  val adminPhone: String,
  val schoolName: String,
  val institutionEmail: String,
  val institutionPhone: String,
  val wilaya: String,
  val moughataa: String,
  val commune: String,
  val address: String,
  val isEmailVerified: Boolean = false,
  val createdAt: Long = System.currentTimeMillis()
)

/**
 * Educational Level representation for Mauritanian Education System
 */
@Entity(tableName = "education_levels")
data class EducationLevelEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val schoolId: String,
  val stage: String, // "التعليم الابتدائي", "التعليم الإعدادي", "التعليم الثانوي"
  val levelName: String, // "الابتدائي 1", "الثانوي 3", etc.
  val branchName: String? = null // e.g., "الشعبة الرياضية", "شعبة الأدب"
)

/**
 * Finance Record Entity (MRU currency)
 */
@Entity(tableName = "finance_records")
data class FinanceRecordEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val schoolId: String,
  val title: String,
  val amountMru: Double,
  val type: String, // "INCOME", "EXPENSE"
  val date: String,
  val note: String? = null
)

/**
 * Mauritania Wilayas (Governorates)
 */
val MauritaniaWilayas = listOf(
  "نواكشوط الغربية",
  "نواكشوط الشمالية",
  "نواكشوط الجنوبية",
  "الحوض الشرقي",
  "الحوض الغربي",
  "لعصابه",
  "غورغول",
  "لبراكنة",
  "ترارزة",
  "آدرار",
  "داخلت نواذيبو",
  "تكانت",
  "كيدي ماغة",
  "تيرس زمور",
  "إنشيري"
)
