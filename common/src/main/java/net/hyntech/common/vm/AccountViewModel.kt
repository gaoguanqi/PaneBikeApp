package net.hyntech.common.vm

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.JsonUtils
import net.hyntech.baselib.app.manager.SingleLiveEvent
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.model.entity.CommonEntity
import net.hyntech.common.model.entity.TestEntity
import net.hyntech.common.model.repository.CommonRepository
import net.hyntech.common.utils.HttpParamsUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class AccountViewModel : BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


    val bgDrawable: ObservableField<Drawable> = ObservableField()

    val verName: ObservableField<String> = ObservableField("")
    val companyEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val forgetPwdEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    val org: ObservableField<String> = ObservableField()
    val account: ObservableField<String> = ObservableField()
    val password: ObservableField<String> = ObservableField()


    init {
        verName.set("当前版本：${AppUtils.getAppVersionName()}")
    }


    fun onChooseCompany() {
        onClickProxy {
            companyEvent.call()
        }
    }


    fun onForgetPassword() {
        onClickProxy {
            forgetPwdEvent.call()
        }
    }

    fun onLogin() {
        onClickProxy {
//            val phone: String? = account.get()
//            val pwd: String? = password.get()
//            if (TextUtils.isEmpty(phone) && TextUtils.isEmpty(pwd)) {
//                ToastUtil.showToast("请输入账号和密码")
//            } else {
//                login(phone!!, pwd!!)
//            }

            l()
//            login()
//            loginTest()
        }

    }


    private fun loginTest(){
        launch(
            {
                val map:Map<String,Any> = HashMap()

//                map.putAll(NetWorkUtils.addPublicRequestParams())
//                map.put("phone","13717591366")
//                map.put("pwd","1111111")
//                val request = GsonUtils.toJson(map)


//                val request = LoginRequest("13717591366","111111")

                /**
                 * {
                "appVersion": "1.0.6",
                "mobileTye": "AN-Android",
                "netType": "WIFI",
                "nonce": "9ff9e2c0-5079-45b1-8ca3-99bf877239c9",
                "phone": "11啦咯啦咯啦咯啦",
                "pwd": "eerrrrrrrr",
                "sig": "2b29d32d2f9e092bbe10f046483ad2ff",
                "timestamp": "2020-11-27 17:49:57"
                }
                 */
                val request = TestEntity("1.0.6",
                    "AN-Android",
                    "WIFI",
                    "9ff9e2c0-5079-45b1-8ca3-99bf877239c9",
                    "13715795455",
                    "eerrrrrrrr",
                    "2b29d32d2f9e092bbe10f046483ad2ff",
                    "2020-11-27 17:49:57"
                )

                val json = GsonUtils.toJson(request)



                val body = JSONObject(map).toString().toRequestBody("application/json;charset=utf-8".toMediaType())
                val result:CommonEntity = repository.loginPhone(body).apply {
                    this.code
                    defUI.toastEvent.postValue("error:未知1")

                }
            },
            {
                //defUI.toastEvent.postValue("error:${it.code} -- ${it.errMsg}")
                defUI.toastEvent.postValue("error:未知2")
            },
            {
                LogUtils.logGGQ("回调完成 complete")
            }, true
        )
    }


    private fun l(){


        launch(
            {
                val map:MutableMap<String,Any> = mutableMapOf()
                map.putAll(HttpParamsUtils.addPublicRequestParams())
                map.put("phone","11111111111")
                map.put("pwd","111111")

                val body = JSONObject(map.toMap()).toString().toRequestBody("application/json;charset=utf-8".toMediaType())
                val result:CommonEntity = repository.loginPhone(body).apply {
                    this.code
                    defUI.toastEvent.postValue("error:未知1")

                }

                LogUtils.logGGQ("result->${result}")
            },
            {
                //defUI.toastEvent.postValue("error:${it.code} -- ${it.errMsg}")
                defUI.toastEvent.postValue("error:未知2")

            },
            {
                LogUtils.logGGQ("回调完成 complete")
            }, true
        )
    }
    private fun login() {

        //String转RequestBody String、ByteArray、ByteString都可以用toRequestBody()
//        val stringBody ="body参数".toRequestBody("application/json;charset=utf-8".toMediaType())
//        val request: Request =Request
//            .Builder()
//            .post(stringBody)
//            .build()

        //File转RequestBody
//        val file= File("")
//        val fileBody=file.asRequestBody("text/x-markdown; charset=utf-8".toMediaType())
//        val request = MultipartBody.Builder()
//            .addFormDataPart("file", file.name,fileBody)
//            .build()

        launch(
            {
//                val request = LoginRequest("13717591366","111111")



//                val json = GsonUtils.toJson(request)

                val params: MutableMap<String, Any> =
                    LinkedHashMap()
                params["name"] = "吴彦祖"
                params["request"] = "123456"
//                JsonHelper.toJSONString(params)
                val json = JsonUtils.formatJson("")
                val body = json.toRequestBody("application/json;charset=utf-8".toMediaType())
                val result:CommonEntity = repository.loginPhone(body).apply {
                    this.code
                    defUI.toastEvent.postValue("error:未知1")

                }
            },
            {
                //defUI.toastEvent.postValue("error:${it.code} -- ${it.errMsg}")
                defUI.toastEvent.postValue("error:未知2")
            },
            {
                LogUtils.logGGQ("回调完成 complete")
            }, true
        )
    }
}