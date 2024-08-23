package com.antoan.kodetest.common.data.cache.model

import com.antoan.kodetest.common.domain.model.Employee
import com.antoan.kodetest.main.domain.model.SortParameters
import kotlinx.coroutines.flow.Flow

interface Cache {
  fun getAllEmployees(): Flow<List<CachedEmployee>>
  suspend fun storeEmployees(employees: List<CachedEmployee>)
  fun getEmployeesByDepartment(department: String): Flow<List<CachedEmployee>>
}