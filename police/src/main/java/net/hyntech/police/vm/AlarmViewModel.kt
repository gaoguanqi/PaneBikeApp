package net.hyntech.police.vm

import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.repository.CommonRepository
import java.util.*

class AlarmViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    private var pageNo: Int = 1
    private var pageSize: Int = 10
    var lastPage: Boolean = true


    /**
     * | 字段        |类型  |必须 |描述| 取值 |
    | --------   | -----  | :---- |:----  |
    | messageId     | String|  否  |消息id|-|
    | messageType     | String|  否  |消息类型|alarm车主报警，move位移变化，enclosure围栏报警|
     */
    fun getAlarmInfoList(messageId:String,messageType:String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("messageId",messageId)
            params.put("messageType",messageType)
            repository.searchDecive(params)
        }, success = {

        })
    }

}