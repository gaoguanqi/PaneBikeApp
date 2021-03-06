package net.hyntech.usual.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.TimeUtils
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.global.handler.MapViewHandler
import net.hyntech.common.model.entity.EbikeTrackEntity
import net.hyntech.common.model.vo.BundleEbikeVo
import net.hyntech.common.ui.adapter.EbikeNoAdapter
import net.hyntech.common.widget.baidumap.MyLocationListener
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.widget.marker.MarkerUtils
import net.hyntech.common.widget.popu.EbikeListPopu
import net.hyntech.usual.R
import net.hyntech.usual.databinding.ActivityEbikeTrackBinding
import net.hyntech.usual.vm.TrackViewModel
import org.jetbrains.anko.collections.forEachWithIndex
import razerdp.basepopup.BasePopupWindow
import java.util.*
import net.hyntech.common.R as CR

/**
 * 查看车辆轨迹时用户首先需要购买防盗保障、增值服务，
 * 如果没有购买的话，会提示用户去购买，
 * 购买之后就可以查看车辆轨迹了。
 * 车辆轨迹只能查看七天之内
 */
class EbikeTrackActivity:BaseViewActivity<ActivityEbikeTrackBinding,TrackViewModel>() {

    //ebike 档速 快  正常
    private val stepList:List<Long> = arrayListOf(200L,50L)
    private var stepTime:Long = 200L

    private var tvTitle:TextView? = null
    private var ivArrowIcon: ImageView? = null
    private var tvStartTime:TextView? = null
    private var tvEndTime:TextView? = null
    private var ibtnPlay:ImageButton? = null

    private var ebikeNo:String? = ""

    private var mapView: TextureMapView? = null
    private var baiduMap: BaiduMap? = null

    private val draPlay:Drawable by lazy { UIUtils.getDrawable(CR.drawable.selector_map_play) }
    private val draPause:Drawable by lazy { UIUtils.getDrawable(CR.drawable.selector_map_pause) }
    private var isPlay:Boolean = false
    private val startMarker by lazy { BitmapDescriptorFactory.fromResource(CR.drawable.ic_ebike_start) }
    private val endMarker by lazy { BitmapDescriptorFactory.fromResource(CR.drawable.ic_ebike_end) }
    private val moveMarker by lazy { BitmapDescriptorFactory.fromResource(CR.drawable.ic_move) }
    private var marker:Marker? = null
    private val pointList:MutableList<LatLng> = mutableListOf()
    private var index:Int = 0

    private val buyDialog: CommonDialog by lazy { CommonDialog(this,UIUtils.getString(CR.string.common_warm),
        UIUtils.getString(CR.string.common_content_nobuy_service),
        UIUtils.getString(CR.string.common_close),
        UIUtils.getString(CR.string.common_buy_now),object :
            CommonDialog.OnClickListener{
            override fun onCancleClick() {
                //关闭
                //onFinish()
            }
            override fun onConfirmClick() {
                //立即购买
                startActivityForResult(Intent(this@EbikeTrackActivity,AddValServiceActivity::class.java).putExtra(Constants.BundleKey.EXTRA_ID,ebikeId),EventCode.EVENT_CODE_TRACK)
            } }) }

    private lateinit var ebikeId:String
    private lateinit var ebikeList:List<BundleEbikeVo>

    private val ebikeAdapter by lazy { EbikeNoAdapter(this)}

    private val ebikePopu by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { EbikeListPopu<EbikeNoAdapter.ViewHolder, EbikeNoAdapter>(this,ebikeAdapter).apply {
        this.setOnPopupWindowShowListener { ivArrowIcon?.background = UIUtils.getDrawable(CR.drawable.ic_arrow_up) }
        this.onDismissListener = object : BasePopupWindow.OnDismissListener(){ override fun onDismiss() { ivArrowIcon?.background = UIUtils.getDrawable(
            CR.drawable.ic_arrow_down) } } }
    }

    private val startCalendar = Calendar.getInstance()
    private val endCalendar = Calendar.getInstance()
    //开始时间范围 当前时间的上一周
    private val startTimePickerView: TimePickerView by lazy {
        TimePickerBuilder(this, object :OnTimeSelectListener{
            override fun onTimeSelect(date: Date?, v: View?) {
                if(checkDate(date,endDate)){
                    startDate = date
                    tvStartTime?.text = TimeUtils.date2String(date)
                    queryEbikeTrack()
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
            .setRangDate(startCalendar,Calendar.getInstance())
            .build()
    }

    //结束时间范围 当前时间的上一周 而且结束时间不能小于 开始时间
    private val endTimePickerView: TimePickerView by lazy {
        TimePickerBuilder(this, object :OnTimeSelectListener{
            override fun onTimeSelect(date: Date?, v: View?) {
                if(checkDate(startDate,date)){
                    endDate = date
                    tvEndTime?.text = TimeUtils.date2String(date)
                    queryEbikeTrack()
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
            .setRangDate(endCalendar,Calendar.getInstance())
            .build()
    }

    private val viewModel by viewModels<TrackViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_ebike_track

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    private var startDate:Date? = null
    private var endDate:Date? = null

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


    override fun initData(savedInstanceState: Bundle?) {
        tvTitle = findViewById(R.id.tv_title)
        ivArrowIcon = findViewById(R.id.iv_arrow_icon)
        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)
        ibtnPlay = findViewById(R.id.ibtn_play)
        ibtnPlay?.background = draPlay

        mapView = findViewById(R.id.bmap_view)
        mapView?.let {
           // it.showZoomControls(false) //设置隐藏放大缩小按钮
            baiduMap = it.map
            baiduMap?.uiSettings?.isRotateGesturesEnabled = true
            // 开启定位图层
            baiduMap?.isMyLocationEnabled = true
            MapViewHandler(this).setMapView(it)
        }

        startCalendar.add(Calendar.DAY_OF_MONTH,-7)
        endCalendar.add(Calendar.DAY_OF_MONTH,-7)

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.notBuyServiceEvent.observe(this, Observer {
            showBuyDialog()
        })

        //轨迹数据
        viewModel.ebikeTrack.observe(this, Observer {
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
                    onDrawLine()
                }
            }
        })

        findViewById<LinearLayout>(R.id.ll_left)?.setOnClickListener {
            onFinish()
        }

        findViewById<LinearLayout>(R.id.ll_title)?.setOnClickListener {
            onClickTitle()
        }

        findViewById<ImageButton>(R.id.ibtn_play)?.setOnClickListener {
            onClickProxy {
                if(pointList.isEmpty()){
                    ToastUtil.showToast("请先搜索车辆")
                }else{
                    onEbikePlay()
                }
            }
        }

        findViewById<ImageButton>(R.id.ibtn_replay)?.setOnClickListener {
            onClickProxy {
                if(pointList.isEmpty()){
                    ToastUtil.showToast("请先搜索车辆")
                }else{
                    onEbikeReplay()
                }
            }
        }

        findViewById<ImageButton>(R.id.ibtn_fast)?.setOnClickListener {
            onClickProxy {
                if(pointList.isEmpty()){
                    ToastUtil.showToast("请先搜索车辆")
                }else{
                    onEbikeFast()
                }
            }
        }

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


        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH,-1)
        startDate = calendar.time
        tvStartTime?.text = TimeUtils.date2String(startDate)

        endDate = TimeUtils.getNowDate()
        tvEndTime?.text = TimeUtils.date2String(endDate)

        val bundle = intent.extras
        bundle?.let {
            ebikeList = it.getSerializable(Constants.BundleKey.EXTRA_OBJ) as List<BundleEbikeVo>
            var vo = ebikeList.first()
            if(ebikeList.size > 1){
                ebikeList.forEach {item ->
                    if(item.isSelected){
                        vo = item
                    }
                }
            }
            setData(vo)
        }

        ebikeAdapter.setListener(object :EbikeNoAdapter.OnClickListener{
            override fun onItemClick(item: BundleEbikeVo?) {
                if(ebikeList.size > 1){
                    item?.let { ebike ->
                        currentEbike?.let {curr ->
                            if(!TextUtils.equals(curr.ebikeNo,ebike.ebikeNo)){
                                ebikeList.forEach {item ->
                                    item.isSelected = false
                                }
                                ebike.isSelected = true
                                setData(ebike)
                            }
                        }
                    }
                }
                ebikePopu.dismiss()
            }
        })

        initLocation()
    }

    private fun initLocation() {
        locClient.registerLocationListener(locListener)
        locClient.locOption = locClientOption
        locClient.start()
    }

    private fun receiveLocation(bdLocation: BDLocation?) {
        bdLocation?.let {
            val mapStatus: MapStatus = MapStatus.Builder()
                .target(LatLng(it.latitude,it.longitude))
                .zoom(18f)
                .build()

            val locData:MyLocationData = MyLocationData.Builder()
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

    private var currentEbike:BundleEbikeVo? = null
    private fun setData(vo: BundleEbikeVo) {
        this.ebikeId = vo.ebikeId
        this.ebikeNo = vo.ebikeNo
        this.currentEbike = vo
        tvTitle?.text = vo.ebikeNo
        queryEbikeTrack()
    }

    private fun onClickTitle() {
        showEBikePopu()
    }

    private fun showEBikePopu(){
        ebikeList.let {list ->
            ebikeAdapter.setData(list)
            ebikePopu.showPopupWindow(tvTitle)
        }
    }

    private fun showBuyDialog(){
        if(!buyDialog.isShowing){
            buyDialog.show()
        }
    }

    private fun checkDate(startDate:Date?,endDate: Date?):Boolean{
        if(startDate == null || endDate == null){
            ToastUtil.showToast("您选择的日期有误！")
            return false
        }
        if(TextUtils.isEmpty(ebikeNo)){
            ToastUtil.showToast("车牌号不能为空")
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


    private fun onEbikePlay(){
        if(isPlay){
            //暂停
            isPlay = false
            ibtnPlay?.background = draPlay
            handler.removeMessages(0)
        }else{
            isPlay = true
            ibtnPlay?.background = draPause
            handler.sendEmptyMessageAtTime(0,stepTime)
        }
    }
    private fun onEbikeReplay(){
        isPlay = true
        ibtnPlay?.background = draPause
        handler.removeMessages(0)
        index = 0
        handler.sendEmptyMessageAtTime(0,stepTime)
    }
    private fun onEbikeFast(){
        setStepMode()
    }

    //查询车辆轨迹
    private fun queryEbikeTrack(){
        viewModel.locationSearch(ebikeNo!!,TimeUtils.date2String(startDate),TimeUtils.date2String(endDate))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                EventCode.EVENT_CODE_TRACK ->{
                   queryEbikeTrack()
                }
            }
        }
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

    private val handler: Handler =  object:Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            index++
            if(index < pointList.size){
                sendMessage()
            }else{
                isPlay = false
                ibtnPlay?.background = draPlay
            }
        }
    }



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