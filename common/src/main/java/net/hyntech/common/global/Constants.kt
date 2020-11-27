package net.hyntech.ebike.app.global

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
        const val HAS_WECLOME_USUAL = "weclome_usual"
        const val HAS_WECLOME_POLICE = "weclome_police"
    }


    object GlobalValue {

    }

    object BundleKey {

    }

}