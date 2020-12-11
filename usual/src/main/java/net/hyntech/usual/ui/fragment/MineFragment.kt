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
import net.hyntech.common.model.api.ApiURL
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

class MineFragment(val viewModel: HomeViewModel):BaseFragment<FragmentMineBinding,HomeViewModel>() {

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

        viewModel.avatarUrl.observe(this, Observer { url->
            if(ivAvatar != null && !TextUtils.isEmpty(url)){
                ImageLoader.getInstance().loadImage(
                    BaseApp.instance,
                    GlideImageConfig(url,ivAvatar!!).also { it.type = TransType.CIRCLE })
            }
        })

        viewModel.userInfo.observe(this, Observer {userInfo ->
            userInfo.user?.let {user ->
                tvName?.text = user.name
                tvPhone?.text = user.phone
                viewModel.avatarUrl.postValue(user.headimgurl)
            }
        })


        //个人资料
        viewModel.accountEvent.observe(this, Observer {
            //ToastUtil.showToast("1")
            //https://appweb.hyntech.net/antitheft/page/my/grxx/grzl.html
//            val bundle:Bundle = Bundle().apply {
//                this.putString(Constants.BundleKey.EXTRA_TITLE,"个人资料")
//                this.putString(Constants.BundleKey.EXTRA_URL,"https://appweb.hyntech.net/antitheft/page/my/grxx/grzl.html")
//            }
//            ARouter.getInstance().build(ARouterConstants.BROWSER_PAGE)
//                .with(bundle)
//                .navigation()


            ARouter.getInstance().build(ARouterConstants.USER_INFO_PAGE)
                .navigation()
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
            ToastUtil.showToast("我的保单")
            ///my/grxx/wdbd.html
             val bundle:Bundle = Bundle().apply {
                this.putString(Constants.BundleKey.EXTRA_TITLE,"我的保单")
                this.putString(Constants.BundleKey.EXTRA_URL,ApiURL.WEB_URL_MY_ORDER)
            }
            ARouter.getInstance().build(ARouterConstants.BROWSER_PAGE)
                .with(bundle)
                .navigation()

        })
        viewModel.myAddValEvent.observe(this, Observer {
            ToastUtil.showToast("1")
        })
        viewModel.myMessageEvent.observe(this, Observer {
            ToastUtil.showToast("1")
        })
        viewModel.changePwdEvent.observe(this, Observer {
//            ToastUtil.showToast("1")
//            val bundle:Bundle = Bundle().apply {
//                this.putString(Constants.BundleKey.EXTRA_TITLE,"修改密码")
//                this.putString(Constants.BundleKey.EXTRA_URL,"https://appweb.hyntech.net/antitheft/page/my/grxx/xgmm.html")
//            }
//            ARouter.getInstance().build(ARouterConstants.BROWSER_PAGE)
//                .with(bundle)
//                .navigation()

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