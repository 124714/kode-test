package com.antoan.kodetest.common.di

import android.content.Context
import androidx.room.Room
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
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

  @Binds
  abstract fun bindCache(cache: RoomCache): Cache

  companion object {
    @Provides
    @Singleton
    fun provideDatabase(
      @ApplicationContext context: Context
    ): EmployeeDatabase {
      return Room.databaseBuilder(
        context,
        EmployeeDatabase::class.java,
        "employee.db"
      ).build()
    }

    @Provides
    fun provideEmployeeDao(database: EmployeeDatabase): EmployeeDao = database.employeeDao()
  }

}