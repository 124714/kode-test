package com.antoan.kodetest.main.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.antoan.kodetest.R

@Composable
fun EmployeeImage(
  modifier: Modifier = Modifier,
  url: String
) {
  AsyncImage(
    model = ImageRequest.Builder(LocalContext.current)
      .data(url)
      .crossfade(true)
      .build(),
    placeholder = painterResource(R.drawable.placeholder),
    contentDescription = stringResource(R.string.description),
    contentScale = ContentScale.Crop,
    modifier = Modifier
      .size(70.dp)
      .clip(CircleShape)
  )
}

@Preview(showBackground = true)
@Composable
fun EmployeeImagePreview(
  modifier: Modifier = Modifier
) {
  MaterialTheme {
    EmployeeImage(
      url = ""
    )
  }
}