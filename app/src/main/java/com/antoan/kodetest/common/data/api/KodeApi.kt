package com.antoan.kodetest.common.data.api

import com.antoan.kodetest.common.data.api.model.ApiResponse
import retrofit2.http.GET

interface KodeApi {
  @GET(ApiConstants.USERS_ENDPOINT)
  suspend fun getAllUsers(): ApiResponse
}