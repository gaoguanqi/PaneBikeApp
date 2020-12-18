package net.hyntech.usual.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SizeUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_akey_alarm.*
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.*
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.model.entity.PhotoEntity
import net.hyntech.common.model.vo.BundleAlarmVo
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.ui.adapter.PhotoAdapter
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.widget.imgloader.engine.GlideEngine
import net.hyntech.usual.R
import net.hyntech.usual.databinding.ActivityAkeyAlarmBinding
import net.hyntech.usual.vm.ControllerViewModel
import java.util.*
import net.hyntech.common.R as CR


class AkeyAlarmActivity:BaseViewActivity<ActivityAkeyAlarmBinding,ControllerViewModel>() {

    private val viewModel by viewModels<ControllerViewModel>()
    private val rxPermissions: RxPermissions = RxPermissions(this)
    private val applyDialog: CommonDialog by lazy { CommonDialog(this,UIUtils.getString(CR.string.common_permissions_title),
        UIUtils.getString(CR.string.common_permissions_camera),
        UIUtils.getString(CR.string.common_permissions_cancle),
        UIUtils.getString(CR.string.common_permissions_confirm),object :
            CommonDialog.OnClickListener{
            override fun onCancleClick() {}
            override fun onConfirmClick() { AppUtils.launchAppDetailsSettings() } }) }


    private lateinit var ebikeList:List<BundleAlarmVo>

    private val photoList:MutableList<PhotoEntity> = mutableListOf()
    private var ebikeId:String = ""

    private val photoAdapter by lazy { PhotoAdapter(this,CR.layout.item_photo_del).apply {
        this.setListener(object : PhotoAdapter.OnClickListener{
            override fun onItemClick(pos:Int,item: PhotoEntity?,list:MutableList<PhotoEntity>) {
                item?.let {
                    val bundle = Bundle()
                    val array = java.util.ArrayList<String>()
                    list.forEach {
                        array.add(it.url)
                    }
                    bundle.putSerializable(Constants.BundleKey.EXTRA_OBJ,array)
                    bundle.putInt(Constants.BundleKey.EXTRA_INDEX,pos)
                    ARouter.getInstance().build(ARouterConstants.PREVIEW_PAGE)
                        .with(bundle)
                        .navigation()
                }
            }
            override fun onItemDel(pos: Int, item: PhotoEntity?) {
                delPhotoItem(pos)
            }
        })
    } }

    //展示车辆
    private val pickerList:MutableList<String> = mutableListOf()
    private val pickerView by lazy {
        OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            LogUtils.logGGQ("options1-->${options1}")
            if(ebikeList.size > 1){
                setData(ebikeList.get(options1))
            }else{
                LogUtils.logGGQ("只有一辆车，不用更新")
            }
        }).apply {
            this.setContentTextSize(22)
            this.setTitleColor(UIUtils.getColor(CR.color.common_color_text))
            this.setCancelColor(UIUtils.getColor(CR.color.common_color_text))
            this.setSubmitColor(UIUtils.getColor(CR.color.common_color_text))
        }.build<String>().apply {
            this.setTitleText("选择车辆")
        }
    }
    private fun delPhotoItem(pos: Int) {
        if(photoAdapter.getDataList().isNotEmpty() && photoAdapter.getDataList().size >= pos){
            photoAdapter.removeAtPosition(pos)
            if(photoList.size < 3 || photoAdapter.getListSize() < 3 || (photoList.size + photoAdapter.getListSize()) < 3){
                if(iv_add.visibility == View.GONE) iv_add.visibility = View.VISIBLE
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_akey_alarm

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {

        setTitle<AkeyAlarmActivity>(UIUtils.getString(CR.string.common_title_akey_alarm)).onBack<AkeyAlarmActivity> {
            onFinish()
        }

        rv.layoutManager = LinearLayoutManager(this).apply {
            this.orientation = LinearLayoutManager.HORIZONTAL
        }
        iv_add.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                applyCamera()
            }
        }
        btn_no_down.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                showPicker()
            }
        }
        btn_position.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                ARouter.getInstance().build(ARouterConstants.BAIDU_MAP_PAGE)
                    .navigation(this,101)
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
        viewModel.imgUrls.observe(this, Observer {
            LogUtils.logGGQ("上传成功->${it}")

            val address= et_position.text.toString().trim()

            val ebikeNo = tv_car_no.text.toString()
            val name = tv_name.text.toString()
            val phone = tv_phone.text.toString()
            val des = et_des.text.toString()

            LogUtils.logGGQ("车牌号->>${ebikeNo}")
            LogUtils.logGGQ("联系人->>${name}")
            LogUtils.logGGQ("联系电话->>${phone}")
            LogUtils.logGGQ("丢失地点->>${address}")
            LogUtils.logGGQ("描述内容->>${des}")
            LogUtils.logGGQ("车辆id->>${ebikeId}")
            //报警
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("alarmId","")
            params.put("ebikeId",ebikeId)
            params.put("stolenAddr",address)
            params.put("remark",des)
            params.put("relevantPic",it)

            viewModel.submitAlarm(params)
        })

        viewModel.alarmResultEvent.observe(this, Observer {
            ToastUtil.showToast("操作成功")

        })
        viewModel.alarmEvent.observe(this, Observer {
            ToastUtil.showToast("提交")
           val address= et_position.text.toString().trim()
            if(TextUtils.isEmpty(address)){
                ToastUtil.showToast("请填写丢失地点")
                return@Observer
            }
            if(photoAdapter.getDataList().isNullOrEmpty()){
                ToastUtil.showToast("请添加相关图片")
            }else{
                val imegList = mutableListOf<String>()
                photoAdapter.getDataList().forEach { item ->
                    imegList.add(item.url)
                }
                viewModel.uploadImageList(imegList)
            }
        })

        rv.adapter = photoAdapter
        photoAdapter.setData(photoList)
        val bundle = intent.extras
        bundle?.let {
            val type:Int = it.getInt(Constants.BundleKey.EXTRA_TYPE)
            LogUtils.logGGQ("--------type------------>>${type}")
            ebikeList = it.getSerializable(Constants.BundleKey.EXTRA_OBJ) as List<BundleAlarmVo>
            if(type == 1 && ebikeList.isNotEmpty()){
                btn_no_down.visibility = View.VISIBLE
                var vo = ebikeList.first()
                if(ebikeList.size > 1){
                    ebikeList.forEach {item ->
                        if(item.isSelected){
                            vo = item
                        }
                    }
                }
                setData(vo)
            }else if(type == 2 && ebikeList.isNotEmpty()){
                btn_no_down.visibility = View.GONE
                setData(ebikeList.first())
            }
        }

    }


    private fun showPicker() {
        pickerList.clear()
        ebikeList.forEach {item ->
            pickerList.add(item.ebikeNo)
        }
        pickerView.setPicker(pickerList)
        if(!pickerView.isShowing){
            pickerView.show()
        }
    }

    private fun setData(vo:BundleAlarmVo){
        ebikeId = vo.ebikeId
        tv_car_no.text = vo.ebikeNo
        tv_name.text = vo.name
        tv_phone.text = vo.phone
        et_position.setText(vo.address)
    }

    private fun applyCamera(){
        PermissionUtil.applyCamera(object : RequestPermission {
            override fun onRequestPermissionSuccess() {
                openPhoto()
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

    private fun openPhoto(){
        photoList.clear()
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .selectionMode(PictureConfig.MULTIPLE)
            .isCamera(false)
            .isCompress(true)// 是否压缩
            .maxSelectNum(3 - photoAdapter.getListSize())
            .isGif(false)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                    result?.let {
                        it.forEach { localMedia ->
                            var compressPath:String? = ""
                            if(localMedia.isCompressed && !TextUtils.isEmpty(localMedia.compressPath))
                                compressPath = localMedia.compressPath
                            else{
                                compressPath = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){ localMedia.androidQToPath }else{ localMedia.path }
                            }
                            if(!TextUtils.isEmpty(compressPath)){
                                photoList.add(PhotoEntity(compressPath,isDelete = true))
                            }
                        }
                        updataList()
                    }?:let {
                        ToastUtil.showToast("选择照片出错,请重新选择！")
                    }
                }
                override fun onCancel() {
                    ToastUtil.showToast("取消")
                }
            })
    }

    private fun updataList(){
        if(photoList.size >= 3 || photoAdapter.getListSize() >= 3 || (photoList.size + photoAdapter.getListSize()) >= 3){
            if(iv_add.visibility == View.VISIBLE) iv_add.visibility = View.GONE
        }
        photoAdapter.updataList(photoList)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                101 ->{
                    val content = data?.getStringExtra(Constants.BundleKey.EXTRA_CONTENT)
                   LogUtils.logGGQ("跳转返回-->>${content}")
                    if(!TextUtils.isEmpty(content)){
                        et_position?.setText(content)
                    }
                }
            }
        }
    }
}