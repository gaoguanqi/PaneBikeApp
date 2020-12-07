package net.hyntech.common.model.api

import net.hyntech.common.model.entity.*
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService:BaseApi {

    @Headers("urlname:center")
    @GET("/index.json")
    suspend fun getOrgList(): CenterEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_USER_LOGIN)
    suspend fun loginPhone(@Body requestBody: RequestBody): UserEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_SMS_VERIFY_CODE)
    suspend fun getVerifyCode(@Body requestBody: RequestBody): CommonEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_MESSAGE_COUNT)
    suspend fun getMessageCount(@Body requestBody: RequestBody): MessageCountEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_USER_INFO)
    suspend fun getUserInfo(@Body requestBody: RequestBody): UserInfoEntity

    @Headers("urlname:hyntech")
    @POST(ApiURL.URL_EBIKE_LOCK)
    suspend fun ebikeLock(@Body requestBody: RequestBody): CommonEntity



}