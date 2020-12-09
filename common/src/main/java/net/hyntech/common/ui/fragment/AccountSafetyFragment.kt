package net.hyntech.common.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.databinding.FragmentAccountSafetyBinding
import net.hyntech.common.global.Constants
import net.hyntech.common.ui.activity.AccountSafetyActivity
import net.hyntech.common.vm.AccountSafetyViewModel

class AccountSafetyFragment:BaseFragment<FragmentAccountSafetyBinding,AccountSafetyViewModel>() {

    private var tvTitle:TextView? = null
    private var llLeft:LinearLayout? = null
    private var viewModel:AccountSafetyViewModel? = null

    override fun getLayoutId(): Int = R.layout.fragment_account_safety

    override fun hasNavController(): Boolean = true

    override fun initData(savedInstanceState: Bundle?) {
        view?.apply {
            tvTitle = this.findViewById(R.id.tv_title)
            llLeft = this.findViewById(R.id.ll_left)
            tvTitle?.text = UIUtils.getString(R.string.common_title_account_safety)
            llLeft?.setOnClickListener {
                viewModel?.onClickBack()
            }
        }

        viewModel?.let {vm ->
            vm.phoneEvent.observe(this, Observer {
                navController?.navigate(R.id.common_action_accountsafetyfragment_to_phonesafetyfragment)
            })

            vm.idCardEvent.observe(this, Observer {
                navController?.navigate(R.id.common_action_accountsafetyfragment_to_idcardsafetyfragment)
            })
        }

        val phone = this.arguments?.getString(Constants.BundleKey.EXTRA_PHONE)
        val idCard = this.arguments?.getString(Constants.BundleKey.EXTRA_IDCARD)
        LogUtils.logGGQ("phone->${phone}")
        LogUtils.logGGQ("idCard->${idCard}")
        if(!TextUtils.isEmpty(phone)){
            viewModel?.userPhone?.set(phone)
        }
        if(!TextUtils.isEmpty(idCard)){
            viewModel?.userIDCard?.set(idCard)
        }
    }

    override fun bindViewModel() {
        viewModel = (this.activity as AccountSafetyActivity).viewModel
        binding.viewModel = viewModel
    }

}