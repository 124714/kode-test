package com.antoan.kodetest.common.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.antoan.kodetest.common.data.cache.model.CachedEmployee
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EmployeeDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertEmployees(employees: List<CachedEmployee>)

  @Query("SELECT * FROM employee ORDER BY first_name, last_name DESC")
  abstract fun getAllEmployees(): Flow<List<CachedEmployee>>

  @Query("SELECT * FROM employee WHERE department = :department ORDER BY first_name, last_name DESC")
  abstract fun getEmployeesByDepartment(department: String): Flow<List<CachedEmployee>>
}