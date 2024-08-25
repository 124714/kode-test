package com.antoan.kodetest.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.kodetest.common.domain.model.NetworkException
import com.antoan.kodetest.common.domain.model.NetworkUnavailableException
import com.antoan.kodetest.common.presentation.Event
import com.antoan.kodetest.common.presentation.model.UIEmployee
import com.antoan.kodetest.common.presentation.model.mappers.UiEmployeeMapper
import com.antoan.kodetest.common.utils.createExceptionHandler
import com.antoan.kodetest.main.domain.GetEmployeeByDepartment
import com.antoan.kodetest.main.domain.GetEmployees
import com.antoan.kodetest.main.domain.RequestInitialEmployeeList
import com.antoan.kodetest.main.domain.model.SortParameter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val getEmployees: GetEmployees,
  private val getEmployeeByDepartment: GetEmployeeByDepartment,
  private val requestInitialEmployeeList: RequestInitialEmployeeList,
  private val uiEmployeeMapper: UiEmployeeMapper
) : ViewModel() {

  private val _uiState = MutableStateFlow(MainUiState())
  val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

  /*private val query = MutableStateFlow("")*/
  private val departmentState = MutableStateFlow("ALL")
  private val orderState = MutableStateFlow(SortParameter.ALPHABET)

  init {
    viewModelScope.launch {
      getEmployees(departmentState, orderState)
        .map { employees -> employees.map { uiEmployeeMapper.mapToView(it) } }
        .catch { onFailure(it) }
        .collect { onUpdateEmployees(it) }
    }
  }

  fun onEvent(event: MainEvent) {
    when (event) {
      MainEvent.RequestInitialEmployeesList -> loadAllEmployees()
      is MainEvent.DepartmentChanged -> updateDepartment(event.department)
      is MainEvent.SortOrderChanged -> updateSortOrder(event.order)
      is MainEvent.QueryInput -> TODO()
    }
  }

  private fun updateSortOrder(order: SortParameter) {
    orderState.value = order

    _uiState.update { oldState ->
      oldState.copy(order = order)
    }
  }

  private fun onFailure(failure: Throwable) {
    when (failure) {
      is NetworkException,
      is NetworkUnavailableException -> {
        _uiState.update { oldState ->
          oldState.copy(
            failure = Event(failure),
            isLoading = false
          )
        }
      }
    }
  }

  private fun onUpdateEmployees(employees: List<UIEmployee>) {
    _uiState.update { oldState ->
      oldState.copy(
        isLoading = false,
        employees = employees
      )
    }
  }

  private fun updateDepartment(department: String) {
    departmentState.value = department
  }

  private fun loadAllEmployees() {
    _uiState.update {
      it.copy(failure = null)
    }
    val errorMessage = "Failed to fetch employees"
    val exceptionHandler = viewModelScope
      .createExceptionHandler(errorMessage) { onFailure(it) }

    if (uiState.value.employees.isEmpty()) {
      viewModelScope.launch(exceptionHandler) {
        requestInitialEmployeeList.invoke()
      }
    }
  }
}