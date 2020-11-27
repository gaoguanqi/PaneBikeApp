package net.hyntech.police.app

import net.hyntech.common.app.CommonApp

class PoliceApplication : CommonApp() {

    override fun getAppPackage(): String = this.packageName

    override fun onCreate() {
        super.onCreate()

    }
}