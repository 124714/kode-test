package com.antoan.kodetest.common.data

import com.antoan.kodetest.common.data.api.KodeApi
import com.antoan.kodetest.common.data.api.model.mappers.ApiEmployeeMapper
import com.antoan.kodetest.common.data.api.utils.FakeServer
import com.antoan.kodetest.common.data.cache.model.Cache
import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit.Builder
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject


@HiltAndroidTest
class KodeEmployeeRepositoryTest {
  private val fakeServer = FakeServer()
  private lateinit var repository: EmployeeRepository
  private lateinit var api: KodeApi

  @get:Rule
  val hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var cache: Cache

  @Inject
  lateinit var retrofitBuilder: Builder

  @Inject
  lateinit var apiEmployeeMapper: ApiEmployeeMapper

  @Before
  fun setup() {
    fakeServer.start()

    hiltRule.inject()


    // Изменяю реальный endpoint для перенаправления вызовов на MockWebServer
    api = retrofitBuilder
      .baseUrl(fakeServer.baseEndpoint)
      .build()
      .create(KodeApi::class.java)

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
}