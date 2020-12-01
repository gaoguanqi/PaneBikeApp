package net.hyntech.common.model

import net.hyntech.baselib.app.config.Config
import net.hyntech.baselib.http.BaseRetrofitClient
import net.hyntech.baselib.http.HeaderInterceptor
import net.hyntech.common.model.api.ApiService
import net.hyntech.common.utils.BaseURLInterceptor
import net.hyntech.common.utils.HttpParamsUtils
import okhttp3.OkHttpClient

object RetrofitClient : BaseRetrofitClient() {

    val service by lazy { getService(ApiService::class.java, Config.BASE_URL) }


    override fun handleBuilder(builder: OkHttpClient.Builder) {
        //添加header
        val header = HttpParamsUtils.addPublicRequestParams()
        builder.addInterceptor(HeaderInterceptor(header))

        //添加BaseURL 拦截器
        builder.addInterceptor(BaseURLInterceptor())

    }
}