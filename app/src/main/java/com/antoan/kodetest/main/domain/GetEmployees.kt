package com.antoan.kodetest.main.domain

import com.antoan.kodetest.common.domain.model.Employee
import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import com.antoan.kodetest.main.domain.model.SortParameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetEmployees @Inject constructor(
  private val employeeRepository: EmployeeRepository
) {
  operator fun invoke(
    department: StateFlow<String>,
    sort: StateFlow<SortParameter>
  ): Flow<List<Employee>> {
    return combine(
      employeeRepository.getAllEmployees(),
      department,
      sort
    ) { employees, department, sort ->
      val employeesByDepartment = if (department == "ALL") {
        employees
      } else {
        employees.filter { it.department.toString() == department }
      }

      val sortedEmployees =
        if (sort == SortParameter.BIRTHDAY) employeesByDepartment.sortedBy { it.birthday }
        else employeesByDepartment

      sortedEmployees
    }
  }
}

/*employeeRepository.getAllEmployees()
      .filter { it.isNotEmpty()}*/