package com.antoan.kodetest.main.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antoan.kodetest.common.presentation.model.UIEmployee
import com.antoan.kodetest.common.presentation.model.fakeUIEmployee
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmployeeCard(
  modifier: Modifier = Modifier,
  employee: UIEmployee
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    EmployeeImage(
      modifier = Modifier.padding(8.dp),
      url = employee.avatarUrl
    )
    Spacer(modifier = Modifier.width(16.dp))
    Column {
      Row{
        Text(
          text = employee.fullName,
          fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = employee.nickname,
          fontSize = 14.sp,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        )
      }
      Text(
        text = employee.position,
        fontWeight = FontWeight.Light ,
//        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        fontSize = 16.sp
      )
    }
    Spacer(modifier = Modifier.weight(1f))
    Text(
//      modifier = Modifier.weight(1f),
      text = "${employee.birthdayDay} ${employee.birthdayMonth} ${employee.birthdayYear}",
      fontSize = 15.sp,
//      fontWeight = FontWeight.Medium,
      color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    )
    Spacer(modifier = Modifier.width(4.dp))
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EmployeeCardPreview() {
  MaterialTheme {
    EmployeeCard(
      employee = fakeUIEmployee
    )
  }
}