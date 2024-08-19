package com.antoan.kodetest.common.domain.repository

import com.antoan.kodetest.common.domain.model.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
  fun getEmployees(): Flow<List<Employee>>
  suspend fun requestEmployees(): List<Employee>
  fun storeEmployees(employees: List<Employee>)
}