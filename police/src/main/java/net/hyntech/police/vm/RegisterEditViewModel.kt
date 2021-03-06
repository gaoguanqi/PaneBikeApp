package net.hyntech.police.vm

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.UpImgEntity
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.model.repository.CommonRepository
import net.hyntech.common.utils.CommonUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jetbrains.anko.collections.forEachReversedWithIndex
import org.jetbrains.anko.collections.forEachWithIndex
import java.io.File
import java.lang.Exception
import java.util.*

class RegisterEditViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val userInfo: MutableLiveData<UserInfoEntity> = MutableLiveData()

    fun getUserInfo(idNo: String) {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            //idNo 410223200003264537
            params.put("idNo",idNo)
            repository.getUserInfo(params)
        }, success = {
            it?.let {data ->
                userInfo.postValue(data)
            }
        })
    }


    private val imageList:MutableList<UpImgEntity> = mutableListOf()


    //-----------车主信息修改----------------
    fun editOwnerInfo(params: WeakHashMap<String, Any>, idCardAPath: String, idCardBPath: String) {
        //如果 身份证 A面 或 B面 有值,说明需要修改上传身份证照片
        imageList.clear()
        if(!TextUtils.isEmpty(idCardAPath)){
            imageList.add(UpImgEntity("idNoPic1",idCardAPath))
        }

        if(!TextUtils.isEmpty(idCardBPath)){
            imageList.add(UpImgEntity("idNoPic2",idCardBPath))
        }

        if(imageList.isNotEmpty()){
            //先上传照片再提交
            uploadImageList(Constants.GlobalValue.IMAGE_TYPE_ID_NO,params,imageList)
        }else{
            //直接提交
            saveSubmit(true,params)
        }
    }




   private fun uploadImageList(imgType:String,params: WeakHashMap<String, Any>,imegList: List<UpImgEntity>) {
       defUI.showDialog.call()
       val builder: MultipartBody.Builder = MultipartBody.Builder()
           .setType(MultipartBody.FORM)
       builder.addFormDataPart("imgType", imgType)
       for (param in repository.getPublicParams(true)) {
           builder.addFormDataPart(param.key, param.value.toString())
       }
       imegList.forEachWithIndex { index, path ->
           val file = File(path.path)
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
               if(TextUtils.equals(Constants.GlobalValue.IMAGE_TYPE_ID_NO,imgType)){ //上传身份证 照片
                   if(!TextUtils.isEmpty(data.idNoPic1)){
                       params.put("idNoPic1",data.idNoPic1)
                   }
                   if(!TextUtils.isEmpty(data.idNoPic2)){
                       params.put("idNoPic2",data.idNoPic2)
                   }
                   saveSubmit(false,params)
               }else if(TextUtils.equals(Constants.GlobalValue.IMAGE_TYPE_USUAL,imgType)){ //上传车辆 照片
                   val urlList = CommonUtils.splitPicList(data.imgUrl)
                   if(urlList.isNotEmpty()){
                        try {
                            urlList.forEachWithIndex { i, s ->
                                params.put(imegList.get(i).key,s)
                            }
                            saveSubmit(false,params)
                        }catch (e:Exception){
                            e.fillInStackTrace()
                            defUI.dismissDialog.call()
                            LogUtils.logGGQ("上传照片异常---->>修改车辆信息")
                        }
                   }else{
                       defUI.dismissDialog.call()
                   }
               }
               defUI.dismissDialog.call()
           }?:let {
               defUI.dismissDialog.call()
           }
       },error = {
           defUI.dismissDialog.call()
       },isShowDialog = false,isShowToast = false)
    }


    val saveEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    private fun saveSubmit(isShow:Boolean,params: WeakHashMap<String, Any>){
        launchOnlyResult({
            repository.registerEbike(params)
        }, success = {
            saveEvent.call()
        },complete = {
            defUI.dismissDialog.call()
        },isShowDialog = isShow)
    }


    //-----------车辆信息修改-------------------
    fun editEbikeInfo(params: WeakHashMap<String, Any>, ebikeAPath: String, ebikeBPath: String, labelLocPath: String, invoicePath: String) {
        //如果 车辆 A面 或 B面 或 标签 或 发票 有值,说明需要修改上传车辆照片
        imageList.clear()
        if(!TextUtils.isEmpty(ebikeAPath)){
            imageList.add(UpImgEntity("ebikePic1",ebikeAPath))
        }

        if(!TextUtils.isEmpty(ebikeBPath)){
            imageList.add(UpImgEntity("ebikePic2",ebikeBPath))
        }

        if(!TextUtils.isEmpty(labelLocPath)){
            imageList.add(UpImgEntity("locatorPic",labelLocPath))
        }

        if(!TextUtils.isEmpty(invoicePath)){
            imageList.add(UpImgEntity("invoicePic",invoicePath))
        }
        if(imageList.isNotEmpty()){
            //先上传照片再提交
            uploadImageList(Constants.GlobalValue.IMAGE_TYPE_USUAL,params,imageList)
        }else{
            //直接提交
            saveSubmit(true,params)
        }
    }


}