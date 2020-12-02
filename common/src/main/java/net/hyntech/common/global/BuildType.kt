package net.hyntech.common.global

import android.text.TextUtils
import net.hyntech.baselib.app.BaseApp

class BuildType {
    companion object{
        fun isUsual():Boolean{
            return TextUtils.equals(Constants.BundleKey.EXTRA_USUAL,BaseApp.instance.getBuildType())
        }

        fun isPolice():Boolean{
            return TextUtils.equals(Constants.BundleKey.EXTRA_POLICE,BaseApp.instance.getBuildType())
        }
    }
}