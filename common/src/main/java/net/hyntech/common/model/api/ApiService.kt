package net.hyntech.common.model.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("/login/phone")
    suspend fun loginPhone(@Query("phone") phone: String, @Query("pwd") password: String): String
}