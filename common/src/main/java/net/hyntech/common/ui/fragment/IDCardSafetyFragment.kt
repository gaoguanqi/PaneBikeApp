package net.hyntech.common.ui.fragment

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseFragment
import net.hyntech.common.databinding.FragmentIdcardSafetyBinding
import net.hyntech.common.ui.activity.AccountSafetyActivity
import net.hyntech.common.vm.AccountSafetyViewModel

class IDCardSafetyFragment:BaseFragment<FragmentIdcardSafetyBinding, AccountSafetyViewModel>() {

    private var tvTitle: TextView? = null
    private var llLeft: LinearLayout? = null
    private var tvPhone: TextView? = null
    private var viewModel:AccountSafetyViewModel? = null

    override fun getLayoutId(): Int = R.layout.fragment_idcard_safety

    override fun hasNavController(): Boolean = true

    override fun initData(savedInstanceState: Bundle?) {
        view?.apply {
            tvTitle = this.findViewById(R.id.tv_title)
            llLeft = this.findViewById(R.id.ll_left)
            tvPhone = this.findViewById(R.id.tv_phone)
            tvTitle?.text = UIUtils.getString(R.string.common_title_idcard_safety)
            llLeft?.setOnClickListener {
                viewModel?.onClickBack()
            }
        }

        viewModel?.userPhone?.get()?.let {
            tvPhone?.text = it
        }
    }

    override fun bindViewModel() {
        viewModel = (this.activity as AccountSafetyActivity).viewModel
        binding.viewModel = viewModel
    }

}