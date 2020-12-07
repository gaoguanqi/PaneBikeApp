package net.hyntech.usual.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.*
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.ui.activity.LoginActivity
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.usual.R
import net.hyntech.usual.databinding.FragmentMineBinding
import net.hyntech.usual.ui.activity.EbikeInfoActivity
import net.hyntech.usual.vm.HomeViewModel

class MineFragment(viewModel: HomeViewModel):BaseFragment<FragmentMineBinding,HomeViewModel>(viewModel) {

    private var tvName:TextView? = null
    private var ivAvatar:ImageView? = null
    private var tvPhone:TextView? = null

    private val commonDialog by lazy { CommonDialog(requireContext(),UIUtils.getString(net.hyntech.common.R.string.common_logout),"确定退出登录?",listener = object :
        CommonDialog.OnClickListener{
        override fun onCancleClick() {}
        override fun onConfirmClick() { onLogout() } })
    }

    companion object {
        fun getInstance(viewModel: HomeViewModel): MineFragment {
            return MineFragment(viewModel)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initData(savedInstanceState: Bundle?) {
        view?.apply {
            tvName = this.findViewById(R.id.tv_name)
            ivAvatar = this.findViewById(R.id.iv_avatar)
            tvPhone = this.findViewById(R.id.tv_phone)
        }

        viewModel.userInfo.observe(this, Observer {userInfo ->
            userInfo.user?.let {user ->
                tvName?.text = user.name
                tvPhone?.text = user.phone
                if(ivAvatar != null && !TextUtils.isEmpty(user.headimgurl)){
                    ImageLoader.getInstance().loadImage(
                        BaseApp.instance,
                        GlideImageConfig(user.headimgurl,ivAvatar!!).also { it.type = TransType.CIRCLE })
                }
            }
        })


        viewModel.accountEvent.observe(this, Observer {
            ToastUtil.showToast("1")
        })
        viewModel.carInfoEvent.observe(this, Observer {

            viewModel.userInfo.value?.ebike_list?.let {ebikes ->
                LogUtils.logGGQ("---event ->>${ebikes.size}")
                val event:Event<List<UserInfoEntity.EbikeListBean>> = Event(EventCode.EVENT_CODE_EBIKES,ebikes)
                EventBusUtils.sendStickyEvent(event)
            }

            startActivity(Intent(requireContext(),EbikeInfoActivity::class.java))

        })
        viewModel.myOrderEvent.observe(this, Observer {
            ToastUtil.showToast("1")
        })
        viewModel.myAddValEvent.observe(this, Observer {
            ToastUtil.showToast("1")
        })
        viewModel.myMessageEvent.observe(this, Observer {
            ToastUtil.showToast("1")
        })
        viewModel.changePwdEvent.observe(this, Observer {
            ToastUtil.showToast("1")
            val bundle:Bundle = Bundle().apply {
                this.putString(Constants.BundleKey.EXTRA_TITLE,"修改密码")
                this.putString(Constants.BundleKey.EXTRA_URL,"https://appweb.hyntech.net/antitheft/page/my/grxx/xgmm.html")
            }
            ARouter.getInstance().build(ARouterConstants.BROWSER_PAGE)
                .with(bundle)
                .navigation()
        })
        viewModel.logoutEvent.observe(this, Observer {
            if(!commonDialog.isShowing){
                commonDialog.show()
            }
        })
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        LogUtils.logGGQ("我的 -->> lazyLoadData")
    }

    override fun refReshData() {
        super.refReshData()
        LogUtils.logGGQ("我的 -->> refReshData")
    }


    private fun onLogout() {
        AppDatabase.getInstance(BaseApp.instance).userDao().apply {
            this.getCurrentUser()?.let {user ->
                user.password = ""
                this.updateUser(user)
            }
        }
        this.requireActivity().apply {
            startActivity(
                Intent(requireContext(), LoginActivity::class.java).putExtra(
                    Constants.GlobalValue.BUILD_TYPE,
                    Constants.BundleKey.EXTRA_USUAL))
            finish()
        }
    }
}