package net.hyntech.police.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.handler.MapViewHandler
import net.hyntech.common.widget.baidumap.MyLocationListener
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityFindEbikeBinding
import net.hyntech.police.vm.FindEbikeViewModel

class FindEbikeActivity:BaseViewActivity<ActivityFindEbikeBinding,FindEbikeViewModel>(),BaiduMap.OnMarkerClickListener {

    private var mapView: TextureMapView? = null
    private var baiduMap: BaiduMap? = null

    private var llTime:LinearLayout? = null
    private var llPlay:LinearLayout? = null
    private var llTrack:LinearLayout? = null
    private var ibtnInfo:ImageButton? = null

    private val viewModel by viewModels<FindEbikeViewModel>()

    private val locClient: LocationClient by lazy { LocationClient(this) }
    private val locListener: MyLocationListener by lazy {
        MyLocationListener(object :
            MyLocationListener.LocationListener {
            override fun onReceive(bdLocation: BDLocation) {
                receiveLocation(bdLocation)
            }
        })
    }

    private val locClientOption: LocationClientOption by lazy {
        LocationClientOption().apply {
            this.isOpenGps = true //打开gps
            this.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
            this.coorType = "bd09ll" //设置坐标类型
            this.setIsNeedAddress(true) //必须设置之后才能获取到详细的地址信息
            this.scanSpan = 0 //可选3000，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            this.setIsNeedLocationDescribe(true)
        }
    }


    override fun getLayoutId(): Int = R.layout.activity_find_ebike

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {
        setTitle<FindEbikeActivity>(UIUtils.getString(CR.string.common_title_find_ebike)).onBack<FindEbikeActivity> {
            onFinish()
        }

        llTime = findViewById(R.id.ll_time)
        llPlay = findViewById(R.id.ll_play)
        llTrack = findViewById(R.id.ll_track)
        ibtnInfo = findViewById(R.id.ibtn_info)
        ibtnInfo?.visibility = View.VISIBLE
        ibtnInfo?.setOnClickListener {
            ToastUtil.showToast("请输入车牌号")
        }

        showPlayView(false)

        mapView = findViewById(R.id.bmap_view)
        mapView?.let {
            // it.showZoomControls(false) //设置隐藏放大缩小按钮
            baiduMap = it.map
            baiduMap?.uiSettings?.isRotateGesturesEnabled = true
            // 开启定位图层
            baiduMap?.isMyLocationEnabled = true
            baiduMap?.setOnMarkerClickListener(this)

            MapViewHandler(this).setMapView(it)
        }

        initLocation()
    }

    private fun initLocation() {
        locClient.registerLocationListener(locListener)
        locClient.locOption = locClientOption
        locClient.start()
    }

    private fun receiveLocation(bdLocation: BDLocation?){
        bdLocation?.let {
            val mapStatus: MapStatus = MapStatus.Builder()
                .target(LatLng(it.latitude,it.longitude))
                .zoom(18f)
                .build()

            val locData: MyLocationData = MyLocationData.Builder()
                .accuracy(it.radius)
                //此处设置开发者获取到的方向信息，顺时针0-360
                .direction(it.direction).latitude(it.latitude)
                .longitude(it.longitude)
                .build()
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            val mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus)
            baiduMap?.setMyLocationData(locData)
            //改变地图状态
            baiduMap?.setMapStatus(mapStatusUpdate)
        }
    }

    //控制播放控件和日期控件的显示和隐藏
    private fun showPlayView(isShow:Boolean){
        if(isShow){
            if(llTime?.visibility == View.GONE) llTime?.visibility = View.VISIBLE
            if(llPlay?.visibility == View.GONE) llPlay?.visibility = View.VISIBLE
        }else{
            if(llTime?.visibility == View.VISIBLE) llTime?.visibility = View.GONE
            if(llPlay?.visibility == View.VISIBLE) llPlay?.visibility = View.GONE
        }
    }



    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.let {
            val bundle:Bundle = it.extraInfo
            val id:String = bundle.getString("id","")
            val view = View.inflate(this,R.layout.window_marker_info,null)
            view?.findViewById<TextView>(R.id.tv_info)?.text = id
            val infoWindow:InfoWindow = InfoWindow(view,marker.position,-100)
            baiduMap?.showInfoWindow(infoWindow)
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        // 退出时销毁定位
        locClient.unRegisterLocationListener(locListener)
        locClient.stop()

    }

}