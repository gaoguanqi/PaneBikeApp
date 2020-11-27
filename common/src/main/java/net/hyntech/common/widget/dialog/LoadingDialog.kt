package net.hyntech.common.widget.dialog

import android.content.Context
import android.os.Bundle
import net.hyntech.baselib.widget.dialog.BaseDailog
import net.hyntech.common.R

class LoadingDialog(context: Context) : BaseDailog(context,style = R.style.common_DialogCommon) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
    }

}