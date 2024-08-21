package com.antoan.kodetest.common.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.antoan.kodetest.common.data.api.KodeApi
import com.antoan.kodetest.common.data.api.model.mappers.ApiEmployeeMapper
import com.antoan.kodetest.common.data.cache.model.Cache
import com.antoan.kodetest.common.data.cache.model.CachedEmployee
import com.antoan.kodetest.common.domain.model.Employee
import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Определяет границу (domain | data), так как реализует интерфейс domain
 */

@RequiresApi(Build.VERSION_CODES.O)
class KodeEmployeeRepository @Inject constructor(
  private val api: KodeApi,
  private val cache: Cache,
  private val apiEmployeeMapper: ApiEmployeeMapper
) : EmployeeRepository {


  override fun getAllEmployees(): Flow<List<Employee>> {
    return cache
      .getAllEmployees()
      .distinctUntilChanged()
      .map { employeeList -> employeeList.map(CachedEmployee::toDomain) }
  }

  override suspend fun requestEmployees(): List<Employee> {
    val apiResponse = api.getAllUsers()
    return apiResponse.items?.map { apiEmployeeMapper.mapToDomain(it) }.orEmpty()
  }

  override fun storeEmployees(employees: List<Employee>) {
    cache.storeEmployees(
        employees.map(CachedEmployee::fromDomain)
      )
  }
}