package net.hyntech.police.vm

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.global.Constants
import net.hyntech.common.model.entity.AddValServiceEntity
import net.hyntech.common.model.repository.CommonRepository
import net.hyntech.common.utils.CommonUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jetbrains.anko.collections.forEachWithIndex
import org.json.JSONObject
import java.io.File
import java.util.*

class EbikeRegisterViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


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


    private fun idCardDistinguish(idNoPic1:String,idNoPic2:String,imgUrl1:String,imgUrl2:String){
        val params: WeakHashMap<String, Any> = WeakHashMap()
        params.put("idNoPic1",idNoPic1)
        params.put("idNoPic2",idNoPic2)
        params.put("imgUrl1",imgUrl1)
        params.put("imgUrl2",imgUrl2)
        launchOnlyResult({
            repository.idCardDistinguish(params)
        }, success = {
            it?.let { userInfo ->

            }
        },complete = {
            defUI.dismissDialog.call()
        },isShowDialog = false)
    }

    var isSaveServicePackage: Boolean = false

    fun getServicePackage() {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            repository.getServicePackage(params)
        }, success = {
            it?.let {data ->
                val servicePackage = GsonUtils.toJson(data)
                if(!TextUtils.isEmpty(servicePackage)){
                    SPUtils.getInstance(BaseApp.instance.getAppPackage()).put(Constants.SaveInfoKey.SERVICE_PACKAGE,servicePackage)
                    isSaveServicePackage = true
                }
            }
        },isShowDialog = false,isShowToast = false)
    }

    var isSaveEbikeRegInfo: Boolean = false

    fun getEbikeRegInfo() {
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
            repository.getEbikeRegInfo(params)
        }, success = {
            it?.let {data ->
                val ebikeRegInfo = GsonUtils.toJson(data)
                if(!TextUtils.isEmpty(ebikeRegInfo)){
                    SPUtils.getInstance(BaseApp.instance.getAppPackage()).put(Constants.SaveInfoKey.EBIKE_REG_INFO,ebikeRegInfo)
                    isSaveEbikeRegInfo = true
                }
            }
        },isShowDialog = false,isShowToast = false)
    }
}