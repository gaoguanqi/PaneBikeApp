package net.hyntech.common.model

import net.hyntech.baselib.app.config.Config
import net.hyntech.baselib.http.BaseRetrofitClient
import net.hyntech.baselib.http.HeaderInterceptor
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.model.api.ApiService
import net.hyntech.common.utils.NetWorkUtils
import okhttp3.OkHttpClient

object RetrofitClient : BaseRetrofitClient() {

    val service by lazy { getService(ApiService::class.java, Config.BASE_URL) }
    override fun getHeader(): HeaderInterceptor {
        val params = NetWorkUtils.addPublicRequestParams()
        return HeaderInterceptor(params)
    }

    override fun handleBuilder(builder: OkHttpClient.Builder) {

    }
}