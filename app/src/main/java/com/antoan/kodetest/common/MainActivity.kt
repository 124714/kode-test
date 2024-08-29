package com.antoan.kodetest.common

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antoan.kodetest.R
import com.antoan.kodetest.common.presentation.theme.KodeTestTheme
import com.antoan.kodetest.main.presentation.MainEvent
import com.antoan.kodetest.main.presentation.MainScreen
import com.antoan.kodetest.main.presentation.MainViewModel
import com.antoan.kodetest.temp.PullToRefreshScreen
import com.antoan.kodetest.temp.RefreshLayoutWithList
import com.antoan.kodetest.temp.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels()

  @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Выполняет сетевой вызов
    requestInitialEmployeeList()

    setContent {
      val uiState by viewModel.uiState.collectAsStateWithLifecycle()
      KodeTestTheme {
        MainScreen(
          uiState = uiState,
          onError = { requestInitialEmployeeList() },
          onDepartmentChanged = { department ->
            viewModel.onEvent(MainEvent.DepartmentChanged(department))
          },
          onOrderChanged =  { order ->
            viewModel.onEvent(MainEvent.SortOrderChanged(order))
          },
          onSearchQueryChanged = { query ->
            viewModel.onEvent(MainEvent.QueryChanged(query))
          },
          onSearchModeChanged = { isActive ->
            viewModel.onEvent(MainEvent.SearchModeChanged(isActive))
          },
          onRefresh = {
            viewModel.onEvent(MainEvent.RefreshEmployeeList)
          }
        )
      }
    }
  }

  private fun requestInitialEmployeeList() {
    viewModel.onEvent(MainEvent.RequestInitialEmployeesList)
  }
}

