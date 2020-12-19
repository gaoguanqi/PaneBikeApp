package net.hyntech.usual.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.cloud.CloudManager
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.CoordinateConverter
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.TimeUtils
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.global.handler.MapViewHandler
import net.hyntech.common.model.vo.BundleEbikeVo
import net.hyntech.common.ui.activity.OrgActivity
import net.hyntech.common.ui.adapter.EbikeNoAdapter
import net.hyntech.common.widget.baidumap.MyLocationListener
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.widget.popu.EBikeListPopu
import net.hyntech.usual.R
import net.hyntech.usual.databinding.ActivityEbikeTrackBinding
import net.hyntech.usual.vm.TrackViewModel
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

    private var tvTitle:TextView? = null
    private var ivArrowIcon: ImageView? = null
    private var tvStartTime:TextView? = null
    private var tvEndTime:TextView? = null
    private var ebikeNo:String? = ""

    private var mapView: TextureMapView? = null
    private var baiduMap: BaiduMap? = null

    private val buyDialog: CommonDialog by lazy { CommonDialog(this,UIUtils.getString(CR.string.common_warm),
        UIUtils.getString(CR.string.common_content_nobuy_service),
        UIUtils.getString(CR.string.common_close),
        UIUtils.getString(CR.string.common_buy_now),object :
            CommonDialog.OnClickListener{
            override fun onCancleClick() {
                //关闭
                onFinish()
            }
            override fun onConfirmClick() {
                //立即购买
                startActivityForResult(Intent(this@EbikeTrackActivity,AddValServiceActivity::class.java).putExtra(Constants.BundleKey.EXTRA_ID,ebikeId),EventCode.EVENT_CODE_TRACK)
            } }) }

    private lateinit var ebikeId:String
    private lateinit var ebikeList:List<BundleEbikeVo>

    private val ebikeAdapter by lazy { EbikeNoAdapter(this)}

    private val ebikePopu by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { EBikeListPopu<EbikeNoAdapter.ViewHolder, EbikeNoAdapter>(this,ebikeAdapter).apply {
        this.popupGravity = Gravity.BOTTOM
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
        mapView = findViewById(R.id.bmap_view)
        mapView?.let {
            it.showZoomControls(false) //设置隐藏放大缩小按钮
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

        findViewById<LinearLayout>(R.id.ll_left)?.setOnClickListener {
            onFinish()
        }

        findViewById<LinearLayout>(R.id.ll_title)?.setOnClickListener {
            onClickTitle()
        }

        findViewById<ImageButton>(R.id.ibtn_play)?.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                onEbikePlay()
            }
        }

        findViewById<ImageButton>(R.id.ibtn_replay)?.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                onEbikeReplay()
            }
        }

        findViewById<ImageButton>(R.id.ibtn_fast)?.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                onEbikeFast()
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
        ToastUtil.showToast("onEbikePlay")
    }
    private fun onEbikeReplay(){
        ToastUtil.showToast("onEbikeReplay")
    }
    private fun onEbikeFast(){
        ToastUtil.showToast("onEbikeFast")
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
                   // queryEbikeTrack()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 退出时销毁定位
        locClient.unRegisterLocationListener(locListener)
        locClient.stop()
    }
}