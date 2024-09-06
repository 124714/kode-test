package com.antoan.kodetest.common.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class Employee(
  val id: String,
  val avatarUrl: String,
  val firstName: String,
  val lastName: String,
  val position: String,
  val department: Department,
  val birthday: LocalDate,
  val userTag: String,
  val phone: String
) {
  val fullName = "$firstName $lastName"
  @RequiresApi(Build.VERSION_CODES.O)
  val currentYearBirthday = LocalDate.of(LocalDate.now().year, birthday.month, birthday.dayOfMonth)
}