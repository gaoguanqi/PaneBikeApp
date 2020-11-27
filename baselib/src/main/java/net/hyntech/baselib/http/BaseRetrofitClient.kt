package net.hyntech.baselib.http

import android.util.Pair
import com.blankj.utilcode.util.NetworkUtils
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.app.config.Config
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseRetrofitClient {
    companion object CLIENT {
        private const val TIME_OUT:Long = 60000L
    }

    abstract fun getHeader():HeaderInterceptor

    protected val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            if (Config.CONFIG_DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }

            //添加公共请求头
//            val header = HeaderInterceptor(map)
            builder.addInterceptor(TokenInterceptor())
            builder.addInterceptor(logging)
//            builder.addInterceptor(getHeader())

//                    ssl
//            builder.sslSocketFactory(SSLHelper.sslSocketFactory, SSLHelper.trustManager).hostnameVerifier(SSLHelper.hostnameVerifier)

//            val sslContext = SSLHelper.getSSLContext(caIn, ksIn, ksPwd)
//            builder.sslSocketFactory(sslContext.socketFactory).hostnameVerifier(SSLHelper.hostnameVerifier)

                    //ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
                .cookieJar(
                    PersistentCookieJar(
                        SetCookieCache(), SharedPrefsCookiePersistor(
                    BaseApp.instance)
                    )
                )
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            handleBuilder(builder)
            return builder.build()
        }

    /**
     * 以便对builder可以再扩展
     */
    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)

     fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
}

infix fun <A, B> A.t(that: B): Pair<A, B> = Pair(this, that)