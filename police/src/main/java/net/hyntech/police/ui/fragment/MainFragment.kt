package net.hyntech.police.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.GsonUtils
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.model.entity.BannerEntity
import net.hyntech.common.model.entity.SeverInfoEntity
import net.hyntech.common.ui.adapter.MyBannerAdapter
import net.hyntech.common.ui.adapter.SeverListAdapter
import net.hyntech.common.widget.decoration.GridItemDecoration
import net.hyntech.police.R
import net.hyntech.police.databinding.FragmentMainBinding
import net.hyntech.police.ui.activity.*
import net.hyntech.common.R as CR
import net.hyntech.police.vm.HomeViewModel
import java.util.ArrayList

class MainFragment(val viewModel: HomeViewModel):BaseFragment<FragmentMainBinding,HomeViewModel>() {

    companion object {
        fun getInstance(viewModel: HomeViewModel): MainFragment {
            return MainFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {
        val bannerList:List<BannerEntity> = arrayListOf(BannerEntity("banner1","",R.drawable.pic_banner,1),BannerEntity("banner2","",R.drawable.pic_banner,1))
        binding.banner.addBannerLifecycleObserver(this).setAdapter(MyBannerAdapter(requireContext(),bannerList)).setIndicator(
            CircleIndicator(requireContext())
        ).setOnBannerListener(object :OnBannerListener<BannerEntity>{
            override fun OnBannerClick(data: BannerEntity?, position: Int) {
                data?.let {
                    ToastUtil.showToast(it.name)
                }
            }
        })

        val list:MutableList<SeverInfoEntity> = mutableListOf()
        AppDatabase.getInstance(BaseApp.instance).userDao().apply {
            this.getCurrentUser()?.let {user ->
                if(!TextUtils.isEmpty(user.menu)){
                    val menusList = GsonUtils.fromJson(user.menu,ArrayList::class.java)
                    if(menusList != null && menusList.isNotEmpty()){
                        menusList.forEach {item ->
                            when(item.toString()){
                                "collectorList" -> list.add(SeverInfoEntity(UIUtils.getString(CR.string.common_dot_manage),CR.drawable.icon_dot_manage,item.toString()))
                                "collector" -> list.add(SeverInfoEntity(UIUtils.getString(CR.string.common_device_info),CR.drawable.icon_device_info,item.toString()))
                                "serviceShop" -> list.add(SeverInfoEntity(UIUtils.getString(CR.string.common_conve_service),CR.drawable.icon_conver_service2,item.toString()))
                                "ebikeReg" -> list.add(SeverInfoEntity(UIUtils.getString(CR.string.common_car_up),CR.drawable.icon_car_info,item.toString()))
                                "regList" -> list.add(SeverInfoEntity(UIUtils.getString(CR.string.common_up_record),CR.drawable.icon_up_record,item.toString()))
                                "location_search" -> list.add(SeverInfoEntity(UIUtils.getString(CR.string.common_find_car),CR.drawable.icon_find_car,item.toString()))
                            }
                        }
                    }
                }
            }
        }

        binding.rvMain.layoutManager = GridLayoutManager(requireContext(),3)
        binding.rvMain.addItemDecoration(GridItemDecoration(requireContext()))
        val adapter: SeverListAdapter = SeverListAdapter(requireContext(),CR.layout.item_sever_police,list)
        adapter.setListener(object :SeverListAdapter.OnClickListener{
            override fun onItemClick(pos: Int,item: SeverInfoEntity?) {
                item?.let {
                    when(it.menu){
                        "collectorList" ->{  pointManage() } //点位管理
                        "collector" ->{ deviceInfo() }  //设备信息
                        "serviceShop" ->{ converService() } //便民服务
                        "ebikeReg" ->{ ebikeRegister() } // 车辆登记
                        "regList" ->{ regRecord() } //登记记录
                        "location_search" ->{ ebikeSearch() } // 车辆查找
                    }
                }
            }
        })
        binding.rvMain.adapter = adapter

        //用户数据
        viewModel.userInfo.observe(this, Observer {userInfo ->
            LogUtils.logGGQ("用户数据：${userInfo.user}")
        })
        viewModel.initLoadingEvent.observe(this, Observer {
            if(it) showLoading() else dismissLoading()
        })
        viewModel.getUserInfo(true)
    }

    //点位管理
    private fun pointManage(){
        startActivity(Intent(requireActivity(),PointManageActivity::class.java))
    }

    //设备信息
    private fun deviceInfo(){
        startActivity(Intent(requireActivity(),DeviceInfoActivity::class.java))
    }

    //便民服务
    private fun converService(){

    }

    //车辆登记
    private fun ebikeRegister(){
        startActivity(Intent(requireActivity(),EbikeRegisterActivity::class.java))
    }

    //登记记录
    private fun regRecord(){
        startActivity(Intent(requireActivity(), RecordPendingActivity::class.java))
    }

    //车辆查找
    private fun ebikeSearch(){
        startActivity(Intent(requireActivity(),FindEbikeActivity::class.java))
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



}