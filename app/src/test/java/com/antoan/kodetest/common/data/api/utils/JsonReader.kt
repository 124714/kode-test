package com.antoan.kodetest.common.data.api.utils

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import java.io.IOException
import java.io.InputStream

object JsonReader {
  fun getJson(path: String): String {
    println("!!!!!GetJson!!!!")
    return try {
      val context = InstrumentationRegistry.getInstrumentation().context
      val jsonStream: InputStream = context.assets.open(path)
      val testing = String(jsonStream.readBytes())
      println(testing)
      testing
    } catch (exception: IOException) {
      Log.e("FakeServer2", "Error reading network response json asset")
      throw exception
    }
  }
}