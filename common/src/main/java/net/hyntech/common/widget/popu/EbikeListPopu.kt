package net.hyntech.common.widget.popu

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.widget.decoration.SimpleItemDecoration
import razerdp.basepopup.BaseLazyPopupWindow
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.ScaleConfig

class EbikeListPopu<VH:BaseViewHolder,T : BaseAdapter<VH>>(
    val mContext: Context,
    val mAdapter: T,
    val mWidth: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    val mHeight: Int = WindowManager.LayoutParams.WRAP_CONTENT
) : BaseLazyPopupWindow(mContext) {


    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popu_ebike_list)
    }

    override fun setWidth(width: Int): BasePopupWindow {
        return super.setWidth(mWidth)
    }

    override fun setHeight(height: Int): BasePopupWindow {
        return super.setHeight(mHeight)
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        this.setPopupGravity(Gravity.BOTTOM or Gravity.CENTER)
        contentView.findViewById<RecyclerView>(R.id.rv_ebike)?.apply {
            this.layoutManager = LinearLayoutManager(mContext)
            this.addItemDecoration(SimpleItemDecoration(mContext))
            this.adapter = mAdapter
        }
    }

    override fun onCreateShowAnimation(): Animation {
//        return super.onCreateShowAnimation()
        return AnimationHelper.asAnimation().withScale(ScaleConfig.TOP_TO_BOTTOM.duration(200L)).toShow()
    }

    override fun onCreateDismissAnimation(): Animation {
//        return super.onCreateDismissAnimation()
        return AnimationHelper.asAnimation().withScale(ScaleConfig.BOTTOM_TO_TOP.duration(100L)).toDismiss()
    }



}