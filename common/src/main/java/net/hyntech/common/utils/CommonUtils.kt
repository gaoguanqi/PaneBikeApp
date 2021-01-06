package net.hyntech.common.utils

import android.text.TextUtils
import org.jetbrains.anko.collections.forEachWithIndex

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

        fun splitDateArray(date:String?):List<String>{
            if(TextUtils.isEmpty(date)){
                return emptyList()
            }
            if(date?.contains(" ")?:false){
                return date?.split(" ")?:emptyList()
            }
            return emptyList()
        }

        fun splitPic(url:String?):String{
            if(TextUtils.isEmpty(url)){
                return ""
            }
            if(url?.contains(",")?:false){
              return url?.split(",")?.first()?:""
            }else{
                return url?:""
            }
        }

        fun splitPicList(url: String?):List<String>{
            val list:MutableList<String> = mutableListOf()
            if(!TextUtils.isEmpty(url)){
                if(url?.contains(",")?:false){
                    list.addAll(url!!.split(","))
                }else{
                    list.add(url!!)
                }
            }
            return list
        }

        //便民服务
        fun filterShopType(type:String,shopType:String?):Boolean{
            if(TextUtils.isEmpty(shopType)){
                return false
            }
            return shopType?.contains(type)?:false
        }

        //list 拼接字符
        private fun listSpliceChar(list:List<String>, c:String):String{
            val sb = StringBuffer()
            if(list.isNotEmpty()){
                list.forEachWithIndex { i, s ->
                    if(i < list.size -1){
                        sb.append(list.get(i) + c)
                    }else{
                        sb.append(list.get(i))
                    }
                }
            }
            return sb.toString()
        }

        //list 拼接逗号
        fun listSpliceComma(list:List<String>):String{
            return listSpliceChar(list,",")
        }
    }
}