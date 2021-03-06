package net.hyntech.usual.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.PhoneUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import net.hyntech.baselib.utils.*
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.ui.activity.SearchActivity
import net.hyntech.common.ui.adapter.MyFragmentStateAdapter
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivityConverServiceBinding
import net.hyntech.usual.ui.fragment.ServiceAllFragment
import net.hyntech.usual.ui.fragment.ServiceFixFragment
import net.hyntech.usual.ui.fragment.ServicePowerFragment
import net.hyntech.usual.ui.fragment.ServiceStoreFragment
import net.hyntech.usual.vm.ServiceViewModel

//便民服务
class ConverServiceActivity:BaseViewActivity<ActivityConverServiceBinding,ServiceViewModel>() {

    private val viewModel by viewModels<ServiceViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_conver_service

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    private var id:String? = ""
    private var lat:String? = ""
    private var lng:String? = ""

    private val rxPermissions: RxPermissions = RxPermissions(this)

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<ConverServiceActivity>(UIUtils.getString(CR.string.common_title_conver_service)).onBack<ConverServiceActivity> {
            onFinish()
        }.onSide<ConverServiceActivity> {
            startActivity(Intent(this,SearchActivity::class.java).putExtra(Constants.BundleKey.EXTRA_CONTENT,"请输入网点名称、地址"))
        }

         id = intent?.getStringExtra(Constants.BundleKey.EXTRA_ID)
         lat = intent?.getStringExtra(Constants.BundleKey.EXTRA_LAT)
         lng = intent?.getStringExtra(Constants.BundleKey.EXTRA_LNG)

        findViewById<ImageView>(R.id.iv_right)?.visibility = View.VISIBLE

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
            ServiceAllFragment.getInstance(id!!,lat!!,lng!!,viewModel),
            ServiceStoreFragment.getInstance(id!!,lat!!,lng!!,viewModel),
            ServiceFixFragment.getInstance(id!!,lat!!,lng!!,viewModel),
            ServicePowerFragment.getInstance(id!!,lat!!,lng!!,viewModel)
        )

        binding.pager.isUserInputEnabled = false
        val adapter: MyFragmentStateAdapter = MyFragmentStateAdapter(this, list)
        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pager.adapter = adapter
        binding.pager.currentItem = 0
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rbtn_all ->{
                    binding.pager.currentItem = 0
                }
                R.id.rbtn_store ->{
                    binding.pager.currentItem = 1
                }
                R.id.rbtn_fix ->{
                    binding.pager.currentItem = 2
                }
                R.id.rbtn_power ->{
                    binding.pager.currentItem = 3
                }
            }
        }
    }

    fun callPhone(phone:String){
        PermissionUtil.applyCallPhone(object : RequestPermission {
            override fun onRequestPermissionSuccess() {
                showConfirmDialog(phone)
            }

            override fun onRequestPermissionFailure(permissions: List<String>) {
                ToastUtil.showToast(UIUtils.getString(CR.string.common_permissions_phone))
            }

            override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                ToastUtil.showToast(UIUtils.getString(CR.string.common_permissions_phone))
            }
        }, rxPermissions)
    }

    private var callDialog:CommonDialog? = null
    private fun showConfirmDialog(phone: String){
        if(callDialog == null){
            callDialog = CommonDialog(this,UIUtils.getString(CR.string.common_warm),
                "您确定要拨打电话:${phone}？",
                UIUtils.getString(CR.string.common_text_cancle),
                UIUtils.getString(CR.string.common_text_confirm),object :
                    CommonDialog.OnClickListener{
                    override fun onCancleClick() {}
                    override fun onConfirmClick() {
                        call(phone)
                    } })
        }
        callDialog?.let { if(!it.isShowing) it.show()}
    }

    @SuppressLint("MissingPermission")
    private fun call(phone:String){
        PhoneUtils.call(phone)
    }


    override fun hasUsedEventBus(): Boolean = true

    override fun <T> onEventBusDispense(event: Event<T>) {
        super.onEventBusDispense(event)
        when(event.code){
            EventCode.EVENT_CODE_SEARCH ->{
                val content = event.data.toString()
                val index = binding.pager.currentItem
                when(index){
                    0 ->{viewModel.getServiceAllList(id!!,lat!!,lng!!,index.toString(),content)}
                    1 ->{viewModel.getServiceStoreList(id!!,lat!!,lng!!,index.toString(),content)}
                    2 ->{viewModel.getServiceFixList(id!!,lat!!,lng!!,index.toString(),content)}
                    3 ->{viewModel.getServicePowerList(id!!,lat!!,lng!!,index.toString(),content)}
                }
            }
        }
    }
}