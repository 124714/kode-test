package com.antoan.kodetest.main.domain

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.antoan.kodetest.common.domain.model.Employee
import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import com.antoan.kodetest.main.domain.model.SortParameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.LocalDate
import javax.inject.Inject

class GetEmployees @Inject constructor(
  private val employeeRepository: EmployeeRepository
) {
  @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
  operator fun invoke(
    department: StateFlow<String>,
    sort: StateFlow<SortParameter>,
    query: StateFlow<String>
  ): Flow<List<Employee>> {
    return combine(
      employeeRepository.getAllEmployees(),
      department,
      sort,
      query
    ) { employees, department, sort, query ->
      val employeesByDepartment = if (department == "ALL") {
        employees
      } else {
        employees.filter { it.department.toString() == department }
      }

      val sortedEmployees =
        if (sort == SortParameter.BIRTHDAY) {
          val now = LocalDate.now()
          val endOfYear = LocalDate.of(now.year, 12, 31)
          employeesByDepartment
            .partition { it.currentYearBirthday in now..endOfYear }
            .let { emp ->
              emp.first.sortedWith(compareBy({ it.birthday.month }, { it.birthday.dayOfMonth })) +
                      emp.second.sortedWith(compareBy({ it.birthday.month }, { it.birthday.dayOfMonth }))
            }
        }
        else employeesByDepartment

      val normalQuery = query.trim()
      val filteredByQueryEmployees = sortedEmployees.filter {
        normalQuery in it.fullName.lowercase() || normalQuery in it.userTag.lowercase()
      }

      filteredByQueryEmployees
    }.distinctUntilChanged()
  }
}

/*employeeRepository.getAllEmployees()
      .filter { it.isNotEmpty()}*/