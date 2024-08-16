package com.antoan.kodetest.temp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.antoan.kodetest.R


data class Employee(
  val firstName: String,
  val lastName: String,
  val avatar: String,
  val position: String
)

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

@Composable
fun EmployeeCard(
  modifier: Modifier = Modifier,
  employee: Employee
) {
  Row(
    modifier = modifier.padding(8.dp).fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    EmployeeImage(
      modifier = Modifier.padding(8.dp),
      url = employee.avatar
    )
    Spacer(modifier = Modifier.width(16.dp))
    Column {
      Text(
        text = "${employee.firstName} ${employee.lastName}",
        fontSize = 16.sp
      )
      Text(
        text = employee.position,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
        fontSize = 12.sp
      )
    }
  }
}

@Composable
fun SearchToolbar(
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  onCancelClick: () -> Unit,
  modifier: Modifier = Modifier,
) {

  var isFocused by remember { mutableStateOf(false) }

  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.fillMaxWidth(),
  ) {
    SearchTextField(
      onSearchQueryChanged = onSearchQueryChanged,
      searchQuery = searchQuery,
      isFocused = isFocused,
      onFocusChanged = {
        isFocused = it.isFocused
      },
      modifier = Modifier.weight(1f)
    )
    val density = LocalDensity.current
    AnimatedVisibility(
      visible = isFocused,
      enter = slideInHorizontally { with(density) { 30.dp.roundToPx() } }
    ) {
      Text(
        modifier = modifier
          .padding(end = 16.dp)
          .clickable { isFocused = false },
        text = "Отмена",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 16.sp
      )
    }

  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTextField(
  modifier: Modifier = Modifier,
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  onFocusChanged: (FocusState) -> Unit,
  isFocused: Boolean
) {
  val focusRequester = remember { FocusRequester() }
  val keyboardController = LocalSoftwareKeyboardController.current
  val focusManager = LocalFocusManager.current

  LaunchedEffect(isFocused) {
    if (!isFocused) focusManager.clearFocus()
  }

  val onSearchExplicitlyTriggered = {
    keyboardController?.hide()
  }

  TextField(
    colors = TextFieldDefaults.colors(
      focusedIndicatorColor = Color.Transparent,
      unfocusedIndicatorColor = Color.Transparent,
      disabledIndicatorColor = Color.Transparent,
    ),
    leadingIcon = {
      Icon(
        imageVector = Icons.Outlined.Search,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurface,
      )
    },
    trailingIcon = {
      if (searchQuery.isNotEmpty()) {
        IconButton(
          onClick = {
            onSearchQueryChanged("")
          },
        ) {
          Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
          )
        }
      } else if (searchQuery.isEmpty() && !isFocused) {
        IconButton(
          onClick = {}
        ) {
          Icon(
            imageVector = Icons.Outlined.FilterList,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
          )
        }
      }
    },
    onValueChange = {
      if ("\n" !in it) onSearchQueryChanged(it)
    },
    modifier = modifier
      .onFocusChanged(onFocusChanged)
      .padding(16.dp)
      .focusRequester(focusRequester)
      .onKeyEvent {
        if (it.key == Key.Enter) {
          onSearchExplicitlyTriggered()
          true
        } else {
          false
        }
      },
    placeholder = {
      if (!isFocused) {
        Text(
          text = stringResource(id = R.string.search_hint),
          modifier = Modifier.alpha(0.5f)
        )
      }
    },
    shape = RoundedCornerShape(16.dp),
    value = searchQuery,
    keyboardOptions = KeyboardOptions(
      imeAction = ImeAction.Search,
    ),
    keyboardActions = KeyboardActions(
      onSearch = {
        onSearchExplicitlyTriggered()
      },
    ),
    maxLines = 1,
    singleLine = true,
  )

}


@Preview(showBackground = true)
@Composable
fun SearchToolbarPreview() {
  MaterialTheme {
    SearchToolbar(
      searchQuery = "",
      onSearchQueryChanged = {},
      onCancelClick = { /*TODO*/ })
  }
}

@Preview(showBackground = true)
@Composable
fun EmployeeCardPreview() {
  MaterialTheme {
    EmployeeCard(
      employee = Employee("Мария", "Шарапова", "", "Дизайнер")
    )
  }
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