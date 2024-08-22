package com.antoan.kodetest.common.data.cache

import com.antoan.kodetest.common.data.cache.daos.EmployeeDao
import com.antoan.kodetest.common.data.cache.model.Cache
import com.antoan.kodetest.common.data.cache.model.CachedEmployee
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
  private val employeeDao: EmployeeDao
): Cache {
  override fun getAllEmployees(): Flow<List<CachedEmployee>> {
    return employeeDao.getAllEmployees()
  }

  override suspend fun storeEmployees(employees: List<CachedEmployee>) {
    employeeDao.insertEmployees(employees)
  }
}