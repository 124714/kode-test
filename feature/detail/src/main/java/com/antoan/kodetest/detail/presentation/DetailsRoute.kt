package com.antoan.kodetest.detail.presentation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.antoan.kodetest.common.presentation.model.UIEmployee
import com.antoan.kodetest.common.presentation.model.fakeUIEmployee
import com.antoan.kodetest.detail.R


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsRoute(
  modifier: Modifier = Modifier,
  viewModel: DetailsViewModel = hiltViewModel(),
) {
  val employee by viewModel.employee.collectAsStateWithLifecycle()

  employee?.let {
    DetailsScreen(
      employee = it,
      onBackClick = {}
    )
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(
  modifier: Modifier = Modifier,
  employee: UIEmployee,
  onBackClick: () -> Unit,
) {
  Column(modifier = modifier) {
    Box(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.surfaceContainer)
        .fillMaxWidth()
        .height(346.dp),
      contentAlignment = Alignment.Center,
    ) {
      Icon(
        modifier = Modifier
          .padding(16.dp)
          .align(Alignment.TopStart),
        imageVector = Icons.Default.ArrowBackIosNew,
        contentDescription = null
      )
      EmployeeTitle(
        employee = employee,
        onBackClick = {}
      )
    }
    AgeRow(
      modifier = Modifier.padding(vertical = 12.dp),
      birthday = employee.uiBirthday,
      age = employee.uiAge
    )
    PhoneRow(
      modifier = Modifier.padding(vertical = 12.dp),
      phone = employee.phone,
      onPhoneClick = { }
    )
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmployeeTitle(
  modifier: Modifier = Modifier,
  employee: UIEmployee,
  onBackClick: () -> Unit
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data(employee.avatarUrl)
        .crossfade(true)
        .build(),
      placeholder = painterResource(R.drawable.placeholder),
      contentDescription = stringResource(R.string.description),
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .size(154.dp)
        .clip(CircleShape)
    )
    Spacer(modifier.height(24.dp))
    Row(verticalAlignment = Alignment.CenterVertically){
      Text(
        text = employee.fullName,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier.width(4.dp))
      Text(
        text = employee.nickname,
        fontSize = 17.sp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
      )
    }
    Spacer(modifier.height(12.dp))
    Text(
      text = employee.position,
      fontSize = 16.sp,
      color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    )
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgeRow(
  modifier: Modifier = Modifier,
  birthday: String,
  age: String
) {
  Row(
    modifier = modifier.padding(horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
  ) {
    Icon(
      imageVector = Icons.Outlined.StarBorder,
      contentDescription = null
    )
    Text(text = birthday)
    Spacer(modifier = Modifier.weight(1f))
    Text(
      text = age,
      fontSize = 16.sp,
      color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
    )
  }
}

@Composable
fun PhoneRow(
  modifier: Modifier = Modifier,
  phone: String,
  onPhoneClick: () -> Unit
) {
  Row(
    modifier = modifier.padding(horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
  ) {
    Icon(
      imageVector = Icons.Outlined.Phone,
      contentDescription = null
    )
    Text(text = phone)
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
  MaterialTheme {
    DetailsScreen(
      modifier = Modifier.fillMaxSize(),
      employee = fakeUIEmployee,
      onBackClick = {}
    )
  }
}