package net.hyntech.usual.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.activity_ebike_info.*
import net.hyntech.baselib.utils.Event
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.global.EventCode
import net.hyntech.common.model.entity.BannerEntity
import net.hyntech.common.model.entity.PhotoEntity
import net.hyntech.common.model.entity.UserInfoEntity
import net.hyntech.common.provider.ARouterConstants
import net.hyntech.common.ui.adapter.EBikeListAdapter
import net.hyntech.common.ui.adapter.MyBannerAdapter
import net.hyntech.common.ui.adapter.PhotoAdapter
import net.hyntech.common.widget.popu.EBikeListPopu
import net.hyntech.usual.R
import razerdp.basepopup.BasePopupWindow
import net.hyntech.common.R as CR

/**
 * 我的 -> 车辆信息
 */
class EbikeInfoActivity : BaseActivity() {

    private var tvTitle:TextView? = null
    private var ivArrowIcon: ImageView? = null

    private val ebikeAdapter by lazy { EBikeListAdapter(this) }

    private val ebikePopu by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { EBikeListPopu<EBikeListAdapter.ViewHolder, EBikeListAdapter>(this,ebikeAdapter).apply {
        this.popupGravity = Gravity.BOTTOM
        this.setOnPopupWindowShowListener { ivArrowIcon?.background = UIUtils.getDrawable(CR.drawable.ic_arrow_up) }
        this.onDismissListener = object : BasePopupWindow.OnDismissListener(){ override fun onDismiss() { ivArrowIcon?.background = UIUtils.getDrawable(CR.drawable.ic_arrow_down) } } }
    }

    private var banner:Banner<BannerEntity,MyBannerAdapter>? = null
    private val bannerAdapter:MyBannerAdapter by lazy { MyBannerAdapter(this,bannerList)}

    private var currentEbike:UserInfoEntity.EbikeListBean? = null
    private var ebikeList:List<UserInfoEntity.EbikeListBean>? = null

    private val bannerList:MutableList<BannerEntity> = mutableListOf()
    private val photoList:MutableList<PhotoEntity> = mutableListOf()

    private val photoAdapter by lazy { PhotoAdapter(this,CR.layout.item_photo).apply {
        this.setListener(object :PhotoAdapter.OnClickListener{
            override fun onItemClick(pos:Int,item: PhotoEntity?,list:MutableList<PhotoEntity>) {
                item?.let {
                    val bundle = Bundle()
                    val array = java.util.ArrayList<String>()
                    list.forEach {
                        array.add(it.url)
                    }
                    bundle.putSerializable(Constants.BundleKey.EXTRA_OBJ,array)
                    bundle.putInt(Constants.BundleKey.EXTRA_INDEX,pos)
                    ARouter.getInstance().build(ARouterConstants.PREVIEW_PAGE)
                        .with(bundle)
                        .navigation()
                }
            }
            override fun onItemDel(pos: Int, item: PhotoEntity?) {}
        })
    } }

    override fun getLayoutId(): Int = R.layout.activity_ebike_info

    override fun hasUsedEventBus(): Boolean = true

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle = findViewById(R.id.tv_title)
        ivArrowIcon = findViewById(R.id.iv_arrow_icon)
        banner = findViewById(R.id.banner)
        findViewById<LinearLayout>(R.id.ll_left)?.setOnClickListener {
            onFinish()
        }

        findViewById<LinearLayout>(R.id.ll_title)?.setOnClickListener {
            onClickTitle()
        }


        banner?.addBannerLifecycleObserver(this)?.setAdapter(bannerAdapter)?.setIndicator(
            CircleIndicator(this)
        )?.setOnBannerListener(object : OnBannerListener<BannerEntity> {
            override fun OnBannerClick(data: BannerEntity?, position: Int) {
                data?.let {
                    ToastUtil.showToast(it.name)
                }
            }
        })
        rv_photo?.layoutManager = GridLayoutManager(this,3)
        rv_photo?.adapter = photoAdapter

        ebikeAdapter.setListener(object :EBikeListAdapter.OnClickListener{
            override fun onItemClick(entity: UserInfoEntity.EbikeListBean?) {
                if(ebikeList != null && ebikeList!!.size > 1){
                    entity?.let {ebike ->
                        currentEbike?.let { curr ->
                            if(!TextUtils.equals(curr.ebikeNo,ebike.ebikeNo)){
                                ebikeList?.forEach {item ->
                                    item.isSelected = false
                                }
                                ebike.isSelected = true
                                currentEbike = ebike
                                setData(currentEbike)
                            }
                        }
                    }
                }
                ebikePopu.dismiss()
            }
        })
    }
    private fun onClickTitle() {
        showEBikePopu()
    }

    private fun showEBikePopu(){
        ebikeList?.let {list ->
            ebikeAdapter.setData(list)
            ebikePopu.showPopupWindow(tvTitle)
        }
    }

    override fun <T> onStickyEventBusDispense(event: Event<T>) {
        super.onStickyEventBusDispense(event)
        when(event.code){
            EventCode.EVENT_CODE_EBIKES ->{
                LogUtils.logGGQ("--event code:--${event.code}")
                ebikeList = event.data as List<UserInfoEntity.EbikeListBean>
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ebikeList?.let {
            currentEbike = ebikeList?.first()
            ebikeList?.forEach {item ->
                LogUtils.logGGQ("--item-->>${item.isSelected}")
                if(item.isSelected){
                    currentEbike = item
                }
            }
            setData(currentEbike)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(ebike:UserInfoEntity.EbikeListBean?) {
        bannerList.clear()
        photoList.clear()
        ebike?.let {info ->
            tvTitle?.text = info.ebikeNo
            if(!TextUtils.isEmpty(info.ebikePic1)){ bannerList.add(BannerEntity((info.ebikeNo),info.ebikePic1,0))
                photoList.add(PhotoEntity(info.ebikePic1)) }
            if(!TextUtils.isEmpty(info.ebikePic2)){ bannerList.add(BannerEntity((info.ebikeNo),info.ebikePic2,0))
                photoList.add(PhotoEntity(info.ebikePic2))}
            if(!TextUtils.isEmpty(info.ebikePic3)){ bannerList.add(BannerEntity((info.ebikeNo),info.ebikePic3,0))
                photoList.add(PhotoEntity(info.ebikePic3))
            }
            if(!TextUtils.isEmpty(info.ebikePic4)){ bannerList.add(BannerEntity((info.ebikeNo),info.ebikePic4,0))
                photoList.add(PhotoEntity(info.ebikePic4))
            }
            if(!TextUtils.isEmpty(info.ebikePic5)){ bannerList.add(BannerEntity((info.ebikeNo),info.ebikePic5,0))
                photoList.add(PhotoEntity(info.ebikePic5))
            }
            if(!TextUtils.isEmpty(info.ebikePic6)){ bannerList.add(BannerEntity((info.ebikeNo),info.ebikePic6,0))
                photoList.add(PhotoEntity(info.ebikePic6))
            }
            tv_label?.text = info.locatorNo
            tv_frame?.text = info.frameNo
            tv_brand?.text = info.ebikeType
            tv_motor?.text = info.engineNo
            tv_color?.text = info.ebikeColor
            tv_type?.text = info.typeName
            tv_buy_price?.text = (info.price / 100.0).toString()
            tv_product?.text = "${info.insuranceProductName} ${info.termRange}年版"
            var limit:String = "赔付限额${(info.insuranceCoverage / 100.0)}元"
            if(info.insuranceCoverage <= 0){
                val state = info.insuranceState
                if(TextUtils.equals("compensate",state)){ //车辆已丢失已赔付
                    limit = "车辆丢失,已赔付"
                }else if(TextUtils.equals("not_work",state)){
                    limit = "服务暂未生效，请耐心等待"
                }else if(TextUtils.equals("working",state)){
                    limit = ""
                }else{
                    limit = "还没购买该服务"
                }
            }
            tv_limit?.text = limit
            var buyTime:String? = info.buyTime
            buyTime = buyTime?.split(" ")?.first()
            if(!TextUtils.isEmpty(buyTime)){
                tv_buy_date?.text = buyTime
            }
            tv_totaltime?.text = "${info.beginTimeInsurance} 至 ${info.endTimeInsurance}"
        }

        if(bannerList.isNotEmpty()){
            bannerAdapter.setDatas(bannerList)
            bannerAdapter.notifyDataSetChanged()
        }

        if(photoList.isNotEmpty()){
            photoAdapter.setData(photoList)
        }
    }
}