package com.antoan.kodetest.main.presentation

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antoan.kodetest.R
import com.antoan.kodetest.common.presentation.model.UIEmployee
import com.antoan.kodetest.common.presentation.model.fakeUIEmployeeList
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
  pages: Array<EmployeePage> = EmployeePage.entries.toTypedArray(),
  uiState: MainViewState
) {

  Log.d("MainScreen", uiState.employees.joinToString("\n"))
  Scaffold(
    modifier = modifier,
    topBar = {
      SearchToolbar(searchQuery = "", onSearchQueryChanged = {}, onCancelClick = { /*TODO*/ })
    }
  ) { contentPadding ->
    DepartmentPage(
      state = uiState,
      onEmployeeClick = {},
      onFilterClick = {},
      pages = pages,
      modifier = Modifier.padding(top = contentPadding.calculateTopPadding())
    )
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DepartmentPage(
  onEmployeeClick: (UIEmployee) -> Unit,
  onFilterClick: (String) -> Unit,
  state: MainViewState,
  pages: Array<EmployeePage>,
  modifier: Modifier = Modifier
) {
  var currentPageIndex by remember { mutableIntStateOf(0) }

  val onPageChanged = { index: Int ->
    currentPageIndex = index
    onFilterClick(pages[index].toString())
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
      if (state.isLoading) {
        CircularProgressIndicator()
      } else {
        LazyColumn(
          modifier = Modifier.fillMaxSize()
        ) {
          items(state.employees) { employee ->
            EmployeeCard(employee = employee)
          }
        }
      }
    }
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
