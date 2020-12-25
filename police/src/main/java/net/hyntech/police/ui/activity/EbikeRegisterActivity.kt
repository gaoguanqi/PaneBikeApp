package net.hyntech.police.ui.activity

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import com.blankj.utilcode.util.AppUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.tbruyelle.rxpermissions2.RxPermissions
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.*
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.engine.GlideEngine
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityEbikeRegisterBinding
import net.hyntech.police.vm.EbikeRegisterViewModel

class EbikeRegisterActivity:BaseViewActivity<ActivityEbikeRegisterBinding,EbikeRegisterViewModel>() {

    private var idcardAPath:String? = ""
    private var idcardBPath:String? = ""

    private val rxPermissions: RxPermissions = RxPermissions(this)

    private val applyDialog: CommonDialog by lazy { CommonDialog(this,UIUtils.getString(CR.string.common_permissions_title),
        UIUtils.getString(CR.string.common_permissions_camera),
        UIUtils.getString(CR.string.common_permissions_cancle),
        UIUtils.getString(CR.string.common_permissions_confirm),object :
            CommonDialog.OnClickListener{
            override fun onCancleClick() {}
            override fun onConfirmClick() { AppUtils.launchAppDetailsSettings() } }) }

    private val viewModel by viewModels<EbikeRegisterViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_ebike_register

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<EbikeRegisterActivity>(UIUtils.getString(CR.string.common_title_ebike_register)).onBack<EbikeRegisterActivity> {
            onFinish()
        }.setRightTxt<EbikeRegisterActivity>("已有信息在册").onSide<EbikeRegisterActivity> {
            ToastUtil.showToast("已有信息在册")
        }

        binding.ivIdcardA.setOnClickListener {
            onClickProxy {
                applyCamera(1)
            }
        }
        binding.ivIdcardB.setOnClickListener {
            onClickProxy {
                applyCamera(2)
            }
        }

        binding.btnNext.setOnClickListener {
            onClickProxy {
                if(TextUtils.isEmpty(idcardAPath)){
                    ToastUtil.showToast("请")
                    return@onClickProxy
                }
            }
        }

    }


    private fun applyCamera(type:Int){
        PermissionUtil.applyCamera(object : RequestPermission {
            override fun onRequestPermissionSuccess() {
                openPhoto(type)
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

    private fun openPhoto(type: Int){
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .selectionMode(PictureConfig.SINGLE)
            .isCompress(true)// 是否压缩
            .isGif(false)
            .isPreviewImage(true)
            .isEnablePreviewAudio(false) // 是否可播放音频
            .isCamera(true)// 是否显示拍照按钮
            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            .isEnableCrop(true)// 是否裁剪
            .withAspectRatio(Constants.GlobalValue.IDCARD_WIDTH, Constants.GlobalValue.IDCARD_HEIGHT)// 裁剪
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                    result?.last()?.let {
                        var picPath:String? = ""
                        if(it.isCut && !TextUtils.isEmpty(it.cutPath)){
                            picPath = it.cutPath
                        }

                        if(TextUtils.isEmpty(picPath) && it.isCompressed && !TextUtils.isEmpty(it.compressPath)){
                            picPath = it.compressPath
                        }

                        if(TextUtils.isEmpty(picPath)){
                            picPath =  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                it.androidQToPath
                            }else{
                                it.path
                            }
                        }
                        //上传图片
                        if(!TextUtils.isEmpty(picPath)){
                            uploadImage(type,picPath!!)
                        }else{
                            ToastUtil.showToast("选择照片出错,请重新选择！")
                        }
                    }?:let {
                        ToastUtil.showToast("选择照片出错,请重新选择！！")
                    }
                }
                override fun onCancel() {
                    ToastUtil.showToast("取消")
                }
            })
    }

    private fun uploadImage(type: Int,path:String){
        if(type == 1){
            idcardAPath = path
            ImageLoader.getInstance().loadImage(
                BaseApp.instance,
                GlideImageConfig(path, binding.ivIdcardA).also { config -> config.type = TransType.NORMAL })
        }else if(type == 2){
            idcardBPath = path
            ImageLoader.getInstance().loadImage(
                BaseApp.instance,
                GlideImageConfig(path, binding.ivIdcardB).also { config -> config.type = TransType.NORMAL})
        }




    }

}