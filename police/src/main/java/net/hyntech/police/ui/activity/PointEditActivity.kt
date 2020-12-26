package net.hyntech.police.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.CollectorListEntity
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.police.R
import net.hyntech.police.databinding.ActivityPointEditBinding
import net.hyntech.police.vm.PointManageViewModel
import java.util.WeakHashMap

class PointEditActivity:BaseViewActivity<ActivityPointEditBinding,PointManageViewModel>() {

    private var collectorUuid:String? = ""
    private var collectorId:String? = ""
    private var collectorNo110:String? = ""
    private var orgId:String? = ""
    private var lat:String? = ""
    private var lng:String? = ""
    private var addr:String? = ""


    private var btnSubmit:Button? = null
    private val viewModel by viewModels<PointManageViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_point_edit

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {

        btnSubmit = findViewById(R.id.btn_submit)

        this.intent?.extras?.let {
            val type:Int = it.getInt(Constants.BundleKey.EXTRA_TYPE,0)
            var title:String = ""
            var submit:String = ""
            // type 0  add , 1 edit
            if(type == 0){
                title = "手动采点"
                submit = "保存"
                addr = Constants.Location.address
                lat = Constants.Location.latitude.toString()
                lng = Constants.Location.longitude.toString()
                AppDatabase.getInstance(BaseApp.instance).userDao().apply {
                    this.getCurrentUser()?.let { user ->
                        orgId = user.orgId
                        binding.tvOrgName.text = "${user.orgName}"
                    }
                }
            }else if(type == 1){
                title = "修改数据"
                submit = "修改"
                val collector = it.getSerializable(Constants.BundleKey.EXTRA_OBJ) as CollectorListEntity.AtCollectorListBean
                collector?.let { bean ->
                    collectorUuid = bean.collectorUuid
                    collectorId = bean.collectorId
                    collectorNo110 = bean.collectorNo110
                    orgId = bean.orgId
                    lat = bean.lat.toString()
                    lng = bean.lng.toString()
                    addr = bean.addr

                    binding.etId.setText("${collectorId}")
                    binding.etLookNo.setText("${collectorNo110}")
                    binding.tvOrgName.text = "${bean.orgName}"
                }
            }
            if(!TextUtils.isEmpty(addr)){
                binding.etAddress.setText("${addr}")
                binding.tvLatlng.text = "(${lat},${lng})"
            }

            setTitle<PointEditActivity>(title).onBack<PointEditActivity> {
                onFinish()
            }

            btnSubmit?.text = submit
            btnSubmit?.setOnClickListener {
                onClickProxy {
                    onSubmit(type)
                }
            }
        }

        findViewById<ImageButton>(R.id.btn_position)?.setOnClickListener {
           onClickProxy {
               ARouter.getInstance().build(ARouterConstants.BAIDU_MAP_PAGE).navigation(this@PointEditActivity,103)
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

        viewModel.collectorSuccess.observe(this, Observer {
            ToastUtil.showToast(it)
            onFinish(true)
        })
    }

    private fun onSubmit(type: Int) {
        collectorId = binding.etId.text.toString().trim()
        if(TextUtils.isEmpty(collectorId)){
            ToastUtil.showToast("请输入设备ID号")
            return
        }

        addr = binding.etAddress.text.toString().trim()

        if(TextUtils.isEmpty(addr) || TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)){
            ToastUtil.showToast("请选择地址")
            return
        }

        collectorNo110 = binding.etLookNo.text.toString().trim()
        val params: WeakHashMap<String, Any> = WeakHashMap()
        params.put("collectorUuid",collectorUuid)
        params.put("collectorId",collectorId)
        params.put("collectorNo110",collectorNo110)
        params.put("addr",addr)
        params.put("lat",lat)
        params.put("lng",lng)
        params.put("orgId",orgId)
        viewModel.collectorSave(type,params)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                103 ->{
                    val content = data?.getStringExtra(Constants.BundleKey.EXTRA_CONTENT)
                    val lat = data?.getStringExtra(Constants.BundleKey.EXTRA_LAT)
                    val lng = data?.getStringExtra(Constants.BundleKey.EXTRA_LNG)
                    LogUtils.logGGQ("跳转返回-->>${content}")
                    if(!TextUtils.isEmpty(content)){
                        binding.etAddress.setText("${content}")
                        this.addr = content
                    }

                    if(!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng)){
                        binding.tvLatlng.text = "(${lat},${lng})"
                        this.lat = lat
                        this.lng = lng
                    }
                }
            }
        }
    }
}