package net.hyntech.police.vm

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.PhotoEntity
import net.hyntech.common.model.entity.PositionData
import net.hyntech.common.model.entity.ShopSiteEntity
import net.hyntech.common.model.repository.CommonRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jetbrains.anko.collections.forEachWithIndex
import java.io.File
import java.util.*

class ShopSiteViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val defDrawable: Drawable by lazy { UIUtils.getDrawable(R.drawable.shape_solid) }
    val sefDrawable: Drawable by lazy { UIUtils.getDrawable(R.drawable.shape_stroke) }
    val defColor:Int by lazy { UIUtils.getColor(R.color.common_black) }
    val sefColor:Int by lazy { UIUtils.getColor(R.color.common_color_text) }


    val position: MutableLiveData<PositionData> = MutableLiveData()

    val shopDetails: MutableLiveData<ShopSiteEntity.AtServiceShopBean> = MutableLiveData()


    fun getShopDetails(serviceShopId: String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("serviceShopId",serviceShopId)
            repository.getShopDetails(params)
        }, success = {
            it?.let {data ->
                shopDetails.postValue(data.atServiceShop)
            }
        })
    }


    //照片上传
    val picURL:MutableLiveData<String> = MutableLiveData()
    fun uploadImageList(imegList: MutableList<PhotoEntity>) {
        val builder: MultipartBody.Builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
        builder.addFormDataPart("imgType", Constants.GlobalValue.IMAGE_TYPE_USUAL)
        for (param in repository.getPublicParams(true)) {
            builder.addFormDataPart(param.key, param.value.toString())
        }
        imegList.forEachWithIndex { index, path ->
            val file = File(path.url)
            if (file.exists()) {
                val body: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                builder.addFormDataPart("pic${index+1}", file.name, body)
            }
        }
        val partsList: List<MultipartBody.Part> = builder.build().parts
        launchOnlyResult({
            repository.uploadImageList(partsList)
        }, success = {
            it?.imgUrl?.let {url ->
                picURL.postValue(url)
            }
        })
    }

    val saveEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    fun saveServiceShop(params: WeakHashMap<String, Any>){
        launchOnlyResult({
            repository.saveServiceShop(params)
        }, success = {
            saveEvent.call()
        })
    }

}