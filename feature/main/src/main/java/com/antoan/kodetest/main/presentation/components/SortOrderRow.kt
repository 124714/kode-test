package com.antoan.kodetest.main.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antoan.kodetest.main.R
import com.antoan.kodetest.main.domain.model.SortParameter
import com.antoan.kodetest.main.presentation.MainEvent


@Composable
fun SortOrderComponent(
  modifier: Modifier = Modifier,
  onOrderChanged: (SortParameter) -> Unit,
  order: SortParameter
) {

  Column{
    Text(
      modifier = Modifier
        .padding(top = 10.dp, bottom = 14.dp)
        .align(Alignment.CenterHorizontally),
      text = stringResource(R.string.sort_title),
      fontSize = 22.sp,
      fontWeight = FontWeight.SemiBold
    )
    Row(
      modifier = modifier
        .fillMaxWidth()
        .clickable { onOrderChanged(SortParameter.ALPHABET) },
      verticalAlignment = Alignment.CenterVertically
    ) {
      RadioButton(
        selected = order == SortParameter.ALPHABET,
        onClick = {
          onOrderChanged(SortParameter.ALPHABET)
        }
      )
      Text(
        text = stringResource(R.string.alphabetic_order)
      )
    }
    Row(
      modifier = modifier
        .fillMaxWidth()
        .clickable { onOrderChanged(SortParameter.BIRTHDAY) },
      verticalAlignment = Alignment.CenterVertically
    ) {
      RadioButton(
        selected = order == SortParameter.BIRTHDAY,
        onClick = {
          onOrderChanged(SortParameter.BIRTHDAY)
        }
      )
      Text(
        text = stringResource(R.string.birthday_order)
      )
    }

    Spacer(modifier = Modifier.height(80.dp))
  }
}

@Preview(showBackground = true)
@Composable
fun SortOrderRowPreveiw() {
  MaterialTheme {
   SortOrderComponent(
     onOrderChanged = {},
     order = SortParameter.ALPHABET
   )
  }
}