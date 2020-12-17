package net.hyntech.common.global

import android.os.Environment


class Constants {

    object Package{

    }

    object Path {
        val IMEI_PATH: String = Environment.getExternalStorageDirectory().toString() + "/android/imei.text"
    }

    object ApiParams {
        const val phone = "phone"
        const val pwd = "pwd"
    }

    object SaveInfoKey {
        const val HAS_WELCOME_USUAL = "welcome_usual"
        const val HAS_WELCOME_POLICE = "welcome_police"

        const val ACCESS_TOKEN = "accessToken"

        const val SEARCH = "search"

    }


    object GlobalValue {
        const val BUILD_TYPE = "build_type"

        const val IMAGE_TYPE_USUAL = "usual" //普通图片上传标识
        const val IMAGE_TYPE_ID_NO = "idNo" //身份证图片上传标识
    }

    object BundleKey {
        const val EXTRA_POLICE = "police"
        const val EXTRA_USUAL = "usual"
        const val EXTRA_TITLE = "title"
        const val EXTRA_URL = "url"
        const val EXTRA_OBJ = "obj"
        const val EXTRA_INDEX = "index"
        const val EXTRA_PHONE = "phone"
        const val EXTRA_IDCARD = "idcard"
        const val EXTRA_TYPE = "type"
        const val EXTRA_CONTENT = "content"
        const val EXTRA_PRICE = "price"
        const val EXTRA_ID = "id"
        const val EXTRA_LAT = "lat"
        const val EXTRA_LNG = "lng"
    }

}