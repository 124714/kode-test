package com.antoan.kodetest.common.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.intl.Locale
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateTimeUtils {
  @RequiresApi(Build.VERSION_CODES.O)
  fun parse(dateTimeString: String): LocalDate = try {
//    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd", Locale.current)
    LocalDate.parse(dateTimeString)
  } catch (e: Exception) {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    LocalDate.parse(dateTimeString, dateFormatter)
  }
}