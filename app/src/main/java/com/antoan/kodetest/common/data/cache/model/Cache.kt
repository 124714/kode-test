package com.antoan.kodetest.common.data.cache.model

import kotlinx.coroutines.flow.Flow

interface Cache {
  fun getEmployees(): Flow<List<CachedEmployee>>
  fun storeEmployees(employees: List<CachedEmployee>)
}