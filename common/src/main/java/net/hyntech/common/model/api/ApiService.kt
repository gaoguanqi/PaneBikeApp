package net.hyntech.common.model.api

import net.hyntech.common.model.entity.CenterEntity
import net.hyntech.common.model.entity.CommonEntity
import net.hyntech.common.model.entity.UserEntity
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService:BaseApi {

    @Headers("urlname:center")
    @GET("/index.json")
    suspend fun getOrgList(): CenterEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_LOGIN)
    suspend fun loginPhone(@Body requestBody: RequestBody): UserEntity

    @Headers("urlname:hyntech")
    @POST("/antitheft/v1/sms/usual/send_code.thtml")
    suspend fun getVerifyCode(@Body requestBody: RequestBody): CommonEntity
}