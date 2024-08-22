package com.antoan.kodetest.common.data.cache.model

import kotlinx.coroutines.flow.Flow

interface Cache {
  fun getAllEmployees(): Flow<List<CachedEmployee>>
  suspend fun storeEmployees(employees: List<CachedEmployee>)
}