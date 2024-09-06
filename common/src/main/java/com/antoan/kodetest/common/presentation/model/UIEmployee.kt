package com.antoan.kodetest.common.presentation.model


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period
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
  val birthdayMonth = formatBirthday[1].substring(0, 3)
  val birthdayDay = formatBirthday[2]
  val birthdayYear = formatBirthday[0]

  val uiAge: String
    get() = with(Period.between(birthday, LocalDate.now()).years) {
      when {
        (this / 10) % 10 == 1 || this % 10 == 0 || this % 10 > 4 -> "$this лет"
        this % 10 == 1 -> "$this год"
        else -> "$this года"
      }
    }

  val uiBirthday: String
    get() = with(birthday) {
      when (month.value) {
        1 -> "$dayOfMonth января $year"
        2 -> "$dayOfMonth февраля $year"
        3 -> "$dayOfMonth марта $year"
        4 -> "$dayOfMonth апреля $year"
        5 -> "$dayOfMonth мая $year"
        6 -> "$dayOfMonth июня $year"
        7 -> "$dayOfMonth июля $year"
        8 -> "$dayOfMonth августа $year"
        9 -> "$dayOfMonth сентября $year"
        10 -> "$dayOfMonth октября $year"
        11 -> "$dayOfMonth ноября $year"
        else -> "$dayOfMonth декабря $year"
      }
    }

  fun birthdayIsNextYear(): Boolean {
    val now = LocalDate.now()
    val endOfYear = LocalDate.of(now.year, 12, 31)
    val currentYearBirthday =
      LocalDate.of(LocalDate.now().year, birthday.month, birthday.dayOfMonth)
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
  birthday = LocalDate.of(1990, 11, 12)
)

@RequiresApi(Build.VERSION_CODES.O)
val fakeUIEmployeeList = (1..20).map { fakeUIEmployee }