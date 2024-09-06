package com.antoan.kodetest.main.domain

import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import com.antoan.kodetest.common.utils.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestInitialEmployeeList @Inject constructor(
  private val employeeRepository: EmployeeRepository,
  private val dispatcherProvider: DispatchersProvider
) {
  suspend fun invoke() = withContext(dispatcherProvider.io()) {
    val employees = employeeRepository.requestEmployees()

    employeeRepository.storeEmployees(employees)
  }
}