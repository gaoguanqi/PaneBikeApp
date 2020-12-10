package net.hyntech.common.vm

import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.global.Constants
import net.hyntech.common.model.repository.CommonRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jetbrains.anko.collections.forEachWithIndex
import org.xutils.x
import java.io.File

class UserInfoViewModel : BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    init {
        x.Ext.init(BaseApp.instance)
    }

    val avatarEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val phoneEvent: SingleLiveEvent<Any> = SingleLiveEvent()


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

    /**
     * .addFormDataPart("accessToken", "accessToken-6bd17fd24c9d4f5caa27c402d9babab5")
    .addFormDataPart("timestamp", "2020-12-10 17:05:14")
    .addFormDataPart("nonce", "0f2fd58c-ea8f-48e6-bc12-53a4b80a820d")
    .addFormDataPart("sig", "e274ea9509a3aaf8b64fd37ab2dcfdde")
    .addFormDataPart("imgType", "usual")
     */
    //上传图片
    fun uploadImageList(imegList: List<String>) {
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
        }, success = { data ->
            data?.imgUrl?.let {
                LogUtils.logGGQ("--img->>${it}")
            }
        })

    }
}