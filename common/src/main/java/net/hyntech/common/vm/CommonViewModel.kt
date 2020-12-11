package net.hyntech.common.vm

import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.model.repository.CommonRepository

/**
 * 民用·警用 公共业务ViewModel
 */
open class CommonViewModel:BaseViewModel() {

    protected val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


}