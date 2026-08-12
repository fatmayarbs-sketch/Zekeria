package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.EducationLevelEntity
import com.example.data.model.FinanceRecordEntity
import com.example.data.model.SchoolEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SchoolDao {
  @Query("SELECT * FROM school_profiles WHERE id = :schoolId LIMIT 1")
  fun getSchoolById(schoolId: String): Flow<SchoolEntity?>

  @Query("SELECT * FROM school_profiles WHERE adminEmail = :email LIMIT 1")
  suspend fun getSchoolByEmail(email: String): SchoolEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrUpdateSchool(school: SchoolEntity)

  @Query("UPDATE school_profiles SET isEmailVerified = 1 WHERE id = :schoolId")
  suspend fun markEmailVerified(schoolId: String)

  @Query("SELECT * FROM education_levels WHERE schoolId = :schoolId")
  fun getEducationLevels(schoolId: String): Flow<List<EducationLevelEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertEducationLevels(levels: List<EducationLevelEntity>)

  @Query("SELECT * FROM finance_records WHERE schoolId = :schoolId ORDER BY id DESC")
  fun getFinanceRecords(schoolId: String): Flow<List<FinanceRecordEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertFinanceRecord(record: FinanceRecordEntity)
}
