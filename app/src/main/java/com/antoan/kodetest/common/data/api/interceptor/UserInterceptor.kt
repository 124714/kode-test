package com.antoan.kodetest.common.data.api.interceptor

import com.antoan.kodetest.common.data.api.ApiParameters
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class UserInterceptor @Inject constructor(): Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {

    val newRequest = chain.appendHeaders()

    return chain.proceed(newRequest)
  }

  private fun Interceptor.Chain.appendHeaders(): Request {
    return request()
      .newBuilder()
      .addHeader(ApiParameters.ACCEPT_HEADER, ApiParameters.ACCEPT_HEADER_VALUE)
      .addHeader(ApiParameters.PREFER_HEADER, ApiParameters.PREFER_HEADER_VALUE_SUCCESS)
      .build()
  }
}