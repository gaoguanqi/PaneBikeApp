package net.hyntech.police.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.police.R
import net.hyntech.police.databinding.FragmentMineBinding
import net.hyntech.police.vm.HomeViewModel


class MineFragment(viewModel: HomeViewModel):BaseFragment<FragmentMineBinding,HomeViewModel>(viewModel){

    private var tvName: TextView? = null
    private var ivAvatar: ImageView? = null

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
            ToastUtil.showToast("1")
        })

        viewModel.changePwdEvent.observe(this, Observer {
            ToastUtil.showToast("1")
        })
        viewModel.logoutEvent.observe(this, Observer {
            ToastUtil.showToast("1")
        })

    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

}