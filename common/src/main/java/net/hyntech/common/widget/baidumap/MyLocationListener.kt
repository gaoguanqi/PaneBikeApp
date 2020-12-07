package net.hyntech.common.widget.baidumap

import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation

class MyLocationListener(val listener: LocationListener):BDAbstractLocationListener() {

    override fun onReceiveLocation(bdLocation: BDLocation?) {
        if(bdLocation != null){
            listener.onReceive(bdLocation)
        }
    }

    interface LocationListener{
        fun onReceive(bdLocation: BDLocation)
    }
}