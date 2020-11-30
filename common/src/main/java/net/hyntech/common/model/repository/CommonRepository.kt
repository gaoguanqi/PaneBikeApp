package net.hyntech.common.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.hyntech.baselib.base.BaseRepository
import net.hyntech.common.model.RetrofitClient
import net.hyntech.common.utils.HttpParamsUtils
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

open class CommonRepository:BaseRepository() {

    private val retrofitClient = RetrofitClient.service

    override fun getPublicParams(): WeakHashMap<String, Any> {
        return HttpParamsUtils.addPublicRequestParams(true)
    }

    suspend fun getOrgList() = withContext(Dispatchers.IO) {
        retrofitClient.getOrgList()
    }

    suspend fun loginPhone(params:WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.loginPhone(params2Body(params))
    }




}