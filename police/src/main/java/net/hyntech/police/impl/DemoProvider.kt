package net.hyntech.police.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.provider.IDemoProvider

@Route(path = ARouterConstants.DEMO_OBJ)
class DemoProvider:IDemoProvider {
    override fun getObj(): String {
        return "我是警用端"
    }

    override fun init(context: Context?) {

    }


}