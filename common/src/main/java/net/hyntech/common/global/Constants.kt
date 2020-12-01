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
        const val API_URL = "api_url"
        const val NAME_ORG = "name_org"

    }


    object GlobalValue {
        const val BUILD_TYPE = "build_type"
    }

    object BundleKey {
        const val EXTRA_POLICE = "police"
        const val EXTRA_USUAL = "usual"
    }

}