package net.hyntech.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import net.hyntech.baselib.base.IView
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.widget.dialog.LoadingDialog

abstract class BaseFragment<VB : ViewDataBinding> : Fragment(),
    CoroutineScope by MainScope(), IView {

    protected lateinit var binding: VB


    protected var navController: NavController? = null


    abstract fun getLayoutId(): Int

    abstract fun bindViewModel()

    abstract fun initData(view: View, savedInstanceState: Bundle?): Unit


    private var isFirst: Boolean = true

    private var loadingDialog: LoadingDialog? = null

    open fun hasNavController(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.binding.lifecycleOwner = this
        this.bindViewModel()
        if (this.hasNavController()) {
            this.navController = Navigation.findNavController(view)
        }
        this.initData(view, savedInstanceState)
        this.onVisible()
    }

    open fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = context?.let {
                LoadingDialog(it)
            }
        }
        loadingDialog?.show()
    }

    open fun dismissLoading() {
        loadingDialog?.run { if (isShowing) dismiss() }
    }


    fun onClickProxy(m: () -> Unit) {
        if (!UIUtils.isFastDoubleClick()) {
            m()
        }
    }


    override fun onResume() {
        super.onResume()
        this.onVisible()
    }

    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && this.isFirst) {
            this.isFirst = false
            lazyLoadData()
        }
    }

    open fun lazyLoadData() {}


    override fun onDestroy() {
        super.onDestroy()
        cancel()
        this.binding.unbind()
    }

}