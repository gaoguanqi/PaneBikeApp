package net.hyntech.common.model.api

import net.hyntech.common.model.entity.CommonEntity
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("/app/v1/user/usual/login.thtml")
    suspend fun loginPhone(@Body requestBody: RequestBody): CommonEntity
}