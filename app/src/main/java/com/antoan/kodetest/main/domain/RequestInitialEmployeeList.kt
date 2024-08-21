package com.antoan.kodetest.main.domain

import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestInitialEmployeeList @Inject constructor(
  private val employeeRepository: EmployeeRepository
) {
  suspend fun invoke() = withContext(Dispatchers.IO) {
    val employees = employeeRepository.requestEmployees()
    employeeRepository.storeEmployees(employees)
  }
}