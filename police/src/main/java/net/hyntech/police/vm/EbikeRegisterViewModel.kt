package net.hyntech.police.vm

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.EbikeBrandEntity
import net.hyntech.common.model.entity.EbikeRegInfoEntity
import net.hyntech.common.model.entity.ServiceSafeEntity
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.model.repository.CommonRepository
import net.hyntech.common.utils.CommonUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jetbrains.anko.collections.forEachWithIndex
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class EbikeRegisterViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val ownerInfoMap:MutableMap<String,String> = mutableMapOf()
    val ebikeInfoMap:HashMap<String,String> = hashMapOf()

    init {
        ownerInfoMap.put("birthday","")
        ownerInfoMap.put("userWork","")
        ownerInfoMap.put("crashName","")
        ownerInfoMap.put("crashPhone","")
    }
    val idCardImgList:MutableLiveData<List<String>> = MutableLiveData()
    val idcardAPath: MutableLiveData<String> = MutableLiveData()
    val idcardBPath: MutableLiveData<String> = MutableLiveData()

    val ebikeAPath: MutableLiveData<String> = MutableLiveData()
    val ebikeBPath: MutableLiveData<String> = MutableLiveData()
    val labelPath: MutableLiveData<String> = MutableLiveData()
    val invoicePath: MutableLiveData<String> = MutableLiveData()

    val userInfo: MutableLiveData<UserInfoEntity.UserBean> = MutableLiveData()
    //上传图片
    fun uploadImageList(imegList: List<String>) {
        defUI.showDialog.call()
        val builder: MultipartBody.Builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
        builder.addFormDataPart("imgType", Constants.GlobalValue.IMAGE_TYPE_ID_NO)
        for (param in repository.getPublicParams(true)) {
            builder.addFormDataPart(param.key, param.value.toString())
        }
        imegList.forEachWithIndex { index, path ->
            val file = File(path)
            if (file.exists()) {
                val body: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                builder.addFormDataPart("pic${index+1}", file.name, body)
            }
        }

        val partsList: List<MultipartBody.Part> = builder.build().parts
        launchOnlyResult({
            repository.uploadImageList(partsList)
        }, success = {
            it?.let { data ->
                val urlList = CommonUtils.splitPicList(data.imgUrl)
                if(urlList.isNotEmpty() && urlList.size >= 2){
                    idCardImgList.postValue(urlList)
                    idCardDistinguish(data.idNoPic1,data.idNoPic2,urlList.get(0),urlList.get(1))
                }else{
                    defUI.dismissDialog.call()
                }
            }?:let {
                defUI.dismissDialog.call()
            }
        },error = {
            defUI.dismissDialog.call()
        },isShowDialog = false,isShowToast = false)
    }


    val idCardNextEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    private fun idCardDistinguish(idNoPic1:String,idNoPic2:String,imgUrl1:String,imgUrl2:String){
        val params: WeakHashMap<String, Any> = WeakHashMap()
        params.put("idNoPic1",idNoPic1)
        params.put("idNoPic2",idNoPic2)
        params.put("imgUrl1",imgUrl1)
        params.put("imgUrl2",imgUrl2)
        launchOnlyResult({
            repository.idCardDistinguish(params)
        }, success = {
            it?.let { user ->
                userInfo.postValue(user.user)
                idCardNextEvent.call()
            }
        },complete = {
            defUI.dismissDialog.call()
        },isShowDialog = false)
    }

    var servicePackage: ServiceSafeEntity? = null
    fun getServicePackage() {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            repository.getServicePackage(params)
        }, success = {
            it?.let {data ->
                servicePackage = data
            }
        },isShowDialog = false,isShowToast = false)
    }

    var ebikeRegInfo: EbikeRegInfoEntity? = null
    fun getEbikeRegInfo() {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            repository.getEbikeRegInfo(params)
        }, success = {
            it?.let {data ->
                ebikeRegInfo = data
            }
        },isShowDialog = false,isShowToast = false)
    }
//--------------选择品牌----------------------------
    val brandName:MutableLiveData<String> = MutableLiveData()

    val ebikeBrandList: MutableLiveData<List<EbikeBrandEntity.EbikeTypeListBean>> = MutableLiveData()

    fun getEbikeBrand() {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("keyword","")
            repository.getEbikeBrand(params)
        }, success = {
            ebikeBrandList.postValue(it?.ebikeTypeList)
        })
    }

    val serchBrandList: MutableLiveData<List<EbikeBrandEntity.EbikeTypeListBean>> = MutableLiveData()
    private val serchBrands:MutableList<EbikeBrandEntity.EbikeTypeListBean> = mutableListOf()

    fun searchByBrand(keyword: String){
        ebikeBrandList.value?.let {
            serchBrands.clear()
            if(TextUtils.isEmpty(keyword)){
                serchBrands.addAll(it)
            }else{
                it.forEach {item ->
                    if(item.name.contains(keyword)){
                        serchBrands.add(item)
                    }
                }
            }
            if(serchBrands.isEmpty()){
                defUI.emptyEvent.call()
            }else{
                serchBrandList.postValue(serchBrands)
            }
        }?:let {
            defUI.emptyEvent.call()
        }
    }
}