package com.antoan.kodetest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class KodeTestApplication: Application() {
  override fun onCreate() {
    super.onCreate()
  }

}