package com.antoan.logging

import android.util.Log
import timber.log.Timber


object Logger {
  private val logger by lazy {
    TimberLogging()
  }

  fun init() {
    Timber.plant(logger)
  }

  fun d(message: String, t: Throwable? = null) = logger.d(t, message)

  fun i(message: String, t: Throwable? = null) = logger.i(t, message)

  fun e(t: Throwable? = null, message: String) = logger.e(t, message)

  fun wtf(t: Throwable? = null, message: String) = logger.wtf(t, message)
}

class TimberLogging : Timber.Tree() {
  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    when (priority) {
      Log.WARN -> logWarning(priority, tag, message)
      Log.ERROR -> logError(t, priority, tag, message)
    }
  }

  private fun logWarning(priority: Int, tag: String?, message: String) {
    // Log to external service like Crashlytics
  }

  private fun logError(t: Throwable?, priority: Int, tag: String?, message: String) {
    // Log to external service like Crashlytics

    t?.let {
      // Log to external service like Crashlytics
    }
  }
}