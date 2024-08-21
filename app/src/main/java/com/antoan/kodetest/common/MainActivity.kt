package com.antoan.kodetest.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antoan.kodetest.common.data.api.ApiConstants
import com.antoan.kodetest.common.data.api.ConnectionManager
import com.antoan.kodetest.common.data.api.KodeApi
import com.antoan.kodetest.common.data.api.interceptor.NetworkStatusInterceptor
import com.antoan.kodetest.common.data.api.interceptor.UserInterceptor
import com.antoan.kodetest.common.presentation.theme.KodeTestTheme
import com.antoan.kodetest.main.presentation.MainEvent
import com.antoan.kodetest.main.presentation.MainScreen
import com.antoan.kodetest.main.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.coroutineContext

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
         uiState = uiState
       )
      }
    }
  }

  private fun requestInitialEmployeeList() {
    viewModel.onEvent(MainEvent.RequestInitialEmployeesList)
  }
}

