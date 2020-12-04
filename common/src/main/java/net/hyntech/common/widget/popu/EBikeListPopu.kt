package net.hyntech.common.widget.popu

import android.animation.Animator
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeCompat
import net.hyntech.common.R
import net.hyntech.common.base.BaseAdapter
import net.hyntech.common.base.BaseViewHolder
import net.hyntech.common.widget.decoration.SimpleItemDecoration
import razerdp.basepopup.BaseLazyPopupWindow
import razerdp.basepopup.BasePopupWindow

class EBikeListPopu<VH:BaseViewHolder,T : BaseAdapter<VH>>(
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
        contentView.findViewById<RecyclerView>(R.id.rv_ebike)?.apply {
            this.layoutManager = LinearLayoutManager(mContext)
            this.addItemDecoration(SimpleItemDecoration(mContext))
            this.adapter = mAdapter
        }
    }

}