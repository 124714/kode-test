package com.antoan.kodetest.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
  @Json(name = "items")
  val items: List<ApiEmployee>?
) {
  val isSuccess = !items.isNullOrEmpty()
}
 