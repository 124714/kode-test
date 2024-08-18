package com.antoan.kodetest.common.data.api.interceptor

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

private const val HTTP_LOG = "HTTP_LOG"

class LoggingInterceptor @Inject constructor(): HttpLoggingInterceptor.Logger {
  override fun log(message: String) {
    Log.d(HTTP_LOG, message)
  }
}