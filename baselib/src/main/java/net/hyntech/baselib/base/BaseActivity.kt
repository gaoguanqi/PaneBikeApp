package net.hyntech.baselib.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.BarUtils
import net.hyntech.baselib.R
import net.hyntech.baselib.utils.Event
import net.hyntech.baselib.utils.EventBusUtils
import net.hyntech.baselib.utils.UIUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@Suppress("UNCHECKED_CAST")
abstract class BaseActivity : AppCompatActivity(), IView {

    abstract fun getLayoutId(): Int
    abstract fun initData(savedInstanceState: Bundle?): Unit

    open fun hasUsedEventBus(): Boolean = false
    open fun hasStatusBarMode(): Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        if(hasStatusBarMode()){
            setStatusBarMode()
        }
        super.onCreate(savedInstanceState)
        setContentLayout();
        if (hasUsedEventBus()) {
            EventBusUtils.register(this)
        }
        initData(savedInstanceState)
    }

    open fun setStatusBarMode(color:Int = Color.TRANSPARENT){
        BarUtils.setStatusBarColor(this, color)

    }

    open fun setContentLayout() {
        setContentView(getLayoutId())
    }

    open fun getBundleString(key:String):String?{
        return intent?.getStringExtra(key)
    }

    /**
     * 接收到普通的Event
     * 封装MAIN线程模式，子类可重写 onEvnetBusReceive,
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun <T> onEventBusReceive(event: Event<T>?) {
        if (event != null) {
            onEventBusDispense(event)
        }
    }

    /**
     * 接收到粘性的Event
     * 封装MAIN线程模式，子类可重写 onStickyEvnetBusReceive,
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    open fun <T> onStickyEventBusReceive(event: Event<T>?) {
        if (event != null) {
            onStickyEventBusDispense(event)
        }
    }


    /**
     * 子类重写onEventBusDispense，处理接收到的普通事件
     */
    open fun <T> onEventBusDispense(event: Event<T>) {}

    /**
     * 子类重写onStickyEventBusDispense，处理接收到的粘性事件
     */
    open fun <T> onStickyEventBusDispense(event: Event<T>) {}


    open fun onFinish() {
        this.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (hasUsedEventBus()) {
            EventBusUtils.unregister(this)
        }
    }

}