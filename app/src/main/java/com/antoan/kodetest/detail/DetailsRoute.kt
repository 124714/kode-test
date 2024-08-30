package com.antoan.kodetest.detail


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun DetailsRoute(
  modifier: Modifier = Modifier,
  userId: String?
) {
  Log.d("Details", userId ?: "No userId")
  val context = LocalContext.current
  LaunchedEffect(userId) {
    Toast.makeText(context, userId, Toast.LENGTH_LONG).show()
  }
  Box(modifier = modifier
    .fillMaxSize()
    .background(Color.Red))
}