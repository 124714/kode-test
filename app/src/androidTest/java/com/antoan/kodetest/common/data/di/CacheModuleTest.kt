package com.antoan.kodetest.common.data.di

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.antoan.kodetest.common.data.cache.EmployeeDatabase
import com.antoan.kodetest.common.data.cache.RoomCache
import com.antoan.kodetest.common.data.cache.daos.EmployeeDao
import com.antoan.kodetest.common.data.cache.model.Cache
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.junit.Assert.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TestCacheModule {

  @Binds
  abstract fun bindCache(cache: RoomCache): Cache

  companion object {
    @Singleton
    @Provides
    fun provideRoomDatabase(): EmployeeDatabase {
      return Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().context,
        EmployeeDatabase::class.java
      ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideEmployeeDao(database: EmployeeDatabase): EmployeeDao = database.employeeDao()
  }
}