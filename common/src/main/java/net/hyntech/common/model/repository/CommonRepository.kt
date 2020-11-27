package net.hyntech.common.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.hyntech.baselib.base.BaseRepository
import net.hyntech.common.model.RetrofitClient

open class CommonRepository:BaseRepository() {

    private val retrofitClient = RetrofitClient.service


    suspend fun loginPhone(phone: String, pwd: String) = withContext(Dispatchers.IO) {
        retrofitClient.loginPhone(phone,pwd)
    }



}