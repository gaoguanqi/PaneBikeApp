package net.hyntech.common.global

import android.os.Environment


class Constants {

    object Package{

    }

    object Path {
        val IMEI_PATH: String = Environment.getExternalStorageDirectory().toString() + "/android/imei.text"
    }

    object ApiParams {
        const val USERNAME = "username"
        const val IMEI = "imei"
    }

    object SaveInfoKey {
        const val HAS_WELCOME_USUAL = "welcome_usual"
        const val HAS_WELCOME_POLICE = "welcome_police"
    }


    object GlobalValue {
        const val BUILD_TYPE = "build_type"
    }

    object BundleKey {
        const val EXTRA_POLICE = "police"
        const val EXTRA_USUAL = "usual"
    }

}