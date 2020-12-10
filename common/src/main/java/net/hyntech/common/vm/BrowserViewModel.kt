package net.hyntech.common.vm

import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.model.repository.CommonRepository

class BrowserViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    fun getUserInfo():String? {
        return AppDatabase.getInstance(BaseApp.instance).userDao().getCurrentUser()?.accessToken
    }

}