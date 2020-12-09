package net.hyntech.common.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.IntentUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.tbruyelle.rxpermissions2.RxPermissions
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.*
import net.hyntech.common.R
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.databinding.ActivityUserInfoBinding
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.utils.CommonUtils
import net.hyntech.common.vm.UserInfoViewModel
import net.hyntech.common.widget.dialog.BottomOptionDialog
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.engine.GlideEngine
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig

@Route(path = ARouterConstants.USER_INFO_PAGE)
class UserInfoActivity:BaseViewActivity<ActivityUserInfoBinding,UserInfoViewModel>() {

    private var ivAvatar:ImageView? = null
    private val rxPermissions: RxPermissions = RxPermissions(this)
    private val applyDialog: CommonDialog by lazy { CommonDialog(this,UIUtils.getString(R.string.common_permissions_title),
        UIUtils.getString(R.string.common_permissions_camera),
        UIUtils.getString(R.string.common_permissions_cancle),
        UIUtils.getString(R.string.common_permissions_confirm),object :CommonDialog.OnClickListener{
            override fun onCancleClick() {}
            override fun onConfirmClick() { AppUtils.launchAppDetailsSettings() } }) }


    private val bottomOptionDialog: BottomOptionDialog by lazy {
        BottomOptionDialog(this,object :BottomOptionDialog.OnClickListener{
            override fun onCameraClick() {
                applyCamera(1)
            }

            override fun onPhotoClick() {
                applyCamera(2)
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

        ivAvatar = findViewById(R.id.iv_avatar)

        viewModel.avatarEvent.observe(this, Observer {
            if(!bottomOptionDialog.isShowing){
                bottomOptionDialog.show()
            }
        })
        viewModel.phoneEvent.observe(this, Observer {
            startActivity(Intent(this,AccountSafetyActivity::class.java))
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


    private fun applyCamera(type:Int){
        PermissionUtil.applyPermissions(object : RequestPermission {
            override fun onRequestPermissionSuccess() {
                if(type == 1){
                    openCamera()
                }else if(type == 2){
                    openPhoto()
                }
            }

            override fun onRequestPermissionFailure(permissions: List<String>) {
                showApplyDialog()
            }

            override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                showApplyDialog()
            }
        }, rxPermissions)
    }

    private fun showApplyDialog(){
        if(!applyDialog.isShowing){
            applyDialog.show()
        }
    }

    private fun openCamera(){
        PictureSelector.create(this)
            .openCamera(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .isCompress(true)// 是否压缩
            .forResult(object :OnResultCallbackListener<LocalMedia>{
                override fun onResult(result: MutableList<LocalMedia>?) {
                    result?.last()?.let {
                        var compressPath:String? = ""
                        if(it.isCompressed && !TextUtils.isEmpty(it.compressPath))
                            compressPath = it.compressPath
                        else{
                            compressPath = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                it.androidQToPath
                            }else{
                                it.path
                            }
                        }
                        //上传图片
                        if(!TextUtils.isEmpty(compressPath)){
                            uploadImage(compressPath)
                        }else{
                            ToastUtil.showToast("拍照出错,请重新拍照！")
                        }
                    }?:let {
                        ToastUtil.showToast("拍照出错,请重新拍照！")
                    }
                }

                override fun onCancel() {
                    ToastUtil.showToast("取消")
                }
            })
    }

    private fun uploadImage(path: String) {
        ivAvatar?.let { iv->
            ImageLoader.getInstance().loadImage(
                BaseApp.instance,
                GlideImageConfig(path, iv).also { config-> config.type = TransType.CIRCLE })
        }
    }

    private fun openPhoto(){
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .selectionMode(PictureConfig.SINGLE)
            .isCompress(true)// 是否压缩
            .isGif(false)
            .forResult(object :OnResultCallbackListener<LocalMedia>{
                override fun onResult(result: MutableList<LocalMedia>?) {
                    result?.last()?.let {
                        var compressPath:String? = ""
                        if(it.isCompressed && !TextUtils.isEmpty(it.compressPath))
                            compressPath = it.compressPath
                        else{
                            compressPath = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                it.androidQToPath
                            }else{
                                it.path
                            }
                        }
                        //上传图片
                        if(!TextUtils.isEmpty(compressPath)){
                            uploadImage(compressPath)
                        }else{
                            ToastUtil.showToast("选择照片出错,请重新选择！")
                        }
                    }?:let {
                        ToastUtil.showToast("选择照片出错,请重新选择！")
                    }
                }

                override fun onCancel() {
                    ToastUtil.showToast("取消")
                }
            })
    }
}