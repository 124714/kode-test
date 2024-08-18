package com.antoan.kodetest.common.data.cache.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.antoan.kodetest.common.domain.model.Department
import com.antoan.kodetest.common.domain.model.Employee
import com.antoan.kodetest.common.utils.DateTimeUtils

@Entity(tableName = "employee")
data class CachedEmployee (
  @PrimaryKey
  @ColumnInfo(name = "employee_id")
  val employeeId: String,
  @ColumnInfo(name = "avatar_url")
  val avatarUrl: String,
  @ColumnInfo(name = "first_name")
  val firstName: String,
  @ColumnInfo(name = "last_name")
  val lastName: String,
  @ColumnInfo(name = "position")
  val position: String,
  @ColumnInfo(name = "department")
  val department: String,
  @ColumnInfo(name = "birthday")
  val birthday: String,
  @ColumnInfo(name = "phone")
  val phone: String,
) {
  companion object {
    fun fromDomain(domainModel: Employee): CachedEmployee {

      return CachedEmployee(
        employeeId = domainModel.id,
        avatarUrl = domainModel.avatarUrl,
        firstName = domainModel.firstName,
        lastName = domainModel.lastName,
        position = domainModel.position,
        department = domainModel.department.toString(),
        birthday = domainModel.birthday.toString(),
        phone = domainModel.phone
      )
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  fun toDomain(): Employee {
    return Employee(
      id = employeeId,
      avatarUrl = avatarUrl,
      firstName = firstName,
      lastName = lastName,
      position = position,
      department = Department.valueOf(department),
      birthday = DateTimeUtils.parse(birthday),
      phone = phone
    )
  }
}