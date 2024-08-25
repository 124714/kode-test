package com.antoan.kodetest.main.presentation

import com.antoan.kodetest.main.domain.model.SortParameter

sealed class MainEvent {
  data object RequestInitialEmployeesList: MainEvent()
  data class DepartmentChanged(val department: String): MainEvent()
  data class SortOrderChanged(val order: SortParameter): MainEvent()
  data class QueryInput(val input: String): MainEvent()
}

