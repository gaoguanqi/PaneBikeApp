package net.hyntech.police.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.animation.Animation
import com.baidu.mapapi.animation.ScaleAnimation
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.TimeUtils
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.global.handler.MapViewHandler
import net.hyntech.common.model.entity.AlarmInfoEntity
import net.hyntech.common.model.entity.EbikeTrackEntity
import net.hyntech.common.model.entity.PhotoEntity
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.ui.adapter.PhotoAdapter
import net.hyntech.common.widget.baidumap.MyLocationListener
import net.hyntech.common.widget.dialog.FindEbikeDialog
import net.hyntech.common.widget.marker.MarkerUtils
import net.hyntech.common.widget.popu.EbikeInfoPopu
import net.hyntech.common.widget.view.ClearEditText
import net.hyntech.police.R
import net.hyntech.police.databinding.ActivityFindEbikeBinding
import net.hyntech.police.vm.FindEbikeViewModel
import org.jetbrains.anko.collections.forEachWithIndex
import java.util.*
import kotlin.collections.ArrayList
import net.hyntech.common.R as CR

class FindEbikeActivity:BaseViewActivity<ActivityFindEbikeBinding,FindEbikeViewModel>(),BaiduMap.OnMarkerClickListener {

    //ebike 档速 快  正常
    private val stepList:List<Long> = arrayListOf(200L,50)
    private var stepTime:Long = 200L

    //从首页消息列表传递的 车辆信息
    private var alarmInfo:AlarmInfoEntity.AlarmInfoListBean? = null

    private var mapView: TextureMapView? = null
    private var baiduMap: BaiduMap? = null

    private var llSearch:LinearLayout? = null
    private var llTime:LinearLayout? = null
    private var llPlay:LinearLayout? = null
    private var llTrack:LinearLayout? = null
    private var ibtnInfo:ImageButton? = null

    private var llRight:LinearLayout? = null
    private var tvRight:TextView? = null

    private var tvStartTime:TextView? = null
    private var tvEndTime:TextView? = null

    private var etInput:ClearEditText? = null

    private var ebikeInfo:EbikeTrackEntity.EbikeBean? = null

    private val ebikeMarker by lazy { BitmapDescriptorFactory.fromResource(CR.drawable.icon_marker_car) }
    private val startMarker by lazy { BitmapDescriptorFactory.fromResource(CR.drawable.ic_ebike_start) }
    private val endMarker by lazy { BitmapDescriptorFactory.fromResource(CR.drawable.ic_ebike_end) }
    private val moveMarker by lazy { BitmapDescriptorFactory.fromResource(CR.drawable.ic_move) }
    private var marker:Marker? = null

    private val ebikeInfoPopu by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { EbikeInfoPopu(this,
        mWidth = (ScreenUtils.getScreenWidth() * 0.86).toInt(),
        mHeight = (ScreenUtils.getScreenHeight() * 0.42).toInt()).apply {
        this.setEbikeImgAdapter(photoAdapter)
        this.setListener(object :EbikeInfoPopu.OnClickListener{
            override fun onLabelImgClick(url: String) {
                val pos:Int = 0
                val bundle = Bundle()
                val array = java.util.ArrayList<String>()
                array.add(url)
                bundle.putSerializable(Constants.BundleKey.EXTRA_OBJ,array)
                bundle.putInt(Constants.BundleKey.EXTRA_INDEX,pos)
                ARouter.getInstance().build(ARouterConstants.PREVIEW_PAGE)
                    .with(bundle)
                    .navigation()
            } }) } }

    private val findEbikeDialog: FindEbikeDialog by lazy { FindEbikeDialog(listener = object :FindEbikeDialog.OnClickListener{
        override fun onConfirmClick(address:String,remark:String) {
            viewModel.commitFindEbike(alarmId!!,address,remark)
        }

        override fun onAddressClick() {
            ARouter.getInstance().build(ARouterConstants.BAIDU_MAP_PAGE).navigation(this@FindEbikeActivity,102)
        }
    }) }

    private val photoAdapter by lazy { PhotoAdapter(this,CR.layout.item_photo).apply {
        this.setListener(object : PhotoAdapter.OnClickListener{
            override fun onItemClick(pos:Int, item: PhotoEntity?, list:MutableList<PhotoEntity>) {
                item?.let {
                    val bundle = Bundle()
                    val array = java.util.ArrayList<String>()
                    list.forEach {
                        array.add(it.url)
                    }
                    bundle.putSerializable(Constants.BundleKey.EXTRA_OBJ,array)
                    bundle.putInt(Constants.BundleKey.EXTRA_INDEX,pos)
                    ARouter.getInstance().build(ARouterConstants.PREVIEW_PAGE)
                        .with(bundle)
                        .navigation()
                }
            }
            override fun onItemDel(pos: Int, item: PhotoEntity?) {}
        })
    } }


    private val startCalendar = Calendar.getInstance()
    private val endCalendar = Calendar.getInstance()
    private var startDate:Date? = null
    private var endDate:Date? = null
    //开始时间范围 当前时间的上一周
    private val startTimePickerView: TimePickerView by lazy {
        TimePickerBuilder(this, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date?, v: View?) {
                if(checkDate(date,endDate)){
                    startDate = date
                    tvStartTime?.text = TimeUtils.date2String(date)
                    val input = etInput?.text.toString().trim()
                    onFindEbike(input,TimeUtils.date2String(startDate),TimeUtils.date2String(endDate))
                }
            }
        }).setType(booleanArrayOf(true, true, true, true, true, true))
            .setTitleText("开始时间")
            .setCancelColor(UIUtils.getColor(CR.color.common_color_gray))
            .setSubmitColor(UIUtils.getColor(CR.color.common_colorTheme))
            .setTitleBgColor(UIUtils.getColor(CR.color.common_white))
            .setTitleColor(UIUtils.getColor(CR.color.common_black))
            .setTextColorOut(UIUtils.getColor(CR.color.common_color_gray))
            .setBgColor(UIUtils.getColor(CR.color.common_default_background))
            .setRangDate(startCalendar, Calendar.getInstance())
            .build()
    }

    //结束时间范围 当前时间的上一周 而且结束时间不能小于 开始时间
    private val endTimePickerView: TimePickerView by lazy {
        TimePickerBuilder(this, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date?, v: View?) {
                if(checkDate(startDate,date)){
                    endDate = date
                    tvEndTime?.text = TimeUtils.date2String(date)
                    val input = etInput?.text.toString().trim()
                    onFindEbike(input,TimeUtils.date2String(startDate),TimeUtils.date2String(endDate))
                }
            }
        }).setType(booleanArrayOf(true, true, true, true, true, true))
            .setTitleText("结束时间")
            .setCancelColor(UIUtils.getColor(CR.color.common_color_gray))
            .setSubmitColor(UIUtils.getColor(CR.color.common_colorTheme))
            .setTitleBgColor(UIUtils.getColor(CR.color.common_white))
            .setTitleColor(UIUtils.getColor(CR.color.common_black))
            .setTextColorOut(UIUtils.getColor(CR.color.common_color_gray))
            .setBgColor(UIUtils.getColor(CR.color.common_default_background))
            .setRangDate(endCalendar, Calendar.getInstance())
            .build()
    }

    private val viewModel by viewModels<FindEbikeViewModel>()

    private val locClient: LocationClient by lazy { LocationClient(this) }
    private val locListener: MyLocationListener by lazy {
        MyLocationListener(object :
            MyLocationListener.LocationListener {
            override fun onReceive(bdLocation: BDLocation) {
                if(alarmInfo == null){ //如果 传递过来的报警车辆为空，默认显示当前位置
                    receiveLocation(bdLocation)
                }
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

    private var alarmId:String? = ""
    private val pointList:MutableList<LatLng> = mutableListOf()
    private var index:Int = 0
    override fun initData(savedInstanceState: Bundle?) {
        setTitle<FindEbikeActivity>(UIUtils.getString(CR.string.common_title_find_ebike)).onBack<FindEbikeActivity> {
            onFinish()
        }

        llSearch = findViewById(R.id.ll_search)
        etInput = findViewById(R.id.et_input)
        etInput?.hint = "请输入车牌号"
        llTime = findViewById(R.id.ll_time)
        llPlay = findViewById(R.id.ll_play)
        llTrack = findViewById(R.id.ll_track)
        ibtnInfo = findViewById(R.id.ibtn_info)
        ibtnInfo?.visibility = View.GONE
        ibtnInfo?.setOnClickListener {
            if(ebikeInfo == null || ebikeInfoPopu.isShowing){
                ToastUtil.showToast("未查询到车辆信息")
            }else{
                showInfoPopu(ebikeInfo!!)
            }
        }

        llRight = findViewById(R.id.ll_right)
        tvRight = findViewById(R.id.tv_right)
        tvRight?.text = "已找回"
        llRight?.visibility = View.GONE
        llRight?.setOnClickListener {
            //提交已找回
            if(!TextUtils.isEmpty(alarmId) && !UIUtils.isFastDoubleClick() && ebikeInfo != null){
                showFindDialog()
            }
        }
        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)

        tvStartTime?.setOnClickListener {
            if(!startTimePickerView.isShowing){
                startTimePickerView.show()
            }
        }
        tvEndTime?.setOnClickListener {
            if(!endTimePickerView.isShowing){
                endTimePickerView.show()
            }
        }
//        startCalendar.add(Calendar.DAY_OF_MONTH,-28)
//        endCalendar.add(Calendar.DAY_OF_MONTH,-28)

        startCalendar.add(Calendar.MONTH,-1)
        startCalendar.add(Calendar.MONTH,-1)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH,-1)
        startDate = calendar.time
        tvStartTime?.text = TimeUtils.date2String(startDate)

        endDate = TimeUtils.getNowDate()
        tvEndTime?.text = TimeUtils.date2String(endDate)


        etInput?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(TextUtils.isEmpty(s)){
                    if(ibtnInfo?.visibility == View.VISIBLE) ibtnInfo?.visibility = View.GONE
                }else{
                    if(ibtnInfo?.visibility == View.GONE) ibtnInfo?.visibility = View.VISIBLE
                }
            }
        })

        findViewById<Button>(R.id.btn_search)?.setOnClickListener {
           onClickProxy {
               val input = etInput?.text.toString().trim()
               if(TextUtils.isEmpty(input)){
                   ToastUtil.showToast("请输入车牌号")
                   return@onClickProxy
               }

               if(startDate == null || endDate == null){
                   onFindEbike(input,"","")
               }else{
                   onFindEbike(input,TimeUtils.date2String(startDate),TimeUtils.date2String(endDate))
               }
           }
        }

        findViewById<ImageButton>(R.id.ibtn_track).setOnClickListener {
            if(pointList.isEmpty()){
                ToastUtil.showToast("请先查找车辆")
            }else{
                showPlayView(true)
                onDrawLine()
            }
        }

        findViewById<ImageButton>(R.id.ibtn_ding).setOnClickListener {
            //返回到当前位置
            latLng?.let {
                val mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng)
                baiduMap?.animateMapStatus(mapStatusUpdate)
            }
        }

        //播放
        findViewById<ImageButton>(R.id.ibtn_play).setOnClickListener {
            onClickProxy {
                if(pointList.isEmpty()){
                    ToastUtil.showToast("请先搜索车辆")
                }else{
                    startPlay()
                }
            }
        }

        //重新播放
        findViewById<ImageButton>(R.id.ibtn_replay).setOnClickListener {
            onClickProxy {
                if(pointList.isEmpty()){
                    ToastUtil.showToast("请先搜索车辆")
                }else{
                    replay()
                }
            }
        }

        findViewById<ImageButton>(R.id.ibtn_fast).setOnClickListener {
            onClickProxy {
                if(pointList.isEmpty()){
                    ToastUtil.showToast("请先搜索车辆")
                }else{
                  setStepMode()
                }
            }
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

        viewModel.ebikeTrack.observe(this, Observer {
            it.ebike?.let { ebike ->
                ebikeInfo = ebike
                addMarkers(ebike)
                if(!TextUtils.isEmpty(ebike.alarmId) && TextUtils.equals("alarm_confrim",ebike.state)){
                    this.alarmId = ebike.alarmId
                    llRight?.visibility = View.VISIBLE
                }else{
                    this.alarmId = ""
                    llRight?.visibility = View.GONE
                }
            }

            it.trajectoryList?.let { list ->
                if(!list.isNullOrEmpty()){
                    LogUtils.logGGQ("list---->>>>${list.size}")
                    pointList.clear()
                    isPlay = false
                    list.forEachWithIndex { i, bean ->
                        val n = i + 1
                        if(n < list.size - 1){
                            val from = LatLng(list.get(i).lat,list.get(i).lng)
                            val to = LatLng(list.get(n).lat,list.get(n).lng)
                            pointList.addAll(MarkerUtils.splitDots(from,to))
                        }else{
                            val ll = LatLng(list.get(i).lat,list.get(i).lng)
                            pointList.addAll(MarkerUtils.splitDots(ll,ll))
                        }
                    }
                }
            }
        })

        initLocation()

        intent?.extras?.let {
            alarmInfo = it.getSerializable(Constants.BundleKey.EXTRA_OBJ) as? AlarmInfoEntity.AlarmInfoListBean
            alarmInfo?.let { info ->
                etInput?.setText(info.ebikeNo)
                //显示当前车辆位置
                if(!TextUtils.isEmpty(info.ebikeNo) && !info.lat.isNaN() && !info.lng.isNaN()){
                    val latLng:LatLng = LatLng(info.lat, info.lng)
                    addMarkerByLocation(info.ebikeNo,latLng)
                }
            }
        }
    }

    private fun showInfoPopu(ebike: EbikeTrackEntity.EbikeBean) {
        ebikeInfoPopu.setInfo(ebike)
        ebikeInfoPopu.showPopupWindow(llSearch)
    }

    private fun showFindDialog(){
        findEbikeDialog.apply {
            this.showNow(supportFragmentManager,"FindEbikeDialog")
            this.setNoText(ebikeInfo!!.ebikeNo)
            this.setAddText(currentAddr)
        }
    }

    private fun initLocation() {
        locClient.registerLocationListener(locListener)
        locClient.locOption = locClientOption
        locClient.start()
    }

    private var currentAddr:String = ""
    private var latLng:LatLng? = null
    private fun receiveLocation(bdLocation: BDLocation?){
        bdLocation?.let {
            currentAddr = it.addrStr
            latLng = LatLng(it.latitude,it.longitude)
            val mapStatus: MapStatus = MapStatus.Builder()
                .target(latLng)
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

    private fun addMarkers(ebike: EbikeTrackEntity.EbikeBean) {
        val latLng:LatLng? = LatLng(ebike.lastLat, ebike.lastLng)
        latLng?.let {ll ->
            val marker = baiduMap?.run {
                val bundle:Bundle = Bundle()
                bundle.putString("id",ebike.ebikeNo)
                //添加之前删除 marker
                this.clear()
                val markerOptions = MarkerOptions().position(ll).icon(ebikeMarker)
                markerOptions?.alpha(0.9f);//marker图标透明度，0~1.0，默认为1.0
//                markerOptions?.animateType(MarkerOptions.MarkerAnimateType.drop) ////marker出现的方式，从天上掉下
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

    //单独处理从首页报警消息列表点击进来的车辆位置显示
    private fun addMarkerByLocation(ebikeNo:String,latLng:LatLng) {
        val marker = baiduMap?.run {
            val bundle:Bundle = Bundle()
            bundle.putString("id",ebikeNo)
            this.clear()
            val markerOptions = MarkerOptions().position(latLng).icon(ebikeMarker)
            markerOptions?.alpha(0.9f);//marker图标透明度，0~1.0，默认为1.0
            markerOptions?.extraInfo(bundle)
            this.addOverlay(markerOptions)
        } as? Marker

        marker?.setAnimation(markerAnim)
        marker?.startAnimation()

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

    private fun ebikeStartAndEndMarker(start:LatLng,end:LatLng){
        LogUtils.logGGQ("开始位置：${start.latitude}--${start.longitude}")
        LogUtils.logGGQ("结束位置：${end.latitude}--${end.longitude}")
        baiduMap?.run {
            this.addOverlays(listOf(MarkerOptions().position(start).icon(startMarker),MarkerOptions().position(end).icon(endMarker)))
        }

        //定义地图状态
        val mapStatus: MapStatus = MapStatus.Builder()
            .target(start)
            .zoom(15f)
            .build()
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        val mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus)
        //改变地图状态
        baiduMap?.setMapStatus(mapStatusUpdate)
    }




    private val markerAnim by lazy {
        ScaleAnimation(1f, 1.1f, 1f).apply {
            this.setDuration(2000L)
            this.setRepeatMode(Animation.RepeatMode.RESTART)
            this.setRepeatCount(0)
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.let {
            val bundle:Bundle = it.extraInfo
            val id:String = bundle.getString("id","")
            val view = View.inflate(this,R.layout.window_marker_info,null)
            view?.findViewById<TextView>(R.id.tv_info)?.text = id
            val infoWindow:InfoWindow = InfoWindow(view,marker.position,-158)
            baiduMap?.showInfoWindow(infoWindow)
        }
        return false
    }
    private fun checkDate(startDate:Date?,endDate: Date?):Boolean{
        if(startDate == null || endDate == null){
            ToastUtil.showToast("您选择的日期有误！")
            return false
        }
        val input = etInput?.text.toString().trim()
        if(TextUtils.isEmpty(input)){
            ToastUtil.showToast("请输入车牌号")
            return false
        }

        if(endDate.before(startDate)){
            ToastUtil.showToast("结束时间不能大于开始时间")
            return false
        }

        if(endDate.after(Calendar.getInstance().time)){
            ToastUtil.showToast("结束时间不能大于当前时间")
            return false
        }

        if(startDate.time == endDate.time){
            ToastUtil.showToast("开始时间和结束时间不能相同")
            return false
        }

        return true
    }

    private fun onFindEbike(ebikeNo:String,startTime:String,endTime:String){
        viewModel.onFindEbike(ebikeNo,startTime,endTime)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                102 ->{
                    val content = data?.getStringExtra(Constants.BundleKey.EXTRA_CONTENT)
                    LogUtils.logGGQ("跳转返回-->>${content}")
                    if(!TextUtils.isEmpty(content)){
                        findEbikeDialog.setAddText("${content}")
                    }
                }
            }
        }
    }


    private val handler: Handler =  object:Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            index++
            if(index < pointList.size){
                sendMessage()
            }
        }
    }


    private var isPlay:Boolean = false

    private fun onDrawLine(){
        //绘制
        val ooPolyline = PolylineOptions().width(5).points(pointList).color(UIUtils.getColor(CR.color.common_colorTheme))
        baiduMap?.apply {
            this.clear()
            this.addOverlay(ooPolyline)
        }
        //再放marker
        val start = pointList.first()
        val end = pointList.last()
        ebikeStartAndEndMarker(start,end)
        //运动
        //设置移动小车
        index = 0
        val angle = MarkerUtils.getAngle(getFromPoint(),getToPoint())
        val option = MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(moveMarker).position(start).rotate(angle.toFloat())
        marker = baiduMap?.addOverlay(option) as? Marker
    }

    private fun startPlay(){
        if(isPlay){
            replay()
        }else{
            isPlay = true
            handler.sendEmptyMessageAtTime(0,stepTime)
        }
    }

    private fun replay(){
        handler.removeMessages(0)
        index = 0
        handler.sendEmptyMessageAtTime(0,stepTime)
    }

    //设置档位
    private var stepMode:Int = 0
    private fun setStepMode(){
        stepMode ++
        if(stepMode >= stepList.size){
            stepMode = 0
        }
        stepTime = stepList.get(stepMode)
    }


    private fun sendMessage(){
        //设置中心点和纠偏
        baiduMap?.let {
            val pt = it.mapStatus.targetScreen
            val point = it.projection.toScreenLocation(pointList.get(index))
            if(point.x < 0 || point.x > pt.x * 2 || point.y < 0 || point.y > pt.y * 2){
                val mapStatus = MapStatus.Builder().target(marker?.position).zoom(15f).build()
                it.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus))
            }
        }

        //设置角度
        marker?.rotate = MarkerUtils.getAngle(getFromPoint(),getToPoint()).toFloat()
        //移动
        marker?.position = pointList.get(index)
        //循环发送消息
        handler.sendEmptyMessageDelayed(0,stepTime)
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeMessages(0)
        handler.removeCallbacksAndMessages(null)
        // 退出时销毁定位
        locClient.unRegisterLocationListener(locListener)
        locClient.stop()
    }


    private fun getFromPoint():LatLng{
        return pointList.get(index)
    }

    private fun getToPoint():LatLng{
        if(index >= pointList.size - 1){
            return pointList.get(index)
        }
        return pointList.get(index + 1)
    }
}