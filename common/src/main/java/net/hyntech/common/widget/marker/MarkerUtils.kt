package net.hyntech.common.widget.marker

import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import net.hyntech.baselib.utils.LogUtils

class MarkerUtils {

    companion object{

        fun getAngle(from:LatLng,to:LatLng):Double{
            val slope = getSlope(from,to)
            if(slope == Double.MAX_VALUE){
                if(to.latitude > from.latitude){
                    return 0.00
                }else{
                    return 180.00
                }
            }

            var deltAngle = 0.00
            if((to.latitude - from.latitude) * slope < 0){
                deltAngle = 180.00
            }

            val radio = Math.atan(slope)
            val angle = 180 * (radio/ Math.PI) + deltAngle - 90
            return angle
        }


        //算斜率
        fun getSlope(from:LatLng,to:LatLng):Double{
            if(from.longitude == to.longitude){
                return Double.MAX_VALUE
            }
            val slope = ((to.latitude - from.latitude)/(to.longitude - from.longitude))
            return slope
        }

        fun splitDots(f:LatLng,t:LatLng):List<LatLng>{
            val list = mutableListOf<LatLng>()
            val a_x = f.latitude
            val a_y = f.longitude
            val b_x = t.latitude
            val b_y = t.longitude


            val distance= DistanceUtil.getDistance(f,t)
            val partX = Math.abs(a_x - b_x) / distance
            val partY = Math.abs(a_y - b_y) / distance

            for (i in 0..distance.toInt()){
                LogUtils.logGGQ("--dot-->>>${i}")
                if (i % 300 == 0) {
                    val x: Double = if (a_x < b_x) a_x + partX * i else if (a_x > b_x) a_x - partX * i else a_x
                    val y: Double = if (a_y < b_y) a_y + partY * i else if (a_y > b_y) a_y - partY * i else a_y
                    list.add(LatLng(x,y))
                }
            }
            list.add(t)
            return list
        }

    }



}