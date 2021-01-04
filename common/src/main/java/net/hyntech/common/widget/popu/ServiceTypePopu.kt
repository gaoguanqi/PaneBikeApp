package net.hyntech.common.widget.popu

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.model.entity.ServiceTypeEntity
import net.hyntech.common.ui.adapter.ShopLoaderAdapter
import net.hyntech.common.ui.adapter.ShopTypeAdapter
import razerdp.basepopup.BaseLazyPopupWindow
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.ScaleConfig

class ServiceTypePopu(
    val mContext: Context,
    val mLoaderAdapter:ShopLoaderAdapter,
    val mTypeAdapter:ShopTypeAdapter,
    val mWidth: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    val mHeight: Int = WindowManager.LayoutParams.WRAP_CONTENT
) : BaseLazyPopupWindow(mContext) {
    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popu_service_type)
    }

    override fun setWidth(width: Int): BasePopupWindow {
        return super.setWidth(mWidth)
    }

    override fun setHeight(height: Int): BasePopupWindow {
        return super.setHeight(mHeight)
    }

    override fun onCreateShowAnimation(): Animation {
//        return super.onCreateShowAnimation()
        return AnimationHelper.asAnimation().withScale(ScaleConfig.TOP_TO_BOTTOM.duration(200L)).toShow()
    }

    override fun onCreateDismissAnimation(): Animation {
//        return super.onCreateDismissAnimation()
        return AnimationHelper.asAnimation().withScale(ScaleConfig.BOTTOM_TO_TOP.duration(100L)).toDismiss()
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        this.setPopupGravity(Gravity.BOTTOM or Gravity.START)
        contentView.findViewById<RecyclerView>(R.id.rv_loader)?.apply {
            val loaderManager = GridLayoutManager(mContext, 3)
            this.layoutManager = loaderManager
            this.adapter = mLoaderAdapter
        }

        contentView.findViewById<RecyclerView>(R.id.rv_type)?.apply {
            val typeManager = GridLayoutManager(mContext, 3)
            this.layoutManager = typeManager
            this.adapter = mTypeAdapter
        }


        contentView.findViewById<TextView>(R.id.tv_reset)?.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){
                //重置
                mLoaderAdapter.resetList()
                mTypeAdapter.resetList()
            }
        }

        contentView.findViewById<TextView>(R.id.tv_finish)?.setOnClickListener {
            if(!UIUtils.isFastDoubleClick()){

            }
        }
    }


    interface OnClickListener{
        fun onResetClick(item: ServiceTypeEntity)
        fun onFinishClick(item: ServiceTypeEntity)
    }

}