package com.antoan.kodetest.common.data.api

object ApiConstants {
  const val BASE_ENDPOINT = "https://stoplight.io/mocks/kode-api/trainee-test/331141861/"
  const val USERS_ENDPOINT = "users"
}

object ApiParameters {
  /**
   * Заголовки для получения успешного ответа
   */
  const val ACCEPT_HEADER = "Accept"
  const val ACCEPT_HEADER_VALUE = "application/json, application/xml"
  const val PREFER_HEADER = "Prefer"
  const val PREFER_HEADER_VALUE_SUCCESS = "code=200, example=success"

  /**
   * Заголовки для тестирования
   */
  const val PREFER_HEADER_VALUE_TEST = "code=200, dynamic=true"

  /**
   * Заголовки для ошибки [500]
   */
  const val PREFER_HEADER_VALUE_ERROR = "code=500, example=error-500"
}