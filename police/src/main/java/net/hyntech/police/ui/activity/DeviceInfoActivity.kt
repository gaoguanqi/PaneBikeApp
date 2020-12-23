package net.hyntech.police.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.animation.Animation
import com.baidu.mapapi.animation.ScaleAnimation
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.handler.MapViewHandler
import net.hyntech.common.model.entity.DeviceInfoEntity
import net.hyntech.common.widget.baidumap.MyLocationListener
import net.hyntech.common.widget.view.ClearEditText
import net.hyntech.police.R
import net.hyntech.police.databinding.ActivityDeviceInfoBinding
import net.hyntech.police.vm.DeviceInfoViewModel
import net.hyntech.common.R as CR

class DeviceInfoActivity:BaseViewActivity<ActivityDeviceInfoBinding,DeviceInfoViewModel>(),
    BaiduMap.OnMarkerClickListener {

    private var etInput:ClearEditText? = null

    private var mapView: TextureMapView? = null
    private var baiduMap: BaiduMap? = null

    private val myMarker by lazy { BitmapDescriptorFactory.fromResource(net.hyntech.common.R.drawable.icon_my_location) }


    private val viewModel by viewModels<DeviceInfoViewModel>()

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

    override fun getLayoutId(): Int = R.layout.activity_device_info

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<DeviceInfoActivity>(UIUtils.getString(CR.string.common_title_device_info)).onBack<DeviceInfoActivity> {
            onFinish()
        }
        etInput = findViewById(R.id.et_input)
        etInput?.hint = "请输入设备ID号"

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

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.deviceList.observe(this, Observer {
            if(it.isNullOrEmpty()){
                ToastUtil.showToast("未搜索到设备")
            } else {
                it.first()?.let { device ->
                    addMarkers(device)
                }
            }
        })

        findViewById<Button>(net.hyntech.common.R.id.btn_search).setOnClickListener {
            onClickProxy {
                val input:String? = etInput?.getText().toString().trim()
                if(TextUtils.isEmpty(input)){
                    ToastUtil.showToast("请输入设备ID")
                }else{
                    viewModel.searchDecive(input!!)
                }
            }
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

    private val markerAnim by lazy {
        ScaleAnimation(1f, 1.1f, 1f).apply {
            this.setDuration(2000L)
            this.setRepeatMode(Animation.RepeatMode.RESTART)
            this.setRepeatCount(0)
        }
    }


    private fun addMarkers(device:DeviceInfoEntity.AtCollectorListBean) {
        val latLng:LatLng? = LatLng(device.lat, device.lng)
        latLng?.let {ll ->
            val marker = baiduMap?.run {
                val bundle:Bundle = Bundle()
                bundle.putString("id",device.collectorId)
                //添加之前删除 marker
                this.clear()
                val markerOptions = MarkerOptions().position(ll).icon(myMarker)
                markerOptions?.alpha(0.9f);//marker图标透明度，0~1.0，默认为1.0
                markerOptions?.animateType(MarkerOptions.MarkerAnimateType.drop) ////marker出现的方式，从天上掉下
                markerOptions?.extraInfo(bundle)
                this.addOverlay(markerOptions)
            } as? Marker

            marker?.setAnimation(markerAnim)
            marker?.startAnimation()
        }


        //定义地图状态
        val mapStatus: MapStatus = MapStatus.Builder()
            .target(latLng)
            .zoom(18f)
            .overlook(0f)
            .build()
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        val mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus)
        //改变地图状态
        baiduMap?.setMapStatus(mapStatusUpdate)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 退出时销毁定位
        locClient.unRegisterLocationListener(locListener)
        locClient.stop()

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
}