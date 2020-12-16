package net.hyntech.common.utils

import android.text.TextUtils

class CommonUtils {
    companion object{
        fun coverIDCard(idCard:String?):String{
            if(TextUtils.isEmpty(idCard)){
                return ""
            }
            if(idCard!!.length >= 16){
                return idCard.substring(0,6) + "********" + idCard!!.substring(idCard!!.length - 4,idCard!!.length)
            }else{
                return ""
            }
        }

        fun splitDate(date:String?):String{
            if(TextUtils.isEmpty(date)){
                return ""
            }
            if(date?.contains(" ")?:false){
                return date?.split(" ")?.first()?:""
            }
            return ""
        }
    }
}