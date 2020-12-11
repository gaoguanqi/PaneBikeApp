package net.hyntech.usual.ui.activity

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.model.LatLng
import net.hyntech.baselib.utils.Event
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.EventCode
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.ui.adapter.MyFragmentStateAdapter
import net.hyntech.common.widget.baidumap.MyLocationListener
import net.hyntech.usual.R
import net.hyntech.usual.databinding.ActivityHomeBinding
import net.hyntech.usual.ui.fragment.MainFragment
import net.hyntech.usual.ui.fragment.MineFragment
import net.hyntech.usual.vm.HomeViewModel

@Route(path = ARouterConstants.USUAL_HOME_PAGE)
class HomeActivity : BaseViewActivity<ActivityHomeBinding,HomeViewModel>(), SensorEventListener {

    private var lastBackPressedMillis: Long = 0
    private val locClient: LocationClient by lazy { LocationClient(this) }
    private val locListener: MyLocationListener by lazy { MyLocationListener(object :MyLocationListener.LocationListener{
        override fun onReceive(bdLocation: BDLocation) {
            viewModel.currentLatLng.postValue(bdLocation)
        }
    }) }
    private val locClientOption: LocationClientOption by lazy { LocationClientOption().apply {
        this.isOpenGps = true //打开gps
        this.coorType = "bd09ll" //设置坐标类型
        this.setIsNeedAddress(true) //必须设置之后才能获取到详细的地址信息
//        this.scanSpan = 0 //可选3000，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
    } }


    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun hasStatusBarMode(): Boolean  = true
    override fun hasUsedEventBus(): Boolean = true

    override fun initData(savedInstanceState: Bundle?) {

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        val list: List<Fragment> = listOf(
            MainFragment.getInstance(viewModel),
            MineFragment.getInstance(viewModel)
        )


        binding.pager.isUserInputEnabled = false
        val adapter: MyFragmentStateAdapter = MyFragmentStateAdapter(this, list)
        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pager.adapter = adapter
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bnav.menu.getItem(position).isChecked = true
            }
        })
        binding.bnav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.item_nav_main ->{
                    binding.pager.currentItem = 0
                }
                R.id.item_nav_mine ->{
                    binding.pager.currentItem = 1
                }
            }
            false
        }

        initLocation()
    }

    private fun initLocation() {
        locClient.registerLocationListener(locListener)
        locClient.locOption = locClientOption
        locClient.start()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (lastBackPressedMillis + 2000 > System.currentTimeMillis()) {
                //moveTaskToBack(true)
                this@HomeActivity.finish()
            } else {
                lastBackPressedMillis = System.currentTimeMillis()
                ToastUtil.showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {}


    override fun <T> onEventBusDispense(event: Event<T>) {
        super.onEventBusDispense(event)
        when(event.code){
            EventCode.EVENT_CODE_AVATAR ->{
                val avatarUrl = event.data.toString()
                if(!TextUtils.isEmpty(avatarUrl)){
                    LogUtils.logGGQ("--event data:--${event.data}")
                    viewModel.avatarUrl.postValue(avatarUrl)
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
