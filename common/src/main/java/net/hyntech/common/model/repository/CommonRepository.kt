package net.hyntech.common.model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.hyntech.baselib.base.BaseRepository
import net.hyntech.common.model.RetrofitClient
import net.hyntech.common.utils.HttpParamsUtils
import java.util.*

open class CommonRepository:BaseRepository() {

    private val retrofitClient = RetrofitClient.service

    override fun getPublicParams(addToken:Boolean): WeakHashMap<String, Any> {
        return HttpParamsUtils.addPublicRequestParams(addToken)
    }

    suspend fun getOrgList() = withContext(Dispatchers.IO) {
        retrofitClient.getOrgList()
    }

    suspend fun loginPhone(params:WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.loginPhone(params2Body(params))
    }

    suspend fun getVerifyCode(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getVerifyCode(params2Body(params,true,false))
    }

    suspend fun getMessageCount(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getMessageCount(params2Body(params))
    }

    suspend fun getUserInfo(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.getUserInfo(params2Body(params))
    }

    suspend fun ebikeLock(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.ebikeLock(params2Body(params))
    }

}