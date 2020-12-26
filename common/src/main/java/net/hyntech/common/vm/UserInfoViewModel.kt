package net.hyntech.common.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.global.Constants
import net.hyntech.common.model.repository.CommonRepository
import net.hyntech.common.utils.CommonUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jetbrains.anko.collections.forEachWithIndex
import java.io.File
import java.util.*

class UserInfoViewModel : BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


    val avatarEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val phoneEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    val avatarUrl: MutableLiveData<String> = MutableLiveData()

    fun onClickAvatar() {
        onClickProxy {
            avatarEvent.call()
        }
    }

    fun onClickPhone() {
        onClickProxy {
            phoneEvent.call()
        }
    }
    //上传图片
    fun uploadImageList(imegList: List<String>) {
        defUI.showDialog.call()
        val builder: MultipartBody.Builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
        builder.addFormDataPart("imgType", Constants.GlobalValue.IMAGE_TYPE_USUAL)
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
            it?.imgUrl?.let {urls ->
               val urlList = CommonUtils.splitPicList(urls)
               if(urlList.isNotEmpty()){
                   editHeadImage(urlList.first())
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


    private fun editHeadImage(imgUrl:String){
        val params: WeakHashMap<String, Any> = WeakHashMap()
        params.put("headimgurl",imgUrl)
        launchOnlyResult({
            repository.editHeadImage(params)
        }, success = {
            avatarUrl.value = imgUrl
            defUI.toastEvent.postValue("修改成功")
        },complete = {
            defUI.dismissDialog.call()
        },isShowDialog = false)
    }
}