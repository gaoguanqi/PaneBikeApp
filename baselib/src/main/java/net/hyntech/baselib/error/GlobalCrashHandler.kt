package net.hyntech.baselib.error
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import net.hyntech.baselib.app.config.Config
import net.hyntech.baselib.utils.LogUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
/**
 * 全局捕获异常
 */
class GlobalCrashHandler:Thread.UncaughtExceptionHandler {
    val VERSION_NAME = "versionName"
    val VERSION_CODE = "versionCode"
    val PACKAGE_NAME = "packageName"
    val STACK_TRACE = "STACK_TRACE"

    /**
     * 异常信息
     */
    private var exception = ""
    /**
     * 设备信息
     */
    private var deviceInfo = ""


    /**
     * 存放设备信息和异常信息
     */
    private var infoMap = mutableMapOf<String, String>()

    /**
     * 格式化日期(作为日志文件名)
     */
    private var formatter = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss", Locale.CHINA)

    /**
     * 私有构造函数
     */
    private constructor()

    companion object {
        /**
         * Debug log tag
         */
        private val TAG = "CrashHandler"
        /**
         * 反馈邮箱
         */
        private var feedbackEmail = ""

        /**
         * 日志开关，Debug状态下开启
         */
        private val isDebug = Config.CONFIG_DEBUG

        /**
         * 系统默认的UncaughtException处理类
         */
        private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

        /**
         * 上下文
         */
        private var context: Context? = null

        /**
         * CrashHandler实例
         */
        var instance: GlobalCrashHandler? = null

        /**
         * 使用单例模式获取实例
         */
        fun getCrashHandlerInstance(): GlobalCrashHandler {
            //防止多线程访问，加双锁
            if (instance == null) {
                synchronized(GlobalCrashHandler::class.java) {
                    if (instance == null) {
                        instance = GlobalCrashHandler()
                    }
                }
            }
            return instance!!
        }

        /**
         * 在application中最先进行初始化
         * 初始化一些成员变量
         */
        fun init(context: Context, email: String) {
            this.context = context
            mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler(this.getCrashHandlerInstance())
            feedbackEmail = email
        }

    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    override fun uncaughtException(t: Thread?, throwable: Throwable?) {
        LogUtils.logGGQ("<<<<<<--全局异常捕获-->>>>>")
        Toast.makeText(context,"数据异常,请稍后重试！",Toast.LENGTH_SHORT).show()

//        if (!handleException(throwable) && mDefaultHandler != null) {
//            // 如果用户没有处理(收集设备信息、保存日志文件等)则让系统默认的异常处理器来处
//            mDefaultHandler!!.uncaughtException(t, throwable)
//        } else {
//            // 跳转到崩溃提示Activity
//            var intent = Intent(context, CrashDialog::class.java)
//            intent.putExtra("Exception", exception)
//            intent.putExtra("DeviceInfo", deviceInfo)
//            intent.putExtra("Email", feedbackEmail)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//设置启动模式
//            context?.startActivity(intent)
////            System.exit(0)// 关闭已奔溃的app进程
//            android.os.Process.killProcess(android.os.Process.myPid())
//
//            LogUtils.logGGQ("异常---》》》》")
//            Toast.makeText(context,"数据异常,请稍后重试！",Toast.LENGTH_SHORT).show()
//        }
    }

    private fun handleException(throwable: Throwable?): Boolean {
        if (throwable == null) {//没有异常，不做处理
            return false
        }
        //收集设备信息
        collectDeviceInfo(context!!)
        //保存日志文件
        saveCrashInfo2File(throwable)
        //弹出异常对话框

        return true
    }

    /**
     * 保存错误信息到文件中
     */
    private fun saveCrashInfo2File(throwable: Throwable) {
        val sb = StringBuffer()
        val exceptionSb = StringBuffer()
        val time = formatter.format(Date())
        //所有信息拼接成字符串
        sb.append("time =" + time + "\n")
        infoMap.forEach {
            sb.append(it.key + "=" + it.value + "\n")
        }

        /**
         * crashInfo 就是我们收集到的所有信息，可以做一个异常上报的接口用来提交用户的crash信息
         */
        deviceInfo = sb.toString()

        //创建文件
        val fileName = "crash_" + time + "_" + System.currentTimeMillis() + ".txt"
        val file = File(context?.filesDir, fileName)
        if (!file.exists()) {//不存在，创建文件
            file.createNewFile()
        }
        file.appendText(deviceInfo)
        file.appendText(STACK_TRACE + ":\n")
        file.appendText("EXCEPTION:" + throwable + "\n")
        exceptionSb.append("EXCEPTION:" + throwable + "\n")
        throwable.stackTrace.forEach {
            Log.e(TAG, "crashInfo:" + it)
            file.appendText("\t" + it?.toString() + "\n")
            exceptionSb.append("\t" + it?.toString() + "\n")
        }

        exception = exceptionSb.toString()
    }

    /**
     * 收集设备参数信息
     */
    private fun collectDeviceInfo(context: Context) {
        try {
            val packageInfo = context.packageManager?.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
            //非空操作
            packageInfo?.let {
                val versionName = if (it.versionName != null) {
                    it.versionName
                } else {
                    "null"
                }
                val versionCode = it.versionName + ""
                val packageName = it.packageName + ""
                infoMap.put(PACKAGE_NAME, packageName)
                infoMap.put(VERSION_NAME, versionName)
                infoMap.put(VERSION_CODE, versionCode)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "an error occured when collect package info \n" + e)
        }

        //反射获取设备信息(所有的字段信息)
        val fields = Build::class.java.declaredFields
        fields.forEach {
            try {
                it?.isAccessible = true
                infoMap.put(it?.name!!, it.get(null).toString())
            } catch (e: Exception) {
                Log.e(TAG, "an error occured when collect package info \n" + e)
            }
        }
    }


}