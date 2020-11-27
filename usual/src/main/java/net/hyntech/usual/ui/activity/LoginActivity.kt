package net.hyntech.usual.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.vm.AccountViewModel
import net.hyntech.usual.R
import net.hyntech.usual.databinding.ActivityLoginBinding

class LoginActivity: BaseViewActivity<ActivityLoginBinding, AccountViewModel>() {

    private val viewModel by viewModels<AccountViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_login

    override fun bindViewModel(){
        binding.viewModel = viewModel
    }


    override fun hasStatusBarMode(): Boolean = true


    override fun initData(savedInstanceState: Bundle?) {

        viewModel.companyEvent.observe(this, Observer {
            startActivity(Intent(LoginActivity@this,OrgActivity::class.java))
        })

        viewModel.forgetPwdEvent.observe(this, Observer {
            startActivity(Intent(LoginActivity@this,ForgetPwdActivity::class.java))
        })

        binding.etAccount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.account.set(s.toString())
            }
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.password.set(s.toString())
            }
        })


    }
}