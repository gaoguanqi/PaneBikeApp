package net.hyntech.common.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.hyntech.baselib.base.BaseRepository
import net.hyntech.common.model.RetrofitClient
import net.hyntech.common.utils.HttpParamsUtils
import okhttp3.RequestBody
import java.util.*

open class CommonRepository:BaseRepository() {

    private val retrofitClient = RetrofitClient.service

    suspend fun loginPhone(params:WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.loginPhone(params2Body(params))
    }

    override fun getPublicParams(): WeakHashMap<String, Any> {
        return HttpParamsUtils.addPublicRequestParams(true)
    }


}