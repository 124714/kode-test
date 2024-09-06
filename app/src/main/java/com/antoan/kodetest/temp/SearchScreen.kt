package com.antoan.kodetest.temp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antoan.kodetest.R
import com.antoan.kodetest.theme.KodeTestTheme

@Composable
fun SearchScreen(
  modifier: Modifier = Modifier
) {
  var query by remember { mutableStateOf("") }
  Scaffold(
    topBar = {
      KodeSearchBar(
        onQueryChange = { query = it },
        query = query
      )
    }
  ) { contentPadding ->
    Box(
      modifier = Modifier
        .padding(contentPadding)
        .fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = query
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KodeSearchBar(
  modifier: Modifier = Modifier,
  onQueryChange: (String) -> Unit,
  query: String,
) {
  val focusRequester = remember { FocusRequester() }
  val focusManager = LocalFocusManager.current
  val keyboardController = LocalSoftwareKeyboardController.current

  var isActiveSearchBar by rememberSaveable { mutableStateOf(false) }

  val onSearchQueryExplicitly = { q: String ->
    onQueryChange(query)
    keyboardController?.hide()
  }

  Row(
    modifier = modifier.padding(horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    SearchBar(
      modifier = modifier
        .focusRequester(focusRequester)
        .weight(1f),
      query = query,
      onQueryChange = onQueryChange,
      leadingIcon = {
        Icon(
          imageVector = Icons.Outlined.Search,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onSurface,
        )
      },
      trailingIcon = {
        if (query.isNotEmpty()) {
          IconButton(
            onClick = {
              onQueryChange("")
            },
          ) {
            Icon(
              imageVector = Icons.Outlined.Close,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onSurface,
            )
          }
        } else if (query.isEmpty() /*&& !isFocused*/) {
          IconButton(
            onClick = {}
          ) {
            Icon(
              imageVector = Icons.Outlined.FilterList,
              contentDescription = null,
              /*tint = if (filterParam == SortParameter.BIRTHDAY) {
                MaterialTheme.colorScheme.primary
              } else {
                MaterialTheme.colorScheme.onSurface
              }*/
            )
          }
        }
      },
      placeholder = {
        Text(text = stringResource(id = R.string.search_hint))
      },
      active = false,
      onActiveChange = {},
      onSearch = {
        onSearchQueryExplicitly(it)
      },
      content = {},
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      modifier = modifier
        .clickable {
          onQueryChange("")
          isActiveSearchBar = false
          focusManager.clearFocus()
        },
      text = "Отмена",
      color = MaterialTheme.colorScheme.primary,
      fontSize = 16.sp,
      maxLines = 1
    )
  }
}

@Preview
@Composable
fun KodeSearchPreview() {
  KodeTestTheme {
    KodeSearchBar(
      onQueryChange = {},
      query = ""
    )
  }
}