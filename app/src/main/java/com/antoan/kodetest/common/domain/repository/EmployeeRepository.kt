package com.antoan.kodetest.common.domain.repository

import com.antoan.kodetest.common.domain.model.Employee
import com.antoan.kodetest.main.domain.model.SortParameters
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
  fun getAllEmployees(): Flow<List<Employee>>
  suspend fun requestEmployees(): List<Employee>
  suspend fun storeEmployees(employees: List<Employee>)
  fun getEmployeeByDepartment(department: String): Flow<List<Employee>>
  /*fun getEmployeesWithFilter(
    query: String,
    order: SortParameters,
    department: String
  ): Flow<List<Employee>>*/
}