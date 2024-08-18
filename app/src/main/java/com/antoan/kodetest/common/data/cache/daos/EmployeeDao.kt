package com.antoan.kodetest.common.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.antoan.kodetest.common.data.cache.model.CachedEmployee
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertEmployees(employees: List<CachedEmployee>)

  @Query("SELECT * FROM employee")
  fun getAllEmployees(): Flow<List<CachedEmployee>>

}