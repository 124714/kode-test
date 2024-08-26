package com.antoan.kodetest.main.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.antoan.kodetest.common.presentation.model.fakeUIEmployeeList
import com.antoan.kodetest.common.presentation.theme.KodeTestTheme
import com.antoan.kodetest.main.domain.model.SortParameter
import com.antoan.kodetest.main.presentation.components.EmployeeCard
import com.antoan.kodetest.main.presentation.components.SearchToolbar
import com.antoan.kodetest.main.presentation.components.SortOrderComponent
import com.antoan.kodetest.main.presentation.components.YearDivider
import kotlinx.coroutines.launch

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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  modifier: Modifier = Modifier,
  uiState: MainUiState,
  onDepartmentChanged: (department: String) -> Unit,
  onOrderChanged: (SortParameter) -> Unit,
  onError: () -> Unit,
  pages: Array<EmployeePage> = EmployeePage.entries.toTypedArray()
) {
  var openBottomSheet by rememberSaveable { mutableStateOf(false) }
  val scope = rememberCoroutineScope()
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

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
          onFilterClick = {
            openBottomSheet = !openBottomSheet
          }
        )
      },
    ) { contentPadding ->
      DepartmentPage(
        state = uiState,
        onEmployeeClick = {},
        onDepartmentChange = onDepartmentChanged,
        pages = pages,
        modifier = Modifier.padding(top = contentPadding.calculateTopPadding())
      )
    }

    // Sheet content
    if (openBottomSheet) {
      ModalBottomSheet(
        modifier = Modifier.padding(horizontal = 8.dp),
        onDismissRequest = { openBottomSheet  = false },
        sheetState = bottomSheetState,
      ) {
        SortOrderComponent(
          onOrderChanged = {
            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
              if (!bottomSheetState.isVisible) {
                openBottomSheet = false
              }
            }
            onOrderChanged(it)
          },
          order = uiState.order
        )

      }
    }
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DepartmentPage(
  onEmployeeClick: (UIEmployee) -> Unit,
  onDepartmentChange: (department: String) -> Unit,
  state: MainUiState,
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
      edgePadding = 16.dp
    ) {
      pages.forEachIndexed { index, page ->
        val title = stringResource(id = page.titleResId)
        Tab(
          selected = currentPageIndex == index,
          text = {
            Text(
              text = title,
              fontSize = 15.sp,
              fontWeight = FontWeight.SemiBold
            )
          },
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
              .fillMaxSize()
              .padding(start = 16.dp, end = 16.dp)
          ) {
            itemsIndexed(state.employees) { index, employee ->
              if(state.order == SortParameter.BIRTHDAY && index == state.dividerIndex && state.dividerIndex != 0) {
                YearDivider(year = "2025")
              }
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


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
  KodeTestTheme {
    val state = MainUiState(employees = fakeUIEmployeeList, isLoading = false)
    MainScreen(
      uiState = state,
      onDepartmentChanged = {},
      onOrderChanged = {},
      onError = {}
    )
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EmployeeScreenPreview() {
  KodeTestTheme {
    val state = MainUiState()
    DepartmentPage(
      state = state,
      onDepartmentChange = {},
      onEmployeeClick = {},
      pages = EmployeePage.entries.toTypedArray()
    )
  }
}
