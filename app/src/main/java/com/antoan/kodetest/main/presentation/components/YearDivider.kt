package com.antoan.kodetest.main.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antoan.kodetest.common.presentation.theme.KodeTestTheme


@Composable
fun YearDivider(
  modifier: Modifier = Modifier,
  year: String
) {
  Row(
    modifier = modifier.padding(horizontal = 8.dp, vertical = 20.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Box(modifier = Modifier
      .background(Color.Gray.copy(alpha = 0.5f))
      .height(1.dp)
      .weight(1f))
    Text(
      text = year,
      modifier = Modifier.padding(horizontal = 60.dp),
      fontSize = 15.sp,
      color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    )
    Box(modifier = Modifier
      .background(Color.Gray.copy(alpha = 0.5f))
      .height(1.dp)
      .weight(1f))
  }
}

@Preview
@Composable
fun YearDividerPreview() {
  KodeTestTheme {
    YearDivider(
      modifier = Modifier.fillMaxWidth(),
      year = "2022"
    )
  }
}