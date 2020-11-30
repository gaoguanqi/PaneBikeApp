package net.hyntech.baselib.base

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.*

abstract class BaseRepository {


    abstract fun getPublicParams():WeakHashMap<String,Any>


    fun params2Body(params: WeakHashMap<String, Any>,addPublicParams:Boolean = true): RequestBody {
        //添加公共请求参数
        if(addPublicParams){
            params.putAll(getPublicParams())
        }
        return JSONObject(params.toMap()).toString().toRequestBody("application/json;charset=utf-8".toMediaType())
    }

}