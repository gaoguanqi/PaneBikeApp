package net.hyntech.police.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.widget.view.ClearEditText
import net.hyntech.police.R
import net.hyntech.common.R as CR
import net.hyntech.police.databinding.ActivityPaymentConfirmBinding
import net.hyntech.police.vm.PaymentViewModel

class PaymentConfirmActivity:BaseViewActivity<ActivityPaymentConfirmBinding,PaymentViewModel>() {

    private var etPwd:ClearEditText? = null
    private var etRemark: EditText? = null

    private val viewModel by viewModels<PaymentViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_payment_confirm

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {
        setTitle<PaymentConfirmActivity>(UIUtils.getString(CR.string.common_title_payment_confirm)).onBack<PaymentConfirmActivity> {
            onFinish()
        }

        val id = getBundleString(Constants.BundleKey.EXTRA_ID)

        etPwd = findViewById(R.id.et_password)
        etRemark = findViewById(R.id.et_remark)

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.paymentEvent.observe(this, Observer {
            startActivity(Intent(this,PendingPaymentActivity::class.java))
            onFinish()
        })


        this.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            onClickProxy {
                val pwd = etPwd?.text.toString().trim()
                val remark = etRemark?.text.toString().trim()

                if(TextUtils.isEmpty(id)){
                    ToastUtil.showToast("校验失败！请重新登记")
                    return@onClickProxy
                }

                if(TextUtils.isEmpty(pwd)){
                    ToastUtil.showToast("请输入密码！")
                    return@onClickProxy
                }

                viewModel.paymentConfirm(id!!,pwd,remark)
            }
        }
    }
}