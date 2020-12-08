package net.hyntech.common.ui.activity

import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.StringUtils
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.databinding.ActivityUserInfoBinding
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.utils.CommonUtils
import net.hyntech.common.vm.UserInfoViewModel
import net.hyntech.common.widget.dialog.BottomOptionDialog
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig

@Route(path = ARouterConstants.USER_INFO_PAGE)
class UserInfoActivity:BaseViewActivity<ActivityUserInfoBinding,UserInfoViewModel>() {

    private val bottomOptionDialog: BottomOptionDialog by lazy {
        BottomOptionDialog(this,object :BottomOptionDialog.OnClickListener{
            override fun onCameraClick() {
            }

            override fun onPhotoClick() {
            }

            override fun onCancelClick() {}
        })
    }

    private val viewModel by viewModels<UserInfoViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_user_info

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<UserInfoActivity>(UIUtils.getString(R.string.common_title_user_info)).onBack<UserInfoActivity>{
            onFinish(true)
        }

        viewModel.avatarEvent.observe(this, Observer {
            if(!bottomOptionDialog.isShowing){
                bottomOptionDialog.show()
            }
        })
        viewModel.phoneEvent.observe(this, Observer {
            ToastUtil.showToast("手机号")
        })

        AppDatabase.getInstance(BaseApp.instance).userDao().apply {
            this.getCurrentUser()?.let { user ->
                binding.tvName.text = user.username
                binding.tvIdcard.text = CommonUtils.coverIDCard(user.idCard)
                binding.tvPhone.text = user.phone
                if(!TextUtils.isEmpty(user.avatar)){
                    ImageLoader.getInstance().loadImage(
                        BaseApp.instance,
                        GlideImageConfig(user.avatar.toString(), binding.ivAvatar).also { it.type = TransType.CIRCLE })
                }
            }
        }
    }
}