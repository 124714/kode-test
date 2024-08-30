package com.antoan.kodetest.detail


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailsRoute(
  modifier: Modifier = Modifier,
  viewModel: DetailsViewModel = hiltViewModel(),
  userId: String?
) {
  val userIdFromVM by viewModel.userId.collectAsStateWithLifecycle()

  val context = LocalContext.current
  LaunchedEffect(userId) {
    Toast.makeText(context, userIdFromVM, Toast.LENGTH_LONG).show()
  }
  Box(modifier = modifier
    .fillMaxSize()
    .background(Color.Red))
}