package net.hyntech.baselib.ext

import android.content.Context
import android.view.LayoutInflater
//自定义 Context 的 扩展方法
internal inline val Context.layoutInflater: LayoutInflater get() = LayoutInflater.from(this)
