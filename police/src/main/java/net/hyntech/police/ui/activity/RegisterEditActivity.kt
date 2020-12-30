package net.hyntech.police.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_register_edit.*
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.common.widget.view.ClearEditText
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityRegisterEditBinding
import net.hyntech.police.ui.adapter.EbikeInfoAdapter
import net.hyntech.police.vm.RegisterEditViewModel


//已有信息在册
class RegisterEditActivity :BaseViewActivity<ActivityRegisterEditBinding,RegisterEditViewModel>(){

    private var idNo:String? = ""
    private var etInput: ClearEditText? = null

    private val ebikeAdapter by lazy { EbikeInfoAdapter(this).apply {
        this.setListener(object :EbikeInfoAdapter.OnClickListener{
            override fun onEditClick(item: UserInfoEntity.EbikeListBean?) {
                ToastUtil.showToast("编辑->>${item?.ebikeNo}")
            } }) } }

    private val viewModel by viewModels<RegisterEditViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_register_edit

    override fun bindViewModel() {
        LogUtils.logGGQ("vm1->${viewModel}")
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {

        setTitle<RegisterEditActivity>(UIUtils.getString(CR.string.common_title_owner_info)).onBack<RegisterEditActivity> {
            onFinish()
        }

        etInput = findViewById(R.id.et_input)
        etInput?.hint = "请输入车主身份证号"
        this.findViewById<Button>(R.id.btn_search)?.setOnClickListener {
            onClickProxy {
                val input = etInput?.text.toString().trim()
                if(TextUtils.isEmpty(input)){
                    ToastUtil.showToast("请输入车主身份证号")
                    return@onClickProxy
                }
                idNo = input
                viewModel.getUserInfo(idNo!!)
            }
        }

        this.findViewById<Button>(R.id.btn_add)?.setOnClickListener {
            onClickProxy {
                //新增车辆
//                startActivity(Intent(this,EbikeRegisterActivity::class.java))
                startActivity(Intent(this,OwnerInfoEditActivity::class.java))
            }
        }


        AppDatabase.getInstance(BaseApp.instance).userDao().apply {
            this.getCurrentUser()?.let {user ->
                idNo = user.idCard
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

        viewModel.userInfo.observe(this, Observer {
            it.user?.let {user ->
                setUserInfo(user)
            }

            it.ebike_list?.let { list->
                ebikeAdapter.setData(list)
            }
        })

        rv_ebike.layoutManager = LinearLayoutManager(this)
        rv_ebike.adapter = ebikeAdapter

        if(!TextUtils.isEmpty(idNo)){
            viewModel.getUserInfo(idNo!!)
        }

    }

    private fun setUserInfo(user: UserInfoEntity.UserBean) {
        binding.tvName.text = "${user.name}"
        binding.tvPhone.text = "${user.phone}"
        binding.tvAddress.text = "${user.addr}"
        binding.tvUserType.text = "${user.idType}"
        binding.tvOrgName.text = "${user.policeName}"


        ImageLoader.getInstance().apply {
            this.loadImage(BaseApp.instance,GlideImageConfig(user.idNoPic1, binding.ivIdcardA).also { config-> config.type = TransType.NORMAL })
            this.loadImage(BaseApp.instance,GlideImageConfig(user.idNoPic2, binding.ivIdcardB).also { config-> config.type = TransType.NORMAL })
        }
    }

}