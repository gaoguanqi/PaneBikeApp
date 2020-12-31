package net.hyntech.police.vm

import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.repository.CommonRepository

class AlarmViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    private var pageNo: Int = 1
    private var pageSize: Int = 10
    var lastPage: Boolean = true


    fun getAlarmInfoList() {

    }

}