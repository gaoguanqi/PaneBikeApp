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
import kotlinx.coroutines.*
import net.hyntech.baselib.base.BaseViewModel
import net.hyntech.baselib.base.IView
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.widget.dialog.LoadingDialog

abstract class BaseFragment<VB : ViewDataBinding,VM :BaseViewModel> : Fragment(),
    CoroutineScope by MainScope(), IView {

    protected lateinit var binding: VB


    protected var navController: NavController? = null


    abstract fun getLayoutId(): Int


    abstract fun initData(savedInstanceState: Bundle?): Unit

    abstract fun bindViewModel()

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.initData(savedInstanceState)
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
        loadingDialog?.run { if (isShowing) {
            runBlocking {
                delay(500L)
            }
            dismiss()
        } }
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
        }else if(!this.isFirst){
            refReshData()
        }
    }

    open fun lazyLoadData() {}
    open fun refReshData(){}

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        this.binding.unbind()
    }

}