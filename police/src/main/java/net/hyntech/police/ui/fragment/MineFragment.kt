package net.hyntech.police.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.global.Constants
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.ui.activity.LoginActivity
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.FragmentMineBinding
import net.hyntech.police.vm.HomeViewModel


class MineFragment(val viewModel: HomeViewModel):BaseFragment<FragmentMineBinding,HomeViewModel>(){

    private var tvName: TextView? = null
    private var ivAvatar: ImageView? = null

    private val commonDialog by lazy { CommonDialog(requireContext(),UIUtils.getString(CR.string.common_logout),"确定退出登录?",listener = object :CommonDialog.OnClickListener{
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
        }

        viewModel.userInfo.observe(this, Observer {userInfo ->
            userInfo.user?.let {user ->
                tvName?.text = user.name
                if(ivAvatar != null && !TextUtils.isEmpty(user.headimgurl)){
                    ImageLoader.getInstance().loadImage(
                        BaseApp.instance,
                        GlideImageConfig(user.headimgurl,ivAvatar!!).also { it.type = TransType.CIRCLE })
                }
            }
        })


        viewModel.accountEvent.observe(this, Observer {
            ARouter.getInstance().build(ARouterConstants.USER_INFO_PAGE)
                .navigation()
        })

        viewModel.changePwdEvent.observe(this, Observer {
            ARouter.getInstance().build(ARouterConstants.RESET_PWD_PAGE)
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

    private fun onLogout() {
        AppDatabase.getInstance(BaseApp.instance).userDao().apply {
            this.getCurrentUser()?.let {user ->
                user.password = ""
                this.updateUser(user)
            }
        }
        this.requireActivity().apply {
            startActivity(Intent(requireContext(),LoginActivity::class.java).putExtra(
                Constants.GlobalValue.BUILD_TYPE,
                Constants.BundleKey.EXTRA_POLICE))
            finish()
        }
    }

}