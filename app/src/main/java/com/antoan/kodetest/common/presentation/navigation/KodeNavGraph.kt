package com.antoan.kodetest.common.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.antoan.kodetest.detail.DetailsRoute
import com.antoan.kodetest.main.presentation.MainRoute

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KodeNavGraph(
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController(),
  startDestination: String = "main"
) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
    modifier = modifier
  ) {
    composable(
      route = "main"
    ) {
      MainRoute(
        navController = navController
      )
    }

    composable(
      route = "details/{userId}",
      arguments = listOf(navArgument("userId") { type = NavType.StringType})
    ) { backStackEntry ->
      DetailsRoute(
        userId = backStackEntry.arguments?.getString("userId")
      )
    }
  }
}