package net.hyntech.common.vm

import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.repository.CommonRepository
import net.hyntech.common.utils.HttpParamsUtils
import org.json.JSONObject

class BrowserViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    fun getUserInfo():String? {
        return JSONObject(HttpParamsUtils.addPublicRequestParams(true)).toString()
    }

}