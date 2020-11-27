package net.hyntech.common.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.hyntech.baselib.base.BaseRepository
import net.hyntech.common.model.RetrofitClient
import okhttp3.RequestBody

open class CommonRepository:BaseRepository() {

    private val retrofitClient = RetrofitClient.service

    suspend fun loginPhone(requestBody: RequestBody) = withContext(Dispatchers.IO) {
        retrofitClient.loginPhone(requestBody)
    }



}