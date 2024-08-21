package com.antoan.kodetest.common.presentation.model


import java.time.LocalDate

data class UIEmployee(
  val id: String,
  val avatarUrl: String,
  val firstName: String,
  val lastName: String,
  val position: String,
  val department: String,
  val userTag: String,
  val birthday: LocalDate,
  val phone: String
) {
  val fullName = "$firstName $lastName"
}

val fakeUIEmployee = UIEmployee(
  id = "dlfj-23nf-23fn",
  firstName = "Мария",
  lastName = "Шарапова",
  avatarUrl = "https://i.pravatar.cc/150",
  position = "Дизайнер",
  department = "Дизайн",
  userTag = "LK",
  phone = "345-123-5432",
  birthday = LocalDate.now()
)

val fakeUIEmployeeList = (1..20).map { fakeUIEmployee }