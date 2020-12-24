package net.hyntech.common.widget.popu

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.R
import net.hyntech.common.model.entity.EbikeTrackEntity
import net.hyntech.common.model.entity.PhotoEntity
import net.hyntech.common.model.entity.SeverInfoEntity
import net.hyntech.common.ui.adapter.PhotoAdapter
import net.hyntech.common.ui.adapter.SeverListAdapter
import net.hyntech.common.widget.imgloader.ImageLoader
import net.hyntech.common.widget.imgloader.TransType
import net.hyntech.common.widget.imgloader.glide.GlideImageConfig
import razerdp.basepopup.BaseLazyPopupWindow
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.ScaleConfig

class EbikeInfoPopu(
    val mContext: Context,
    val mWidth: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    val mHeight: Int = WindowManager.LayoutParams.WRAP_CONTENT
) : BaseLazyPopupWindow(mContext) {

    private var ebike: EbikeTrackEntity.EbikeBean? = null

    private var ebikeImgAdapter:PhotoAdapter? = null

    private val photoList:MutableList<PhotoEntity> = mutableListOf()


    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popu_ebike_info)
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
        this.ebike?.let {
            contentView.apply {
                this.findViewById<TextView>(R.id.tv_label_no)?.text = "${it.locatorNo}"
                this.findViewById<TextView>(R.id.tv_name)?.text = "${it.userName}"
                this.findViewById<TextView>(R.id.tv_phone)?.text = "${it.phone}"
                this.findViewById<TextView>(R.id.tv_frame_no)?.text = "${it.frameNo}"
                this.findViewById<TextView>(R.id.tv_brand)?.text = "${it.ebikeType}"
                this.findViewById<TextView>(R.id.tv_motor)?.text = "${it.engineNo}"
                this.findViewById<TextView>(R.id.tv_color)?.text = "${it.ebikeColor}"
                this.findViewById<TextView>(R.id.tv_type)?.text = "${it.typeName}"

                this.findViewById<ImageView>(R.id.iv_locator_pic)?.let { iv->
                    iv.setOnClickListener { v->
                        if(!UIUtils.isFastDoubleClick()){
                            listener?.onLabelImgClick(it.locatorPic)
                        }
                    }
                    ImageLoader.getInstance().loadImage(
                        BaseApp.instance,
                        GlideImageConfig(it.locatorPic, iv).also { config-> config.type = TransType.NORMAL })
                }
                photoList.clear()
                if(!TextUtils.isEmpty(it.ebikePic1)){
                    photoList.add(PhotoEntity(it.ebikePic1))
                }
                if(!TextUtils.isEmpty(it.ebikePic2)){
                    photoList.add(PhotoEntity(it.ebikePic2))
                }
                if(!TextUtils.isEmpty(it.ebikePic3)){
                    photoList.add(PhotoEntity(it.ebikePic3))
                }
                if(!TextUtils.isEmpty(it.ebikePic4)){
                    photoList.add(PhotoEntity(it.ebikePic4))
                }
                if(!TextUtils.isEmpty(it.ebikePic5)){
                    photoList.add(PhotoEntity(it.ebikePic5))
                }
                if(!TextUtils.isEmpty(it.ebikePic6)){
                    photoList.add(PhotoEntity(it.ebikePic6))
                }
                this.findViewById<RecyclerView>(R.id.rv_ebike_img).apply {
                    this.layoutManager = GridLayoutManager(mContext,2)
                    this.adapter = ebikeImgAdapter
                    ebikeImgAdapter?.setData(photoList)
                }
            }
        }
    }


    override fun onCreateShowAnimation(): Animation {
//        return super.onCreateShowAnimation()
        return AnimationHelper.asAnimation().withScale(ScaleConfig.TOP_TO_BOTTOM.duration(200L))
            .toShow()
    }

    override fun onCreateDismissAnimation(): Animation {
//        return super.onCreateDismissAnimation()
        return AnimationHelper.asAnimation().withScale(ScaleConfig.BOTTOM_TO_TOP.duration(100L))
            .toDismiss()
    }

    fun setInfo(ebike: EbikeTrackEntity.EbikeBean) {
        this.ebike = ebike
    }

    fun setEbikeImgAdapter(adapter: PhotoAdapter) {
        this.ebikeImgAdapter = adapter
    }
    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    interface OnClickListener{
        fun onLabelImgClick(url:String)
    }
}