package net.hyntech.common.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.cloud.CloudManager
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.*
import com.baidu.mapapi.search.poi.*
import com.baidu.mapapi.utils.CoordinateConverter
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.global.handler.MapViewHandler
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.ui.adapter.MapPointAdapter
import net.hyntech.common.widget.baidumap.MyLocationListener

@Route(path = ARouterConstants.BAIDU_MAP_PAGE)
class BaiduMapActivity : BaseActivity(), OnGetGeoCoderResultListener, OnGetPoiSearchResultListener {

    private var mapView: TextureMapView? = null
    private var rvPoint: RecyclerView? = null
    private var baiduMap: BaiduMap? = null
    private var currentLatLng: LatLng? = null
    private var geoCoder: GeoCoder? = null
    private var poiSearch: PoiSearch? = null
    private val myMarker by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_my_location) }

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
            this.setIsNeedLocationPoiList(true)
        }
    }

    private val poiInfoList: MutableList<PoiInfo> = mutableListOf()
    private val pointAdapter: MapPointAdapter by lazy {
        MapPointAdapter(this).apply {
            this.setListener(object : MapPointAdapter.OnClickListener {
                override fun onItemClick(pos: Int, item: PoiInfo?) {
                    changePoiInfoItem(pos, item)
                }
            })
        }
    }

    private fun changePoiInfoItem(pos: Int, item: PoiInfo?) {
        if (pointAdapter.getIndex() != pos) {
            pointAdapter.setIndex(pos)
            pointAdapter.notifyDataSetChanged()
        }
        val intent: Intent = Intent()
        intent.putExtra(Constants.BundleKey.EXTRA_CONTENT,item?.name)
        intent.putExtra(Constants.BundleKey.EXTRA_LAT,item?.location?.latitude?.toString())
        intent.putExtra(Constants.BundleKey.EXTRA_LNG,item?.location?.longitude?.toString())
        onFinishByIntent(intent)
    }


    override fun getLayoutId(): Int = R.layout.activity_baidu_map

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<BaiduMapActivity>(UIUtils.getString(R.string.common_title_baidu_map)).onBack<BaiduMapActivity> {
            onFinish()
        }
        rvPoint = findViewById(R.id.rv_point)
        rvPoint?.layoutManager = LinearLayoutManager(this)
        rvPoint?.adapter = pointAdapter

        mapView = findViewById(R.id.bmap_view)
        mapView?.let {
            it.showZoomControls(false) //设置隐藏放大缩小按钮
            CloudManager.getInstance().init()
            geoCoder = GeoCoder.newInstance()
            geoCoder?.setOnGetGeoCodeResultListener(this)
            poiSearch = PoiSearch.newInstance()
            poiSearch?.setOnGetPoiSearchResultListener(this)
            baiduMap = it.map
            baiduMap?.uiSettings?.isRotateGesturesEnabled = false
            // 开启定位图层
            baiduMap?.isMyLocationEnabled = false
            //自定义标签
//            baiduMap?.setMyLocationConfiguration(MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,myMarker))
            MapViewHandler(this@BaiduMapActivity).setMapView(it)
            baiduMap?.setOnMapClickListener(object : BaiduMap.OnMapClickListener {
                override fun onMapClick(latLng: LatLng?) {
                    latLng?.let {
                        currentLatLng = latLng
                        updateMapState(latLng)
                    }
                }

                override fun onMapPoiClick(poi: MapPoi?) {}
            })

            //隐藏百度logo,ZoomControl
            val count: Int = it.childCount
            for (index in 1..count) {
                val child = it.getChildAt(index)
                child?.let { v ->
                    if (v is ImageView || v is com.baidu.platform.comapi.map.an) {
                        v.visibility = View.INVISIBLE
                    }
                }
            }
        }

        initLocation()
    }

    private fun updateMapState(latLng: LatLng) {
        val options = MarkerOptions().position(latLng).icon(myMarker)
        baiduMap?.clear()
        baiduMap?.addOverlay(options)
        geoCoder?.reverseGeoCode(ReverseGeoCodeOption().location(latLng))
        searchNeayBy()
    }

    private fun initLocation() {
        locClient.registerLocationListener(locListener)
        locClient.locOption = locClientOption
        locClient.start()
    }

    private var radius: Int = 0

    private fun receiveLocation(bdLocation: BDLocation?) {
        bdLocation?.let {
            currentLatLng = LatLng(it.latitude, it.longitude)
            val converter: CoordinateConverter = CoordinateConverter()
            converter.coord(currentLatLng)
            converter.from(CoordinateConverter.CoordType.COMMON)
            val convertLatLng = converter.convert()
            val options =
                MarkerOptions().position(convertLatLng).icon(myMarker).zIndex(4).draggable(true)
            val marker = baiduMap?.addOverlay(options) as Marker
            val u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 16.0f)
            radius = it.radius.toInt()
            baiduMap?.animateMapStatus(u)
            keyword = it.addrStr
            searchNeayBy()
        }
    }

    private var keyword:String? = ""
    private fun searchNeayBy() {
        val option: PoiNearbySearchOption = PoiNearbySearchOption()
        option.keyword(keyword)
        option.sortType = PoiSortType.distance_from_near_to_far
        option.location(currentLatLng)
        if (radius != 0) {
            option.mRadius = radius
        } else {
            option.mRadius = 1000
        }

        poiSearch?.searchNearby(option)
    }


    override fun onDestroy() {
        super.onDestroy()
        // 退出时销毁定位
        locClient.unRegisterLocationListener(locListener)
        locClient.stop()
    }

    override fun onGetGeoCodeResult(result: GeoCodeResult?) {}

    override fun onGetReverseGeoCodeResult(result: ReverseGeoCodeResult?) {}

    override fun onGetPoiIndoorResult(result: PoiIndoorResult?) {}

    //接受周边地理位置结果
    override fun onGetPoiResult(result: PoiResult?) {

        LogUtils.logGGQ("onGetPoiResult--error>>${result?.error}")
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            //ToastUtil.showToast("未找到结果")
            return
        }

        try {
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                val infos = result.allPoi
                if (!infos.isNullOrEmpty()) {
                    poiInfoList.clear()
                    poiInfoList.addAll(infos)
                    pointAdapter.setIndex(0)
                    if (rvPoint?.visibility == View.GONE) {
                        rvPoint?.visibility = View.VISIBLE
                    }
                    pointAdapter.setData(poiInfoList)
                }
            }else if(result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD){
                //当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                val s:StringBuilder = StringBuilder()
                s.append("在")
                result.suggestCityList?.forEach {
                    s.append("${it.city},")
                }
                s.append("找到结果")
                ToastUtil.showToast(s.toString())
            }
        } catch (e: Exception) {
            e.fillInStackTrace()
        }
    }

    override fun onGetPoiDetailResult(result: PoiDetailResult?) {}

    override fun onGetPoiDetailResult(result: PoiDetailSearchResult?) {}


}