package com.antoan.kodetest.main.presentation

import android.util.Log
import com.antoan.kodetest.common.domain.model.Department
import com.antoan.kodetest.common.domain.model.Employee
import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import com.antoan.kodetest.common.presentation.model.UIEmployee
import com.antoan.kodetest.common.presentation.model.mappers.UiEmployeeMapper
import com.antoan.kodetest.common.utils.DispatchersProvider
import com.antoan.kodetest.main.domain.GetEmployees
import com.antoan.kodetest.main.domain.RequestInitialEmployeeList
import com.antoan.kodetest.main.domain.model.SortParameter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.bouncycastle.util.test.SimpleTest.runTest
import org.jetbrains.annotations.TestOnly
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.time.LocalDate

@ExperimentalCoroutinesApi
class TestCoroutineRule(
  val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
  override fun starting(description: Description) {
    Dispatchers.setMain(testDispatcher)
  }

  override fun finished(description: Description) {
    Dispatchers.resetMain()
  }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

  @get:Rule
  val testCoroutineRule = TestCoroutineRule()

  private lateinit var viewModel: MainViewModel
  private lateinit var repository: FakeRepository
  private lateinit var getEmployees: GetEmployees
  private lateinit var requestInitialEmployeeList: RequestInitialEmployeeList

  private val uiEmployeeMapper = UiEmployeeMapper()


  @Before
  fun setup() {
    repository = FakeRepository()
    val dispatchersProvider = object : DispatchersProvider {
      override fun io() = testCoroutineRule.testDispatcher
    }

    // Интеракторы
    getEmployees = GetEmployees(repository)
    requestInitialEmployeeList = RequestInitialEmployeeList(repository, dispatchersProvider)

    viewModel = MainViewModel(
      getEmployees = getEmployees,
      requestInitialEmployeeList = requestInitialEmployeeList,
      uiEmployeeMapper = uiEmployeeMapper
    )
  }

  @Test
  fun mainViewModel_InitializeViewModel_CorrectUiState() = runTest {
    // Given
    val employeesInitList = emptyList<UIEmployee>()

    val expectedInitialState = MainUiState(
      isLoading = false,
      employees = employeesInitList
    )
    // When

    // Первичная инициализация MainViewModel

    // Then
    val uiState = viewModel.uiState.value

    assertEquals(expectedInitialState, uiState)
  }

  @Test
  fun mainViewModel_RequestInitialEmployeeListEvent_correctUiState() = runTest {
    // Given
    val expectedRemoteEmployees = repository.remoteEmployees.map {
      uiEmployeeMapper.mapToView(it)
    }

    val expectedUiState = MainUiState(
      isLoading = false,
      employees = expectedRemoteEmployees,
      failure = null
    )

    // When
    viewModel.onEvent(MainEvent.RequestInitialEmployeesList)

    // Then
    val uiState = viewModel.uiState.value
    assertEquals(expectedUiState, uiState)
  }

  @Test
  fun mainViewModel_DepartmentChangedEvent_correctUiState() = runTest{
    // Given
    val expectedEmployeesAfterDepartmentChanged = listOf(employeeQA).map {
      uiEmployeeMapper.mapToView(it)
    }

    val expectedUiState = MainUiState(
      isLoading = false,
      employees = expectedEmployeesAfterDepartmentChanged,
      failure = null
    )

    // When
    viewModel.onEvent(MainEvent.RequestInitialEmployeesList)
    viewModel.onEvent(MainEvent.DepartmentChanged("QA"))

    // Then
    val uiState = viewModel.uiState.value
    assertEquals(expectedUiState, uiState)
  }

}

class FakeRepository: EmployeeRepository {

  val remoteEmployees = mutableListOf(employeeAndroid, employeeQA)

  private val cachedEmployees: MutableStateFlow<List<Employee>> = MutableStateFlow(emptyList())

  override fun getAllEmployees(): Flow<List<Employee>> {
    return cachedEmployees
  }

  override suspend fun requestEmployees(): List<Employee> {
    return remoteEmployees
  }

  override suspend fun storeEmployees(employees: List<Employee>) {
    cachedEmployees.update {
      employees
    }
  }

  override fun getEmployeeByDepartment(department: String): Flow<List<Employee>> {
    TODO("Not yet implemented")
  }

  @TestOnly
  fun addEmployees(employees: List<Employee>) = cachedEmployees.update { it + employees }
}

private val employeeAndroid = Employee(
  id = "1",
  avatarUrl = "",
  firstName = "Анатолий",
  lastName = "Антонов",
  userTag = "AA",
  department = Department.ANDROID,
  position = "Разработчик",
  birthday = LocalDate.of(1990, 10, 9),
  phone = "123-456-789"
)

private val employeeQA = Employee(
  id = "2",
  avatarUrl = "",
  firstName = "Андрей",
  lastName = "Петров",
  userTag = "AП",
  department = Department.QA,
  position = "Тестировщик",
  birthday = LocalDate.of(1996, 11, 19),
  phone = "123-456-789"
)

private val employeeBackend = Employee(
  id = "3",
  avatarUrl = "",
  firstName = "Рихард",
  lastName = "Курант",
  userTag = "РП",
  department = Department.BACKEND,
  position = "",
  birthday = LocalDate.of(1888, 1, 8),
  phone = "123-456-789"
)