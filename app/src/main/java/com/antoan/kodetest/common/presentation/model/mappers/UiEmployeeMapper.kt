package com.antoan.kodetest.common.presentation.model.mappers

import com.antoan.kodetest.common.domain.model.Department
import com.antoan.kodetest.common.domain.model.Employee
import com.antoan.kodetest.common.presentation.model.UIEmployee
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class UiEmployeeMapper @Inject constructor(): UiMapper<Employee, UIEmployee> {
  override fun mapToView(input: Employee): UIEmployee {
    return UIEmployee(
      id = input.id,
      avatarUrl = input.avatarUrl,
      firstName = input.firstName,
      lastName = input.lastName,
      position = input.position,
      department = input.department.toString(),
      nickname = input.userTag.lowercase(),
      birthday = input.birthday,
      phone = input.phone
    )
  }
}