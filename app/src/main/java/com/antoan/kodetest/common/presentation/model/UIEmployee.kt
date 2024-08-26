package com.antoan.kodetest.common.presentation.model


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
data class UIEmployee(
  val id: String,
  val avatarUrl: String,
  val firstName: String,
  val lastName: String,
  val position: String,
  val department: String,
  val nickname: String,
  val birthday: LocalDate,
  val phone: String
) {

  private val formatBirthday = birthday
    .format(DateTimeFormatter.ofPattern("yyyy-MMM-dd"))
    .split("-")
  val fullName = "$firstName $lastName"
  val birthdayMonth = formatBirthday[1].substring(0,3)
  val birthdayDay = formatBirthday[2]
  val birthdayYear = formatBirthday[0]

  fun birthdayIsNextYear(): Boolean {
    val now = LocalDate.now()
    val endOfYear = LocalDate.of(now.year, 12, 31)
    val currentYearBirthday = LocalDate.of(LocalDate.now().year, birthday.month, birthday.dayOfMonth)
    return currentYearBirthday !in now..endOfYear
  }
}

@RequiresApi(Build.VERSION_CODES.O)
val fakeUIEmployee = UIEmployee(
  id = "dlfj-23nf-23fn",
  firstName = "Мария",
  lastName = "Шарапова",
  avatarUrl = "https://i.pravatar.cc/150",
  position = "Дизайнер",
  department = "Дизайн",
  nickname = "lk",
  phone = "345-123-5432",
  birthday = LocalDate.now()
)

@RequiresApi(Build.VERSION_CODES.O)
val fakeUIEmployeeList = (1..20).map { fakeUIEmployee }