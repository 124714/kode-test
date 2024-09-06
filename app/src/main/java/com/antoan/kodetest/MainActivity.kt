package com.antoan.kodetest

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.antoan.kodetest.navigation.KodeNavGraph
import com.antoan.kodetest.theme.KodeTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Выполняет сетевой вызов
//    requestInitialEmployeeList()

    setContent {
      KodeTestTheme {
        KodeNavGraph()
      }
    }
  }

  /*private fun requestInitialEmployeeList() {
    viewModel.onEvent(MainEvent.RequestInitialEmployeesList)
  }*/
}

