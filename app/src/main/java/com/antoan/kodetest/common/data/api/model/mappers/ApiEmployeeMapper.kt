package com.antoan.kodetest.common.data.api.model.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.antoan.kodetest.common.data.api.model.ApiEmployee
import com.antoan.kodetest.common.domain.model.Department
import com.antoan.kodetest.common.domain.model.Employee
import com.antoan.kodetest.common.utils.DateTimeUtils
import java.time.LocalDateTime
import javax.inject.Inject

class ApiEmployeeMapper @Inject constructor() : ApiMapper<ApiEmployee, Employee> {

  @RequiresApi(Build.VERSION_CODES.O)
  override fun mapToDomain(apiEntity: ApiEmployee): Employee {
    return Employee(
      id = apiEntity.id ?: throw MappingException("User id cannot be null"),
      avatarUrl = apiEntity.avatarUrl.orEmpty(),
      firstName = apiEntity.firstName.orEmpty(),
      lastName = apiEntity.lastName.orEmpty(),
      position = apiEntity.position.orEmpty(),
      department = parseDepartment(apiEntity.department),
      birthday = DateTimeUtils.parse(apiEntity.birthday.orEmpty()),
      phone = apiEntity.phone.orEmpty(),
      userTag = apiEntity.userTag.orEmpty()
    )
  }

  private fun parseDepartment(department: String?): Department {
    if(department.isNullOrEmpty()) return Department.UNKNOWN
    return Department.valueOf(department.uppercase())
  }

}