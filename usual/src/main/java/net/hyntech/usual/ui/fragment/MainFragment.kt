package net.hyntech.usual.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.baidu.mapapi.animation.Animation
import com.baidu.mapapi.animation.ScaleAnimation
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.global.handler.MapViewHandler
import net.hyntech.common.model.entity.SeverInfoEntity
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.ui.adapter.EBikeListAdapter
import net.hyntech.common.ui.adapter.SeverListAdapter
import net.hyntech.common.widget.popu.EBikeListPopu
import net.hyntech.usual.R
import net.hyntech.usual.databinding.FragmentMainBinding
import net.hyntech.usual.vm.HomeViewModel
import razerdp.basepopup.BasePopupWindow
import net.hyntech.common.R as CR


class MainFragment(viewModel: HomeViewModel):BaseFragment<FragmentMainBinding,HomeViewModel>(viewModel) {

    private var tvTitle:TextView? = null
    private var ivArrowIcon:ImageView? = null
    private var mapView: TextureMapView? = null
    private var baiduMap:BaiduMap? = null
    private val iconMarker by lazy { BitmapDescriptorFactory.fromResource(CR.drawable.icon_marker_car) }


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
            ivArrowIcon = this.findViewById(R.id.iv_arrow_icon)
            mapView = this.findViewById(R.id.bmap_view)
            mapView?.let {
                baiduMap = it.map
                baiduMap?.setMyLocationConfiguration(MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true,iconMarker))
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
            override fun onItemClick(item: SeverInfoEntity?) {
                item?.let {
                    ToastUtil.showToast(it.name)
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
                                tvTitle?.text = ebike.ebikeNo
                                ebike.isSelected = true
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
                    tvTitle?.setText(ebike.ebikeNo)
                addMarkers(ebike)
            }
            ebikeList = userInfo.ebike_list
        })

        tvTitle?.setOnClickListener {
            showEBikePopu()
        }
        viewModel.initLoadingEvent.observe(this, Observer {
            if(it) showLoading() else dismissLoading()
        })
        viewModel.currentLatLng.observe(this, Observer {
            val mapStatus: MapStatus = MapStatus.Builder()
                .target(it)
                .zoom(18.0f)
                .build()
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            val mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus)
            //改变地图状态
            baiduMap?.setMapStatus(mapStatusUpdate)
        })
        viewModel.getUserInfo(true)
    }

    private fun addMarkers(ebike: UserInfoEntity.EbikeListBean) {
        val latLng:LatLng? = LatLng(ebike.lastLat, ebike.lastLng)
        latLng?.let {ll ->
            val marker = baiduMap?.run {
                //添加之前删除 marker
                this.clear()
                val markerOptions = MarkerOptions().position(ll).icon(iconMarker)
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
            .zoom(18.0f)
            .overlook(0.0f)
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
}