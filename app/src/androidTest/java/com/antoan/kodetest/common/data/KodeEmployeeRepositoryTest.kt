package com.antoan.kodetest.common.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.antoan.kodetest.common.data.api.KodeApi
import com.antoan.kodetest.common.data.api.model.mappers.ApiEmployeeMapper
import com.antoan.kodetest.common.data.api.utils.FakeServer
import com.antoan.kodetest.common.data.cache.EmployeeDatabase
import com.antoan.kodetest.common.data.cache.RoomCache
import com.antoan.kodetest.common.data.cache.model.Cache
import com.antoan.kodetest.common.di.CacheModule
import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import com.google.common.truth.Truth.assertThat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit.Builder
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(CacheModule::class)
class KodeEmployeeRepositoryTest {
  private val fakeServer = FakeServer()
  private lateinit var repository: EmployeeRepository
  private lateinit var api: KodeApi

  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  lateinit var cache: Cache

  @Inject
  lateinit var database: EmployeeDatabase

  @Inject
  lateinit var retrofitBuilder: Builder

  @Inject
  lateinit var apiEmployeeMapper: ApiEmployeeMapper

  @Module
  @InstallIn(SingletonComponent::class)
  object TestCacheModule {
    @Provides
    fun provideRoomDatabase(): EmployeeDatabase {
      return Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().context,
        EmployeeDatabase::class.java
      ).allowMainThreadQueries().build()
    }
  }

  @Before
  fun setup() {
    fakeServer.start()

    hiltRule.inject()

    // Изменяю реальный endpoint для перенаправления вызовов на MockWebServer
    api = retrofitBuilder
      .baseUrl(fakeServer.baseEndpoint)
      .build()
      .create(KodeApi::class.java)

    cache = RoomCache(database.employeeDao())

    repository = KodeEmployeeRepository(
      api,
      cache,
      apiEmployeeMapper
    )
  }

  @After
  fun teardown() {
    fakeServer.shutdown()
  }

  @Test
  fun requestEmployees_success() = runBlocking {
    // Given
    val expectedEmployeeId = "e0fceffa-cef3-45f7-97c6-6be2e3705927"
    fakeServer.setHappyPathDispatcher()

    // When
    val employees = repository.requestEmployees()

    // Then
    val employee = employees.first()
    assertThat(employee.id).isEqualTo(expectedEmployeeId)
  }

  @Test
  fun insertEmployees_success() = runTest {
    // Given
    val expectedEmployeeId = "e0fceffa-cef3-45f7-97c6-6be2e3705927"
    fakeServer.setHappyPathDispatcher()
    val employees = repository.requestEmployees()
    val firstEmployee = employees.first()

    // When
    repository.storeEmployees(listOf(firstEmployee))

    // Then
    val employee = repository.getEmployees().first()
//    assertThat(employee.first().id).isEqualTo(expectedEmployeeId)
    assertEquals(employee.first().id, expectedEmployeeId)
  }
}