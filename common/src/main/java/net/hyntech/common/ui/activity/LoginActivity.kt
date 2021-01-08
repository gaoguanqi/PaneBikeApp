package net.hyntech.common.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.PathUtils
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xupdate._XUpdate
import com.xuexiang.xupdate.service.OnFileDownloadListener
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.databinding.ActivityLoginBinding
import net.hyntech.common.db.AppDatabase
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.model.entity.AppUpdateEntity
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.vm.AccountViewModel
import net.hyntech.common.widget.update.UpdateDialog
import java.io.File

class LoginActivity : BaseViewActivity<ActivityLoginBinding, AccountViewModel>() {

    private val viewModel by viewModels<AccountViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_login

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun hasStatusBarMode(): Boolean = true


    override fun initData(savedInstanceState: Bundle?) {
        val buildType: String? = getBundleString(Constants.GlobalValue.BUILD_TYPE)
        BaseApp.instance.setBuildType(buildType)
        when (buildType) {
            Constants.BundleKey.EXTRA_USUAL -> {
                viewModel.bgDrawable.set(UIUtils.getDrawable(R.drawable.pic_usual))
            }

            Constants.BundleKey.EXTRA_POLICE -> {
                viewModel.bgDrawable.set(UIUtils.getDrawable(R.drawable.pic_police))
            }
        }

        viewModel.initUser()

        AppDatabase.getInstance(BaseApp.instance).userDao().apply {
            this.getCurrentUser()?.let {
                binding.tvOrgName.text = it.orgName
                viewModel.account.set(it.account)
                viewModel.password.set(it.password)
            } ?: let {
                binding.tvOrgName.text = UIUtils.getString(R.string.common_choose_company)
            }
        }

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.companyEvent.observe(this, Observer {
            startActivityForResult(
                Intent(this, OrgActivity::class.java),
                EventCode.EVENT_CODE_ORG
            )
        })

        viewModel.forgetPwdEvent.observe(this, Observer {
            startActivityForResult(
                Intent(this, ForgetPwdActivity::class.java),
                EventCode.EVENT_CODE_ORG
            )
        })

        viewModel.loginEvent.observe(this, Observer {
            when (buildType) {
                Constants.BundleKey.EXTRA_USUAL -> {
                    ARouter.getInstance().build(ARouterConstants.USUAL_HOME_PAGE).navigation()
                }
                Constants.BundleKey.EXTRA_POLICE -> {
                    ARouter.getInstance().build(ARouterConstants.POLICE_HOME_PAGE).navigation()
                }
            }
            onFinish()
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

        //版本跟新
        viewModel.appUpdate.observe(this, Observer {
            val versionCode: Int = BaseApp.instance.getVersionCode()
            LogUtils.logGGQ("--type->>${it.type}")
            LogUtils.logGGQ("--versionCode->>${versionCode}")
            showUpdateDialog(it)
        })

        viewModel.getOrgList()
    }

    private var updateDialog: UpdateDialog? = null

    private fun showUpdateDialog(bean: AppUpdateEntity) {
        if (updateDialog == null) {
            updateDialog = UpdateDialog(this, bean, object : UpdateDialog.OnClickListener {
                override fun onConfirmClick(url: String) {
                    onDownloadApk(url)
                }
            })
        }

        updateDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    private fun onDownloadApk(url: String) {
        XUpdate.newBuild(this)
            .apkCacheDir(PathUtils.getExternalDownloadsPath()) //设置下载缓存的根目录
            .build()
            .download(url,object :OnFileDownloadListener{
                override fun onStart() {}
                override fun onProgress(progress: Float, total: Long) {
                    updateDialog?.setProgress(progress)
                }
                override fun onError(throwable: Throwable?) {}
                override fun onCompleted(file: File?): Boolean {
                    updateDialog?.cancel()
                    file?.let {
                        installApk(it)
                        return true
                    }
                    return false
                }
            })
    }

    private fun installApk(file: File){
        //填写文件所在的路径
        _XUpdate.startInstallApk(this, file)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                EventCode.EVENT_CODE_ORG -> {
                    AppDatabase.getInstance(BaseApp.instance).userDao().apply {
                        this.getCurrentUser()?.let {
                            binding.tvOrgName.text = it.orgName
                        }
                    }
                }
            }
        }
    }

}