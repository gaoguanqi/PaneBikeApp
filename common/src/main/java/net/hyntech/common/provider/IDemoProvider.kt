package net.hyntech.common.provider

import com.alibaba.android.arouter.facade.template.IProvider

interface IDemoProvider:IProvider {

    fun getObj():String
}