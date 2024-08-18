package com.antoan.kodetest.common.data.cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.antoan.kodetest.common.data.cache.model.Cache
import com.antoan.kodetest.common.data.cache.model.CachedEmployee
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RoomCacheTest {
  private lateinit var db: EmployeeDatabase
  private lateinit var cache: Cache

  @Before
  fun createDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(
      context,
      EmployeeDatabase::class.java
    ).build()

    cache = RoomCache(db.employeeDao())
  }

  @After
  fun closeDb() = db.close()

  @Test
  fun roomCache_storeEmployeesAndGetEmployees() = runTest {
    val employeeEntities = listOf(
      testCachedEmployee("1", "1"),
      testCachedEmployee("2", "2"),
    )

    cache.storeEmployees(employeeEntities)

    val employeeEntitiesFromDb = cache.getEmployees().first()

    assertEquals(employeeEntities.size, employeeEntitiesFromDb.size)
  }
}

private fun testCachedEmployee(
  id: String = "0",
  name: String
) =
  CachedEmployee(
    employeeId = id,
    firstName = name,
    avatarUrl = "http://fakeAvatar.com",
    lastName = "****",
    position = "****",
    department = "****",
    birthday = "****",
    phone = "****"
  )