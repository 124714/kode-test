package com.antoan.kodetest.common.di

import com.antoan.kodetest.common.data.KodeEmployeeRepository
import com.antoan.kodetest.common.domain.repository.EmployeeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

  @Binds
  abstract fun bindEmployeeRepository(repository: KodeEmployeeRepository): EmployeeRepository
}