package com.antoan.kodetest.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiEmployee(
    @Json(name = "id")
    val id: String?,
    @Json(name = "avatarUrl")
    val avatarUrl: String?,
    @Json(name = "birthday")
    val birthday: String?,
    @Json(name = "department")
    val department: String?,
    @Json(name = "firstName")
    val firstName: String?,
    @Json(name = "lastName")
    val lastName: String?,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "position")
    val position: String?,
    @Json(name = "userTag")
    val userTag: String?
)

