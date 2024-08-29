package com.antoan.kodetest.main.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@HiltViewModel
class MainViewModel @Inject constructor(
  private val getEmployees: GetEmployees,
  private val requestInitialEmployeeList: RequestInitialEmployeeList,
  private val uiEmployeeMapper: UiEmployeeMapper
) : ViewModel() {

  private val _uiState = MutableStateFlow(MainUiState())
  val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

  private val queryState = MutableStateFlow("")
  private val departmentState = MutableStateFlow("ALL")
  private val orderState = MutableStateFlow(SortParameter.ALPHABET)

  init {
    viewModelScope.launch {
      getEmployees(departmentState, orderState, queryState)
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
      is MainEvent.QueryChanged -> updateQueryInput(event.input)
      is MainEvent.SearchModeChanged -> updateSearchMode(event.isActive)
      is MainEvent.RefreshEmployeeList -> reloadAllEmployees()
    }
  }

  private fun updateSearchMode(isActive: Boolean) {
    _uiState.update { oldState ->
      oldState.copy(isSearchMode = isActive)
    }
  }

  private fun updateSortOrder(order: SortParameter) {
    orderState.value = order
    // Нужно исправить: UI нет необходиомсти потреблять это состояние (order)
    _uiState.update { oldState ->
      oldState.copy(
        order = order
      )
    }
  }

  private fun updateQueryInput(input: String) {
    queryState.value = input
    _uiState.update { oldState ->
      oldState.copy(
        searchQuery = input,
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

  private fun reloadAllEmployees() {
    _uiState.update {
      it.copy(failure = null)
    }
    val errorMessage = "Failed to fetch employees"
    val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }
    viewModelScope.launch(exceptionHandler) {
      _uiState.update { it.copy(isRefreshing = true) }
      requestInitialEmployeeList.invoke()
      _uiState.update { it.copy(isRefreshing = false) }
    }
  }

  private fun onUpdateEmployees(employees: List<UIEmployee>) {
    _uiState.update { oldState ->
      oldState.copy(
        isLoading = false,
        employees = employees,
        dividerIndex = if (orderState.value == SortParameter.BIRTHDAY)
          employees.calculateDividerIndex().also {
            Log.d("MainViewModel", " divider index: $it")
          }
        else
          MainUiState.NOT_DIVIDER_INDEX,
        noSearchResult = oldState.isSearchMode && employees.isEmpty()
      )
    }
  }

  private fun onFailure(failure: Throwable) {
    when (failure) {
      is NetworkException,
      is NetworkUnavailableException -> {
        _uiState.update { oldState ->
          oldState.copy(
            failure = Event(failure),
            isLoading = false,
            isRefreshing = false
          )
        }
      }

      /*else -> {
        Log.d("MainViewModel", "Failure: ${failure.message}")
        _uiState.update { oldState ->
          oldState.copy(
            failure = Event(failure),
            isLoading = false,
            isRefreshing = false
          )
        }
      }*/
    }
  }

  private fun List<UIEmployee>.calculateDividerIndex(): Int {
    if (this.all { it.birthdayIsNextYear() }) return 0
    for (index in 0..<lastIndex) {
      if (this[index + 1].birthday.month.value - this[index].birthday.month.value < 0) {
        return index + 1
      }
    }
    return MainUiState.NOT_DIVIDER_INDEX
  }
}