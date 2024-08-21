package com.antoan.kodetest.main.presentation

sealed class MainEvent {
  data object RequestInitialEmployeesList: MainEvent()
  data class DepartmentChanged(val department: String): MainEvent()
}