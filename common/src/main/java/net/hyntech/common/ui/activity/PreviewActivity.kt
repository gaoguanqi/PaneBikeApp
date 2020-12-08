package net.hyntech.common.ui.activity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.ui.adapter.PreviewAdapter

@Route(path = ARouterConstants.PREVIEW_PAGE)
class PreviewActivity:BaseActivity() {
    private var vp: ViewPager2? = null

    override fun getLayoutId(): Int = R.layout.activity_preview

    override fun initData(savedInstanceState: Bundle?) {

        setTitle<PreviewActivity>(UIUtils.getString(R.string.common_title_preview)).onBack<PreviewActivity> { onFinish() }
        vp = findViewById(R.id.vp)

        this.intent?.extras?.let {
            val list = it.getSerializable(Constants.BundleKey.EXTRA_OBJ) as List<String>
            val index:Int = it.getInt(Constants.BundleKey.EXTRA_INDEX,0)
            LogUtils.logGGQ("list--${list.size}")
            if( list.isNotEmpty()){
                vp?.adapter = PreviewAdapter(this@PreviewActivity,list)
                vp?.currentItem = index
            }
        }
    }
}