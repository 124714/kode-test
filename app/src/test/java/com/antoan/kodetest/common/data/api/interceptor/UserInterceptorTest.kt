package com.antoan.kodetest.common.data.api.interceptor

import com.antoan.kodetest.common.data.api.ApiConstants
import com.antoan.kodetest.common.data.api.ApiParameters
import com.antoan.kodetest.common.data.api.utils.JsonReader
import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserInterceptorTest {

  private lateinit var mockWebServer: MockWebServer
  private lateinit var userInterceptor: UserInterceptor
  private lateinit var okHttpClient: OkHttpClient

  private val endpointSeparator = "/"
  private val usersEndpointPath = endpointSeparator + ApiConstants.USERS_ENDPOINT

  @Before
  fun setup() {
    mockWebServer = MockWebServer()

    mockWebServer.start(8080)

    userInterceptor = UserInterceptor()

    okHttpClient = OkHttpClient()
      .newBuilder()
      .addInterceptor(userInterceptor)
      .build()
  }

  @After
  fun teardown() {
    mockWebServer.shutdown()
  }

  @Test
  fun userInterceptor_addAcceptAndPreferHeaders() {

    // Given
    mockWebServer.dispatcher = getDispatcher()

    // When
    okHttpClient.newCall(
      Request.Builder()
        .url(mockWebServer.url(ApiConstants.USERS_ENDPOINT))
        .build()
    ).execute()

    // Then
    val usersRequest = mockWebServer.takeRequest()

    with(usersRequest) {
      assertThat(method).isEqualTo("GET")
      assertThat(path).isEqualTo(usersEndpointPath)
      assertThat(getHeader(ApiParameters.ACCEPT_HEADER)).isEqualTo(ApiParameters.ACCEPT_HEADER_VALUE)
      assertThat(getHeader(ApiParameters.PREFER_HEADER)).isEqualTo(ApiParameters.PREFER_HEADER_VALUE_SUCCESS)
    }

  }

  private fun getDispatcher() = object: Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
      return when(request.path) {
        usersEndpointPath -> MockResponse().setResponseCode(200).setBody(JsonReader.getJson("users.json"))
        else -> MockResponse().setResponseCode(404)
      }
    }
  }
}