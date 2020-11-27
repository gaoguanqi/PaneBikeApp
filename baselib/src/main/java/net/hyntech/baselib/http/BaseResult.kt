package net.hyntech.baselib.http

import java.io.Serializable

/**
 * 响应数据封装
 * 这里暂无用
 */
open class BaseResult<out T> : Serializable {
    val code:String = ""
    val msg:String = "未知错误!"
    val data:T? = null
}