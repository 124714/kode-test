package com.antoan.kodetest.common.data.cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.antoan.kodetest.common.data.cache.model.Cache
import com.antoan.kodetest.common.data.cache.model.CachedEmployee
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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

  @Test
  fun roomCache_getEmployeesByDepartment_success() = runTest {
    val employeesEntities = listOf(
      testCachedEmployee("1", "Joshua Bloh 1", "ANDROID"),
      testCachedEmployee("2", "Joshua Bloh 2", "ANDROID"),
      testCachedEmployee("3", "Joshua Bloh 3", "QA"),
      testCachedEmployee("4", "Joshua Bloh 4", "QA"),
      testCachedEmployee("5", "Joshua Bloh 5", "ANDROID"),
    )

    val expectedDepartment = "ANDROID"
    val expectedAmountEmployees = 3

    cache.storeEmployees(employeesEntities)

    val employeeEntitiesFromDb = cache.getEmployeesByDepartment("ANDROID").first()

    assertEquals(expectedAmountEmployees, employeeEntitiesFromDb.size)
    assertTrue(employeeEntitiesFromDb.all { it.department == expectedDepartment} )
  }
}

private fun testCachedEmployee(
  id: String,
  name: String,
  department: String = ""
) =
  CachedEmployee(
    employeeId = id,
    firstName = name,
    avatarUrl = "http://fakeAvatar.com",
    lastName = "****",
    position = "position",
    department = department,
    birthday = "****",
    phone = "****",
    userTag = "****"
  )