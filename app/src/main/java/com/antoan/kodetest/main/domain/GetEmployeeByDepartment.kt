package com.antoan.kodetest.main.domain

import com.antoan.kodetest.common.domain.model.Employee
import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEmployeeByDepartment @Inject constructor(
  private val employeeRepository: EmployeeRepository
) {
  operator fun invoke(department: String): Flow<List<Employee>> = if (department == "ALL") {
    employeeRepository.getAllEmployees()
  } else {
    employeeRepository.getEmployeeByDepartment(department)
  }
}

