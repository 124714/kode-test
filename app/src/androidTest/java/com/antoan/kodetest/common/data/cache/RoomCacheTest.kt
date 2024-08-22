package com.antoan.kodetest.common.data.cache

import android.content.Context
import android.icu.text.Transliterator.Position
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
  fun roomCache_storeEmployeesAndGetEmployees_success() = runTest {
    val employeeEntities = listOf(
      testCachedEmployee("1", "Joshua Bloh"),
      testCachedEmployee("2", "Robert Martin"),
    )

    cache.storeEmployees(employeeEntities)

    val employeeEntitiesFromDb = cache.getAllEmployees().first()

    assertEquals(employeeEntities.size, employeeEntitiesFromDb.size)
  }

  @Test fun roomCache_getEmployeesByDepartment_success() {

  }
}

private fun testCachedEmployee(
  id: String,
  name: String,
  position: String = ""
) =
  CachedEmployee(
    employeeId = id,
    firstName = name,
    avatarUrl = "http://fakeAvatar.com",
    lastName = "****",
    position = position,
    department = "****",
    birthday = "****",
    phone = "****",
    userTag = "****"
  )