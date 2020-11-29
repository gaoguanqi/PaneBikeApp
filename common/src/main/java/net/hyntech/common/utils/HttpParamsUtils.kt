package net.hyntech.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import android.telephony.TelephonyManager.*
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.TimeUtils
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.error.AppException
import net.hyntech.common.global.Constants
import java.util.*

class HttpParamsUtils {
    companion object{

        fun addPublicRequestParams(addToken:Boolean = true):MutableMap<String,String>{
            val map:MutableMap<String,String> = mutableMapOf()
            val accessToken = SPUtils.getInstance(BaseApp.instance.getAppPackage()).getString(Constants.SaveInfoKey.ACCESS_TOKEN)
            val currentTimeStr = TimeUtils.getNowString()
            val uuidStr = UUID.randomUUID().toString()
            val sigStr = MD5Utils.sigMD5Hex(accessToken,uuidStr,currentTimeStr,addToken)
            if(addToken){
                map.put("accessToken",accessToken)
            }
            map.put("timestamp",currentTimeStr)
            map.put("nonce",uuidStr)
            map.put("sig",sigStr)
            map.put("mobileTye","AN-Android")
            map.put("netType",getNetWorkStatusName(BaseApp.instance))
            map.put("appVersion",AppUtils.getAppVersionName(BaseApp.instance.getAppPackage()))
            return map
        }


        private fun getNetWorkStatusName(context: Context):String{
            var netWorkType:String = "UNKNOWN"
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if(networkInfo != null && networkInfo.isConnected){
                val type = networkInfo.type
                if(type == ConnectivityManager.TYPE_WIFI){
                    netWorkType = "WIFI"
                }else if(type == ConnectivityManager.TYPE_MOBILE){
                    netWorkType = getNetTypeClassName(context)
                }
            }

            return netWorkType
        }

        private fun getNetTypeClassName(context: Context):String{
            var networkType = 0
            try {
                val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                networkType = tm.networkType
            }catch (e:AppException){
                e.fillInStackTrace()
            }
            when(networkType){
                NETWORK_TYPE_GPRS,NETWORK_TYPE_GSM,NETWORK_TYPE_EDGE,NETWORK_TYPE_CDMA,NETWORK_TYPE_1xRTT,NETWORK_TYPE_IDEN ->{
                    return "2G"
                }
                NETWORK_TYPE_UMTS,NETWORK_TYPE_EVDO_0,NETWORK_TYPE_EVDO_A,NETWORK_TYPE_HSDPA,NETWORK_TYPE_HSUPA,NETWORK_TYPE_HSPA,NETWORK_TYPE_EVDO_B,NETWORK_TYPE_EHRPD,NETWORK_TYPE_HSPAP,NETWORK_TYPE_TD_SCDMA ->{
                    return "3G"
                }
                NETWORK_TYPE_LTE,NETWORK_TYPE_IWLAN,19 ->{
                    return "4G"
                }
                else ->{
                    return "UNKNOWN"
                }
            }
        }
    }
}