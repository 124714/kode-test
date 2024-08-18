package com.antoan.kodetest.common.domain.model

import java.time.LocalDate

data class Employee(
  val id: String,
  val avatarUrl: String,
  val firstName: String,
  val lastName: String,
  val position: String,
  val department: Department,
  val birthday: LocalDate,
  val phone: String
)