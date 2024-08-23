package com.antoan.kodetest.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antoan.kodetest.common.presentation.theme.KodeTestTheme
import com.antoan.kodetest.main.presentation.MainEvent
import com.antoan.kodetest.main.presentation.MainScreen
import com.antoan.kodetest.main.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    requestInitialEmployeeList()

    setContent {

      val uiState by viewModel.uiState.collectAsStateWithLifecycle()

      KodeTestTheme {
        MainScreen(
          uiState = uiState,
          onError = { requestInitialEmployeeList() },
          onDepartmentChanged = { department ->
            viewModel.onEvent(MainEvent.DepartmentChanged(department))
          }
        )
      }
    }
  }

  private fun requestInitialEmployeeList() {
    viewModel.onEvent(MainEvent.RequestInitialEmployeesList)
  }
}

