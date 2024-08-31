package com.antoan.kodetest.detail.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoan.kodetest.common.presentation.model.UIEmployee
import com.antoan.kodetest.common.presentation.model.mappers.UiEmployeeMapper
import com.antoan.kodetest.detail.domain.GetEmployee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DetailsViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val getEmployee: GetEmployee,
  private val uiEmployeeMapper: UiEmployeeMapper
) : ViewModel() {

  val userId: StateFlow<String?> = savedStateHandle.getStateFlow("userId", null)
  private val _employee = MutableStateFlow<UIEmployee?>(null)
  val employee: StateFlow<UIEmployee?> = _employee.asStateFlow()

  init {
    viewModelScope.launch {
      _employee.value = userId.value?.let { uiEmployeeMapper.mapToView(getEmployee(it)) }
      }
    }
}