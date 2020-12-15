package net.hyntech.common.utils

import android.text.TextUtils
import com.blankj.utilcode.constant.TimeConstants
import com.blankj.utilcode.util.TimeUtils
import net.hyntech.baselib.utils.LogUtils

class DateUtils {
    companion object{
        fun isExpire(date:String?):Boolean{
            var result:Boolean = false
            if(TextUtils.isEmpty(date)) return result
            try {
                val dateTime = TimeUtils.string2Date(date,"yyyy-MM-dd").time
                val nowTime = TimeUtils.getNowDate().time
                val datas = Math.abs((nowTime - dateTime))/(1000*60*60*24)
                if(datas < 31){
                    result = true
                }
            }catch (e:Exception){
                e.fillInStackTrace()
            }
            return result
        }
    }
}