package net.hyntech.police.vm

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.global.Constants
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


    private val imageList:MutableList<String> = mutableListOf()

    fun editOwnerInfo(params: WeakHashMap<String, Any>, idCardAPath: String, idCardBPath: String) {
        //如果 A面 或 B面 有值,说明需要修改上传身份证照片
        imageList.clear()
        if(!TextUtils.isEmpty(idCardAPath)){
            imageList.add(idCardAPath)
        }

        if(!TextUtils.isEmpty(idCardBPath)){
            imageList.add(idCardBPath)
        }

        if(imageList.isNotEmpty()){
            //先上传照片再提交
            uploadIDCardImageList(params,imageList)
        }else{
            //直接提交
            saveSubmit(true,params)
        }
    }




   private fun uploadIDCardImageList(params: WeakHashMap<String, Any>,imegList: List<String>) {
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
               if(urlList.isNotEmpty()){
                   if(!TextUtils.isEmpty(data.idNoPic1)){
                        params.put("idNoPic1",data.idNoPic1)
                   }
                   if(!TextUtils.isEmpty(data.idNoPic2)){
                       params.put("idNoPic2",data.idNoPic2)
                   }
                   saveSubmit(false,params)
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


}