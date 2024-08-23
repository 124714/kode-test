package com.antoan.kodetest.main.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antoan.kodetest.R
import com.antoan.kodetest.common.presentation.model.UIEmployee
import com.antoan.kodetest.common.presentation.theme.KodeTestTheme
import com.antoan.kodetest.main.presentation.components.EmployeeCard
import com.antoan.kodetest.main.presentation.components.SearchToolbar

@Composable
fun EmployeesRoute(
  modifier: Modifier = Modifier
) {
  // TODO
}

enum class EmployeePage(@StringRes val titleResId: Int) {
  ALL(R.string.all_dep),
  ANDROID(R.string.android_dep),
  IOS(R.string.ios_dep),
  MANAGEMENT(R.string.management_dep),
  QA(R.string.qa_dep),
  BACK_OFFICE(R.string.back_office_dep),
  FRONTEND(R.string.frontend_dep),
  HR(R.string.hr_dep),
  PR(R.string.pr_dep),
  BACKEND(R.string.backend_dep),
  SUPPORT(R.string.support_dep),
  ANALYTICS(R.string.analytics_dep)
}

@Composable
fun MainScreen(
  modifier: Modifier = Modifier,
  uiState: MainViewState,
  onDepartmentChanged: (department: String) -> Unit,
  onError: () -> Unit,
  pages: Array<EmployeePage> = EmployeePage.entries.toTypedArray()
) {
  if (uiState.failure != null && uiState.employees.isEmpty()) {
    // Условие для первоначальной загрузки данных
    ErrorScreen(
      onRetry = onError
    )
  } else {
    Scaffold(
      modifier = modifier,
      topBar = {
        SearchToolbar(
          searchQuery = "",
          onSearchQueryChanged = {/*TODO*/ },
          onCancelClick = { /*TODO*/ },
          onFilterClick = { /*TODO*/ }
        )
      }
    ) { contentPadding ->
      DepartmentPage(
        state = uiState,
        onEmployeeClick = {},
        onDepartmentChange = onDepartmentChanged,
        pages = pages,
        modifier = Modifier.padding(top = contentPadding.calculateTopPadding())
      )
    }
  }
}

@Composable
fun DepartmentPage(
  onEmployeeClick: (UIEmployee) -> Unit,
  onDepartmentChange: (department: String) -> Unit,
  state: MainViewState,
  pages: Array<EmployeePage>,
  modifier: Modifier = Modifier
) {
  var currentPageIndex by remember { mutableIntStateOf(0) }

  val onPageChanged = { index: Int ->
    currentPageIndex = index
    onDepartmentChange(pages[index].toString())
  }

  Column(modifier) {
    ScrollableTabRow(
      selectedTabIndex = currentPageIndex,
      edgePadding = 0.dp
    ) {
      pages.forEachIndexed { index, page ->
        val title = stringResource(id = page.titleResId)
        Tab(
          selected = currentPageIndex == index,
          text = { Text(title) },
          onClick = { onPageChanged(index) }
        )
      }
    }
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      when {
        state.isLoading -> {
          CircularProgressIndicator()
        }

        else -> {
          LazyColumn(
            modifier = Modifier.fillMaxSize()
          ) {
            items(state.employees) { employee ->
              EmployeeCard(
                modifier = Modifier.clickable { onEmployeeClick(employee) },
                employee = employee
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun ErrorScreen(
  modifier: Modifier = Modifier,
  onRetry: () -> Unit
) {
  Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      modifier = Modifier.size(56.dp),
      painter = painterResource(R.drawable.ic_error_screen),
      contentDescription = null
    )

    Text(
      text = stringResource(R.string.error_title),
      fontWeight = FontWeight.SemiBold,
      fontSize = 17.sp
    )
    Text(
      text = stringResource(R.string.error_subtitle),
      color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
      fontSize = 16.sp
    )
    TextButton(onRetry) {
      Text(
        text = stringResource(id = R.string.error_button_text),
        fontSize = 16.sp
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
  KodeTestTheme {
    ErrorScreen(
      onRetry = {}
    )
  }
}

/*@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun EmployeeScreenPreview() {
  KodeTestTheme {
    val pagerState = rememberPagerState(pageCount = { EmployeePage.entries.size })
    DepartmentPage(
      onEmployeeClick = {},
      onFilterClick = {},
      employees = fakeUIEmployeeList,
      pages = EmployeePage.entries.toTypedArray()
    )
  }
}*/
