package com.antoan.kodetest.common.data.di

import com.antoan.kodetest.common.data.api.ApiConstants
import com.antoan.kodetest.common.data.api.KodeApi
import com.antoan.kodetest.common.data.api.interceptor.LoggingInterceptor
import com.antoan.kodetest.common.data.api.interceptor.NetworkStatusInterceptor
import com.antoan.kodetest.common.data.api.interceptor.UserInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

  @Provides
  @Singleton
  fun provideApi(builder: Retrofit.Builder): KodeApi {
    return builder
      .build()
      .create(KodeApi::class.java)
  }

  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
    return Retrofit.Builder()
      .baseUrl(ApiConstants.BASE_ENDPOINT)
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create())
  }

  @Provides
  fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    networkStatusInterceptor: NetworkStatusInterceptor,
    authenticationInterceptor: UserInterceptor
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(networkStatusInterceptor)
      .addInterceptor(authenticationInterceptor)
      .addInterceptor(httpLoggingInterceptor)
      .build()
  }

  @Provides
  fun provideHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(loggingInterceptor)

    interceptor.level = HttpLoggingInterceptor.Level.BODY

    return interceptor
  }

}