package net.hyntech.police.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.AppUtils
import com.king.zxing.CaptureActivity
import com.king.zxing.Intents
import com.king.zxing.util.CodeUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.tbruyelle.rxpermissions2.RxPermissions
import net.hyntech.baselib.utils.*
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.ui.adapter.MyFragmentStateAdapter
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.widget.imgloader.engine.GlideEngine
import net.hyntech.police.R
import net.hyntech.police.databinding.ActivityEbikeRegisterBinding
import net.hyntech.police.ui.fragment.RegEbikeInfoFragment
import net.hyntech.police.ui.fragment.RegIdcardFragment
import net.hyntech.police.ui.fragment.RegOwnerInfoFragment
import net.hyntech.police.vm.EbikeRegisterViewModel
import org.w3c.dom.Text
import java.io.Serializable
import net.hyntech.common.R as CR

class EbikeRegisterActivity : BaseViewActivity<ActivityEbikeRegisterBinding, EbikeRegisterViewModel>() {


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

    private val viewModel by viewModels<EbikeRegisterViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_ebike_register

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.regEbikeResult.observe(this, Observer {
            //缴费确认
            startActivity(Intent(this,PaymentConfirmActivity::class.java).putExtra(Constants.BundleKey.EXTRA_ID,it))
            onFinish()
        })

        val list: List<Fragment> = listOf(
            RegIdcardFragment.getInstance(viewModel),
            RegOwnerInfoFragment.getInstance(viewModel),
            RegEbikeInfoFragment.getInstance(viewModel)
        )

        binding.pager.isUserInputEnabled = false
        val adapter: MyFragmentStateAdapter = MyFragmentStateAdapter(this, list)
        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pager.adapter = adapter
        binding.pager.currentItem = 0

        viewModel.getServicePackage()
        viewModel.getEbikeRegInfo()
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
            viewModel.idcardAPath.postValue(picPath)
        } else if (type == 2) {
            viewModel.idcardBPath.postValue(picPath)
        }else if(type == 3){
            viewModel.ebikeAPath.postValue(picPath)
        }else if(type == 4){
            viewModel.ebikeBPath.postValue(picPath)
        }else if(type == 5){
            viewModel.labelPath.postValue(picPath)
        }else if(type == 6){
            viewModel.invoicePath.postValue(picPath)
        }
    }

    fun onNextByIndex(index:Int){
        binding.pager.currentItem = index
    }

    fun onBack(){
        binding.pager.currentItem -= 1
    }


    fun startEbikeBrand(){
        startActivityForResult(Intent(this,EbikeBrandActivity::class.java),EventCode.EVENT_CODE_BRAND)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                EventCode.EVENT_CODE_BRAND ->{
                    val name = data?.getStringExtra(Constants.BundleKey.EXTRA_CONTENT)
                    name?.let {
                        viewModel.brandName.postValue(it)
                    }
                }

                EventCode.EVENT_CODE_SCAN ->{
                    val result = data?.getStringExtra(Intents.Scan.RESULT)
                    result?.let {
                        LogUtils.logGGQ("result-->${result}")
                        viewModel.scanCode.postValue(it)
                    }
                }
            }
        }
    }

    fun startScan() {
        startActivityForResult(Intent(this, CaptureActivity::class.java),EventCode.EVENT_CODE_SCAN)
    }


    fun startEdit(){
        if(viewModel.ebikeRegInfo?.userIdType != null){
            val bundle:Bundle = Bundle()
            bundle.putString(Constants.BundleKey.EXTRA_ID,viewModel.userInfo.value?.idNo)
            bundle.putSerializable(Constants.BundleKey.EXTRA_OBJ,viewModel.ebikeRegInfo?.userIdType as Serializable)
            startActivity(Intent(this,RegisterEditActivity::class.java).putExtras(bundle))
        }else{
            ToastUtil.showToast("数据加载中,请稍后！")
        }
    }
}