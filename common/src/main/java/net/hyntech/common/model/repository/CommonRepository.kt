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


}