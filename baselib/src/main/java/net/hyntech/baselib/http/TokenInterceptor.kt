package net.hyntech.baselib.http

import net.hyntech.baselib.utils.LogUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.nio.charset.Charset

class TokenInterceptor : Interceptor {
    val UTF8: Charset = Charset.forName("UTF-8")

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        try {
            val charset: Charset = UTF8
            response.body?.also {
                val sBody = it.source().buffer.clone().readString(charset)
                val jsonObject: JSONObject = JSONObject(sBody)
                val code: String = jsonObject.getString("code")
                val msg: String = jsonObject.getString("msg")
                LogUtils.logGGQ("code : ${code}")
                LogUtils.logGGQ("msg : ${msg}")
                //if (code == 301) {
                    //ActivityUtils.startActivity(GlobalActivity::class.java)
               // }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.logGGQ("TokenInterceptor : error->${e.fillInStackTrace()}")
        }

        return response
    }
}