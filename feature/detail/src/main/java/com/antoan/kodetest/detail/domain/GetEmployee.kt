package com.antoan.kodetest.detail.domain

import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import javax.inject.Inject

class GetEmployee @Inject constructor(
  private val employeeRepository: EmployeeRepository
) {
  suspend operator fun invoke(employeeId: String) = employeeRepository.getEmployee(employeeId)
}