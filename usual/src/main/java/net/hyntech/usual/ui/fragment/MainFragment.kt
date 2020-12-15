package net.hyntech.usual.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.baidu.mapapi.animation.Animation
import com.baidu.mapapi.animation.ScaleAnimation
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.http.t
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.global.Constants
import net.hyntech.common.global.handler.MapViewHandler
import net.hyntech.common.model.entity.EbikeErrorEntity
import net.hyntech.common.model.entity.SeverInfoEntity
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.model.vo.BundleAlarmVo
import net.hyntech.common.ui.adapter.EBikeListAdapter
import net.hyntech.common.ui.adapter.SeverListAdapter
import net.hyntech.common.widget.popu.EBikeListPopu
import net.hyntech.usual.R
import net.hyntech.usual.databinding.FragmentMainBinding
import net.hyntech.usual.ui.activity.AkeyAlarmActivity
import net.hyntech.usual.ui.activity.EbikeErrorActivity
import net.hyntech.usual.vm.HomeViewModel
import razerdp.basepopup.BasePopupWindow
import net.hyntech.common.R as CR


class MainFragment(val viewModel: HomeViewModel):BaseFragment<FragmentMainBinding,HomeViewModel>() {

    private var tvTitle:TextView? = null
    private var tvLock:TextView? = null
    private var llTitle:LinearLayout? = null
    private var tvFab:TextView? = null
    private var ivArrowIcon:ImageView? = null
    private var mapView: TextureMapView? = null
    private var baiduMap:BaiduMap? = null
    private val ebikeMarker by lazy { BitmapDescriptorFactory.fromResource(CR.drawable.icon_marker_car) }
    private val gcodingMarker by lazy { BitmapDescriptorFactory.fromResource(CR.drawable.icon_gcoding) }


    private var ebikeList: MutableList<UserInfoEntity.EbikeListBean>? = null
    private val ebikeAdapter by lazy { EBikeListAdapter(requireContext()) }
    private val ebikePopu by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { EBikeListPopu<EBikeListAdapter.ViewHolder,EBikeListAdapter>(requireContext(),ebikeAdapter).apply {
        this.popupGravity = Gravity.BOTTOM
        this.setOnPopupWindowShowListener { ivArrowIcon?.background = UIUtils.getDrawable(CR.drawable.ic_arrow_up) }
        this.onDismissListener = object :BasePopupWindow.OnDismissListener(){ override fun onDismiss() { ivArrowIcon?.background = UIUtils.getDrawable(CR.drawable.ic_arrow_down) } } }
    }

    companion object {
        fun getInstance(viewModel: HomeViewModel): MainFragment {
            return MainFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun initData(savedInstanceState: Bundle?) {

        view?.apply {
            tvTitle = this.findViewById(R.id.tv_main_title)
            tvLock = this.findViewById(R.id.tv_lock)
            llTitle = this.findViewById(R.id.ll_title)
            tvFab = this.findViewById(R.id.tv_fab)
            ivArrowIcon = this.findViewById(R.id.iv_arrow_icon)
            mapView = this.findViewById(R.id.bmap_view)
            mapView?.let {
                baiduMap = it.map
                // 开启定位图层
                baiduMap?.isMyLocationEnabled = true
                //自定义标签
//                baiduMap?.setMyLocationConfiguration(MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true,gcodingMarker))
                MapViewHandler(this@MainFragment).setMapView(it)
            }
        }


        val list:List<SeverInfoEntity> = arrayListOf(
            SeverInfoEntity(UIUtils.getString(CR.string.common_car_info),CR.drawable.icon_car_locus),
            SeverInfoEntity(UIUtils.getString(CR.string.common_conve_service),CR.drawable.icon_conver_service),
            SeverInfoEntity(UIUtils.getString(CR.string.common_call_police),CR.drawable.icon_call_police),
            SeverInfoEntity(UIUtils.getString(CR.string.common_the_safe),CR.drawable.icon_the_safe))

        binding.rvMain.layoutManager = GridLayoutManager(requireContext(),4)
        val adapter: SeverListAdapter = SeverListAdapter(requireContext(),CR.layout.item_sever_usual,list)
        adapter.setListener(object :SeverListAdapter.OnClickListener{
            override fun onItemClick(pos:Int,item: SeverInfoEntity?) {
                when(pos){
                    0 -> onCarInfo()
                    1 -> onConverService()
                    2 -> onAkeyAlarm()
                    3 -> onTheSafe()
                }
            }
        })
        binding.rvMain.adapter = adapter
        ebikeAdapter.setListener(object :EBikeListAdapter.OnClickListener{
            override fun onItemClick(entity: UserInfoEntity.EbikeListBean?) {
                entity?.let {ebike ->
                    AppDatabase.getInstance(BaseApp.instance).userDao().apply {
                        this.getCurrentUser()?.let {user ->
                            if(!TextUtils.equals(user.ebikeNo,ebike.ebikeNo)){
                                user.ebikeNo = ebike.ebikeNo
                                this.updateUser(user)
                                ebikeList?.forEach { item ->
                                     item.isSelected = false
                                }
                                ebike.isSelected = true
                                tvTitle?.text = ebike.ebikeNo
                                setEbikeLock(ebike.lockFlag)
                                viewModel.currentEbike.set(ebike)
                                ebikeAdapter.notifyDataSetChanged()
                                addMarkers(ebike)
                            }
                        }
                    }
                }
                ebikePopu.dismiss()
            }
        })

        //用户数据
        viewModel.userInfo.observe(this, Observer {userInfo ->
            viewModel.currentEbike.get()?.let {ebike ->
                tvTitle?.text = ebike.ebikeNo
                setEbikeLock(ebike.lockFlag)
                addMarkers(ebike)
            }?:let {
                tvTitle?.text = ""
                tvLock?.text = ""
                tvFab?.text = "暂无车辆"
            }
            ebikeList = userInfo.ebike_list
        })
        viewModel.noticeEvent.observe(this, Observer {
            startActivity(Intent(requireActivity(),EbikeErrorActivity::class.java))
        })

        llTitle?.setOnClickListener {
            showEBikePopu()
        }
        tvFab?.setOnClickListener {
            viewModel.onLockState()
        }

        viewModel.initLoadingEvent.observe(this, Observer {
            if(it) showLoading() else dismissLoading()
        })
        viewModel.currentLatLng.observe(this, Observer {
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
        })
        viewModel.ebikeLockFlag.observe(this, Observer {
            setEbikeLock(it)
            ToastUtil.showToast("操作成功")
        })
        viewModel.getUserInfo(true)
    }



    private fun setEbikeLock(lockFlag: Int){
        //"lockFlag"   0, 未上锁 锁车  1 已上锁 解锁
        if(lockFlag == 1){
            tvLock?.text = "已上锁"
            tvFab?.text = "解锁"
        }else{
            tvLock?.text = "未上锁"
            tvFab?.text = "锁车"
        }
    }

    private fun addMarkers(ebike: UserInfoEntity.EbikeListBean) {
        val latLng:LatLng? = LatLng(ebike.lastLat, ebike.lastLng)
        latLng?.let {ll ->
            val marker = baiduMap?.run {
                //添加之前删除 marker
                this.clear()
                val markerOptions = MarkerOptions().position(ll).icon(ebikeMarker)
                markerOptions?.alpha(0.9f);//marker图标透明度，0~1.0，默认为1.0
//                markerOptions?.animateType(MarkerOptions.MarkerAnimateType.drop) ////marker出现的方式，从天上掉下
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


    private val markerAnim by lazy {
        ScaleAnimation(1f, 1.2f, 1f).apply {
            this.setDuration(2000L)
            this.setRepeatMode(Animation.RepeatMode.RESTART)
            this.setRepeatCount(2)
        }
    }

    private fun showEBikePopu(){
        ebikeList?.let {list ->
            ebikeAdapter.setData(list)
            ebikePopu.showPopupWindow(tvTitle)
        }
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun lazyLoadData() {
        super.lazyLoadData()
        LogUtils.logGGQ("lazyLoadData--->>")
        viewModel.getMessageCount()
    }

    override fun refReshData() {
        super.refReshData()
        LogUtils.logGGQ("refReshData--->>")
        viewModel.getUserInfo()
        viewModel.getMessageCount()
    }


    override fun onPause() {
        super.onPause()
        LogUtils.logGGQ("--onPause-")
    }

    //车辆信息
    private fun onTheSafe() {

    }
    //便民服务
    private fun onConverService() {

    }

    //一键报警
    private fun onAkeyAlarm() {
//        if(!ebikeList.isNullOrEmpty()){
//            val array = java.util.ArrayList<BundleAlarmVo>()
//            ebikeList?.forEach {item ->
//                val vo = BundleAlarmVo()
//                item.isSelected = vo.isSelected
//                vo.ebikeId = item.ebikeId
//                vo.ebikeNo = item.ebikeNo
//                vo.name = viewModel.userInfo.value?.user?.name
//                vo.phone = viewModel.userInfo.value?.user?.phone
//                vo.address = ""
//                array.add(vo)
//            }
//            val bundle:Bundle = Bundle()
//            bundle.putInt(Constants.BundleKey.EXTRA_TYPE,1)
//            bundle.putSerializable(Constants.BundleKey.EXTRA_OBJ,array)
//            startActivity(Intent(requireActivity(), AkeyAlarmActivity::class.java).putExtras(bundle))
//        }else{
//            ToastUtil.showToast("数据加载中,请稍后")
//        }

        viewModel.currentEbike.get()?.let {
            //lost 已赔付 alarm 已报警 normal 正常
            LogUtils.logGGQ("一键报警-state->${it.state}")
            if(!TextUtils.isEmpty(it.state) && TextUtils.equals(it.state,"lost")){
                ToastUtil.showToast("该车辆已赔付,无需报警")
            }else if(!TextUtils.isEmpty(it.state) && TextUtils.equals(it.state,"alarm")){
                ToastUtil.showToast("该车辆已报警,不可重复报警")
            }else{
                val array = java.util.ArrayList<BundleAlarmVo>()
                val vo = BundleAlarmVo()
                vo.isSelected = it.isSelected
                vo.ebikeId = it.ebikeId
                vo.ebikeNo = it.ebikeNo
                vo.name = viewModel.userInfo.value?.user?.name
                vo.phone = viewModel.userInfo.value?.user?.phone
                vo.address = ""
                array.add(vo)

                val bundle:Bundle = Bundle()
                bundle.putInt(Constants.BundleKey.EXTRA_TYPE,1)
                bundle.putSerializable(Constants.BundleKey.EXTRA_OBJ,array)
                startActivity(Intent(requireActivity(), AkeyAlarmActivity::class.java).putExtras(bundle))
            }
        }?:let {
            ToastUtil.showToast("数据加载中,请稍后")
        }
    }
    //防盗保障
    private fun onCarInfo() {

    }
}