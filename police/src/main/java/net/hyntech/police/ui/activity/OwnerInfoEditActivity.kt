package net.hyntech.police.ui.activity

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
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
import net.hyntech.common.model.entity.EbikeRegInfoEntity
import net.hyntech.common.model.vo.BundleUserVo
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.engine.GlideEngine
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityOwnerInfoEditBinding
import net.hyntech.police.vm.RegisterEditViewModel
import java.util.*

/**
 * 车主信息修改
 */
class OwnerInfoEditActivity:BaseViewActivity<ActivityOwnerInfoEditBinding, RegisterEditViewModel>() {

    private val params: WeakHashMap<String, Any> = WeakHashMap()
    //idType 不为空时 则修改了身份
    private var idType:String = ""
    private var idCardAPath:String = ""
    private var idCardBPath:String = ""

    private var userId:String = ""
    private val rxPermissions: RxPermissions = RxPermissions(this)

    private val applyDialog: CommonDialog by lazy {
        CommonDialog(this, UIUtils.getString(CR.string.common_permissions_title),
            UIUtils.getString(CR.string.common_permissions_camera),
            UIUtils.getString(CR.string.common_permissions_cancle),
            UIUtils.getString(CR.string.common_permissions_confirm), object :
                CommonDialog.OnClickListener {
                override fun onCancleClick() {}
                override fun onConfirmClick() {
                    AppUtils.launchAppDetailsSettings()
                }
            })
    }


    private val userTypeList:MutableList<EbikeRegInfoEntity.UserIdTypeBean> = mutableListOf()
    private val userTypePickerView by lazy {
        OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            val entity:EbikeRegInfoEntity.UserIdTypeBean = userTypeList.get(options1)
            binding.tvType.text = entity.name
            idType = entity.value
        }).apply {
            this.setContentTextSize(22)
            this.setTitleColor(UIUtils.getColor(CR.color.common_color_text))
            this.setCancelColor(UIUtils.getColor(CR.color.common_color_text))
            this.setSubmitColor(UIUtils.getColor(CR.color.common_color_text))
        }.build<EbikeRegInfoEntity.UserIdTypeBean>().apply {
            this.setTitleText("选择车主身份")
            this.setPicker(userTypeList)
        }
    }


    private val viewModel by viewModels<RegisterEditViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_owner_info_edit


    override fun bindViewModel() {
        LogUtils.logGGQ("vm2->${viewModel}")
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {

        setTitle<OwnerInfoEditActivity>(UIUtils.getString(CR.string.common_title_edit_info)).onBack<OwnerInfoEditActivity> {
            onFinish()
        }


        val bundle = intent.extras
        bundle?.let {
            val userInfo = it.getSerializable(Constants.BundleKey.EXTRA_OBJ) as BundleUserVo
            userInfo?.let { info ->
                userId = info.userId
                 binding.etName.setText("${info.name}")
                 binding.etPhone.setText("${info.phone}")
                 binding.etAddress.setText("${info.address}")
                binding.tvType.text = "${info.userType}"
                binding.tvOrgName.text = "${info.orgName}"
                val userType = info.userIdType as List<EbikeRegInfoEntity.UserIdTypeBean>
                userTypeList.addAll(userType)
                LogUtils.logGGQ("-->>${it.toString()}")

                ImageLoader.getInstance().apply {
                    this.loadImage(BaseApp.instance,
                        GlideImageConfig(info.idCardAPath, binding.ivIdcardA).also { config-> config.type = TransType.NORMAL })
                    this.loadImage(BaseApp.instance,
                        GlideImageConfig(info.idCardBPath, binding.ivIdcardB).also { config-> config.type = TransType.NORMAL })
                }
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

        this.findViewById<LinearLayout>(R.id.ll_type)?.setOnClickListener {
            onClickProxy {
                if(userTypeList.isNotEmpty() && !userTypePickerView.isShowing){
                    userTypePickerView.show()
                }
            }
        }

        this.findViewById<ImageView>(R.id.iv_idcard_a)?.setOnClickListener {
            onClickProxy {
                applyCamera(1)
            }
        }

        this.findViewById<ImageView>(R.id.iv_idcard_b)?.setOnClickListener {
            onClickProxy {
                applyCamera(2)
            }
        }


        this.findViewById<Button>(R.id.btn_save)?.setOnClickListener {
            onClickProxy {
                params.clear()
                val name = binding.etName.text.toString().trim()
                val phone = binding.etPhone.text.toString().trim()
                val address = binding.etAddress.text.toString().trim()

                params.put("dataSource","editUser")
                params.put("userId",userId)


                if(TextUtils.isEmpty(name)){
                    ToastUtil.showToast("请输入车主姓名")
                    return@onClickProxy
                }
                params.put("name",name)


                if(TextUtils.isEmpty(phone)){
                    ToastUtil.showToast("请输入联系方式")
                    return@onClickProxy
                }
                params.put("phone",phone)


                if(TextUtils.isEmpty(address)){
                    ToastUtil.showToast("请输入现居地址")
                    return@onClickProxy
                }
                params.put("addr",address)


                if(!TextUtils.isEmpty(idType)){
                    params.put("idType",idType)
                }

                viewModel.editOwnerInfo(params,idCardAPath,idCardAPath)
            }
        }


        viewModel.saveEvent.observe(this, androidx.lifecycle.Observer {
            //成功返回
            onFinish(true)
        })
    }


    fun applyCamera(type: Int) {
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

    private fun showApplyDialog() {
        if (!applyDialog.isShowing) {
            applyDialog.show()
        }
    }

    private fun openPhoto(type: Int) {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .selectionMode(PictureConfig.SINGLE)
            .isCompress(true)// 是否压缩
            .isGif(false)
            .isPreviewImage(true)
            .isEnablePreviewAudio(false) // 是否可播放音频
            .isCamera(false)// 是否显示拍照按钮
            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            .isEnableCrop(true)// 是否裁剪
            .withAspectRatio(
                Constants.GlobalValue.IDCARD_WIDTH,
                Constants.GlobalValue.IDCARD_HEIGHT
            )// 裁剪
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                    result?.last()?.let {
                        var picPath: String? = ""
                        if (it.isCut && !TextUtils.isEmpty(it.cutPath)) {
                            picPath = it.cutPath
                        }

                        if (TextUtils.isEmpty(picPath) && it.isCompressed && !TextUtils.isEmpty(it.compressPath)) {
                            picPath = it.compressPath
                        }

                        if (TextUtils.isEmpty(picPath)) {
                            picPath = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                it.androidQToPath
                            } else {
                                it.path
                            }
                        }
                        //显示图片
                        if (!TextUtils.isEmpty(picPath)) {
                            loadImgPath(type, picPath!!)
                        } else {
                            ToastUtil.showToast("选择照片出错,请重新选择！")
                        }
                    } ?: let {
                        ToastUtil.showToast("选择照片出错,请重新选择！！")
                    }
                }
                override fun onCancel() {
                    //ToastUtil.showToast("取消")
                }
            })
    }


    private fun loadImgPath(type: Int, picPath: String) {
        if (type == 1) {
            idCardAPath = picPath
            ImageLoader.getInstance().loadImage(BaseApp.instance, GlideImageConfig(idCardAPath, binding.ivIdcardA).also { config-> config.type = TransType.NORMAL })
        } else if (type == 2) {
            idCardBPath = picPath
            ImageLoader.getInstance().loadImage(BaseApp.instance, GlideImageConfig(idCardBPath, binding.ivIdcardB).also { config-> config.type = TransType.NORMAL })
        }
    }

}