package net.hyntech.police.vm

import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.common.global.Constants
import net.hyntech.common.model.repository.CommonRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jetbrains.anko.collections.forEachWithIndex
import java.io.File

class EbikeRegisterViewModel:BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


    //上传图片
    fun uploadImageList(imegList: List<String>) {

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
        }, success = { data ->
            data?.imgUrl?.let {urls ->

            }
        })
    }
}