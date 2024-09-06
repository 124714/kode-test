package com.antoan.kodetest.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.antoan.kodetest.common.data.cache.daos.EmployeeDao
import com.antoan.kodetest.common.data.cache.model.CachedEmployee

@Database(entities = [CachedEmployee::class], version = 1, exportSchema = false)
abstract class EmployeeDatabase: RoomDatabase() {
  abstract fun employeeDao(): EmployeeDao
}