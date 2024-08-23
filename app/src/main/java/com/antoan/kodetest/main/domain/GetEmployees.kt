package com.antoan.kodetest.main.domain

import android.util.Log
import com.antoan.kodetest.common.domain.model.Employee
import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import com.antoan.kodetest.main.domain.model.SortParameters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetEmployees @Inject constructor(
  private val employeeRepository: EmployeeRepository
) {
  operator fun invoke(
    departmentStateFlow: StateFlow<String>
  ): Flow<List<Employee>> {
    return combine(
      employeeRepository.getAllEmployees(),
      departmentStateFlow
    ) { employees, department ->
      if(department == "ALL") {
        employees
      } else {
        employees.filter { it.department.toString() == department }
      }
    }
  }
}

/*employeeRepository.getAllEmployees()
      .filter { it.isNotEmpty()}*/