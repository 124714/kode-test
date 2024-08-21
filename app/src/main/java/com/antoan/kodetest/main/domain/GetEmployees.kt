package com.antoan.kodetest.main.domain

import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetEmployees @Inject constructor(
  private val employeeRepository: EmployeeRepository
) {

  operator fun invoke() = employeeRepository.getAllEmployees()
    .filter { it.isNotEmpty()}
}