package com.antoan.kodetest.main.presentation

import com.antoan.kodetest.main.domain.model.SortParameters

sealed class MainEvent {
  data object RequestInitialEmployeesList: MainEvent()
  data class DepartmentChanged(val department: String): MainEvent()
  data class SortOrderChanged(val filterParameters: SortParameters): MainEvent()
}

