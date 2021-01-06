package net.hyntech.police.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.tbruyelle.rxpermissions2.RxPermissions
import net.hyntech.baselib.utils.*
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.PhotoEntity
import net.hyntech.common.model.entity.PositionData
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.ui.adapter.MyFragmentStateAdapter
import net.hyntech.common.ui.adapter.PhotoAdapter
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.widget.imgloader.engine.GlideEngine
import net.hyntech.police.R
import net.hyntech.police.databinding.ActivityShopSiteBinding
import net.hyntech.common.R as CR
import net.hyntech.police.ui.fragment.SiteAddFragment
import net.hyntech.police.ui.fragment.SiteDetailsFragment
import net.hyntech.police.ui.fragment.SiteEditFragment
import net.hyntech.police.vm.ShopSiteViewModel

class ShopSiteActivity:BaseViewActivity<ActivityShopSiteBinding,ShopSiteViewModel>() {

    private var currentItem:Int = 0
    private var serviceShopId:String = ""

    val photoList:MutableList<PhotoEntity> = mutableListOf()
    private val rxPermissions: RxPermissions = RxPermissions(this)
    private val applyDialog: CommonDialog by lazy { CommonDialog(this,
        UIUtils.getString(CR.string.common_permissions_title),
        UIUtils.getString(CR.string.common_permissions_camera),
        UIUtils.getString(CR.string.common_permissions_cancle),
        UIUtils.getString(CR.string.common_permissions_confirm),object :
            CommonDialog.OnClickListener{
            override fun onCancleClick() {}
            override fun onConfirmClick() { AppUtils.launchAppDetailsSettings() } }) }

    val photoAdapter by lazy { PhotoAdapter(this,CR.layout.item_photo_del).apply {
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


    private val viewModel by viewModels<ShopSiteViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_shop_site


    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {

        currentItem = intent?.getIntExtra(Constants.BundleKey.EXTRA_INDEX,0)?:0

        intent?.let {
            currentItem = it.getIntExtra(Constants.BundleKey.EXTRA_INDEX,0)
            serviceShopId = it.getStringExtra(Constants.BundleKey.EXTRA_ID)?:""
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


        val list: List<Fragment> = listOf(
            SiteAddFragment.getInstance(viewModel),
            SiteEditFragment.getInstance(serviceShopId,viewModel),
            SiteDetailsFragment.getInstance(viewModel)
        )

        binding.pager.isUserInputEnabled = false
        val adapter: MyFragmentStateAdapter = MyFragmentStateAdapter(this, list)
        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pager.adapter = adapter
        binding.pager.currentItem = currentItem
    }


    fun startMap(){
        ARouter.getInstance().build(ARouterConstants.BAIDU_MAP_PAGE).navigation(this,104)
    }


    private var ivAdd:ImageView? = null

    fun setAddView(iv:ImageView){
        this.ivAdd = iv
    }

    fun applyCamera(){
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
                            LogUtils.logGGQ("-->>>url${compressPath}")
                            if(!TextUtils.isEmpty(compressPath)){
                                photoList.add(PhotoEntity(compressPath,isDelete = true))
                            }
                        }
                        if(photoList.isNotEmpty()){
                            viewModel.uploadImageList(photoList)
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


    fun updataList(list:List<PhotoEntity>){
        if(list.size >= 3){
            ivAdd?.visibility == View.GONE
        }

        if(photoList.size >= 3 || photoAdapter.getListSize() >= 3 || (photoList.size + photoAdapter.getListSize()) >= 3){
            if(ivAdd?.visibility == View.VISIBLE) ivAdd?.visibility = View.GONE
        }

        photoAdapter.updataList(list)
    }

    private fun delPhotoItem(pos: Int) {
        if(photoAdapter.getDataList().isNotEmpty() && photoAdapter.getDataList().size >= pos){
            photoAdapter.removeAtPosition(pos)
            if(photoList.size < 3 || photoAdapter.getListSize() < 3 || (photoList.size + photoAdapter.getListSize()) < 3){
                if(ivAdd?.visibility == View.GONE) ivAdd?.visibility = View.VISIBLE
            }
        }

        if(photoAdapter.itemCount <= 0){
            photoList.clear()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                104 ->{
                    val content = data?.getStringExtra(Constants.BundleKey.EXTRA_CONTENT)
                    val lat = data?.getStringExtra(Constants.BundleKey.EXTRA_LAT)
                    val lng = data?.getStringExtra(Constants.BundleKey.EXTRA_LNG)
                    if(!TextUtils.isEmpty(content) && !TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng)){
                        viewModel.position.postValue(PositionData(content!!,lat!!,lng!!))
                    }
                }
            }
        }
    }



    fun getShopDetails(){
        viewModel.getShopDetails(serviceShopId)
    }


}