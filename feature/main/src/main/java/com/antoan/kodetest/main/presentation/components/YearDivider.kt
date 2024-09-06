package com.antoan.kodetest.main.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
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
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YearDivider(
  modifier: Modifier = Modifier,
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
      text = LocalDate.now().year.plus(1).toString(),
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun YearDividerPreview() {
  MaterialTheme {
    YearDivider(
      modifier = Modifier.fillMaxWidth(),
    )
  }
}