package net.hyntech.usual.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout

import android.widget.TextView
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils

import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.vo.BundleEbikeVo
import net.hyntech.common.ui.adapter.EbikeNoAdapter
import net.hyntech.common.widget.popu.EBikeListPopu
import net.hyntech.usual.R
import net.hyntech.common.R as CR
import net.hyntech.usual.databinding.ActivityEbikeTrackBinding
import net.hyntech.usual.vm.TrackViewModel
import razerdp.basepopup.BasePopupWindow

class EbikeTrackActivity:BaseViewActivity<ActivityEbikeTrackBinding,TrackViewModel>() {

    private var tvTitle:TextView? = null

    private lateinit var ebikeList:List<BundleEbikeVo>

    private var ivArrowIcon: ImageView? = null

    private val ebikeAdapter by lazy { EbikeNoAdapter(this)}

    private val ebikePopu by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { EBikeListPopu<EbikeNoAdapter.ViewHolder, EbikeNoAdapter>(this,ebikeAdapter).apply {
        this.popupGravity = Gravity.BOTTOM
        this.setOnPopupWindowShowListener { ivArrowIcon?.background = UIUtils.getDrawable(CR.drawable.ic_arrow_up) }
        this.onDismissListener = object : BasePopupWindow.OnDismissListener(){ override fun onDismiss() { ivArrowIcon?.background = UIUtils.getDrawable(
            CR.drawable.ic_arrow_down) } } }
    }

    private val viewModel by viewModels<TrackViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_ebike_track

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle = findViewById(R.id.tv_title)
        ivArrowIcon = findViewById(R.id.iv_arrow_icon)
        findViewById<LinearLayout>(R.id.ll_left)?.setOnClickListener {
            onFinish()
        }

        findViewById<LinearLayout>(R.id.ll_title)?.setOnClickListener {
            onClickTitle()
        }
        val bundle = intent.extras
        bundle?.let {
            ebikeList = it.getSerializable(Constants.BundleKey.EXTRA_OBJ) as List<BundleEbikeVo>
            var vo = ebikeList.first()
            if(ebikeList.size > 1){
                ebikeList.forEach {item ->
                    if(item.isSelected){
                        vo = item
                    }
                }
            }
            setData(vo)
        }

        ebikeAdapter.setListener(object :EbikeNoAdapter.OnClickListener{
            override fun onItemClick(item: BundleEbikeVo?) {
                if(ebikeList.size > 1){
                    item?.let { ebike ->
                        currentEbike?.let {curr ->
                            if(!TextUtils.equals(curr.ebikeNo,ebike.ebikeNo)){
                                ebikeList.forEach {item ->
                                    item.isSelected = false
                                }
                                ebike.isSelected = true
                                setData(ebike)
                            }
                        }
                    }
                }
                ebikePopu.dismiss()
            }
        })
    }

    private var currentEbike:BundleEbikeVo? = null
    private fun setData(vo: BundleEbikeVo) {
        this.currentEbike = vo
        tvTitle?.text = vo.ebikeNo
        viewModel.locationSearch(vo.ebikeNo)
    }

    private fun onClickTitle() {
        showEBikePopu()
    }

    private fun showEBikePopu(){
        ebikeList.let {list ->
            ebikeAdapter.setData(list)
            ebikePopu.showPopupWindow(tvTitle)
        }
    }
}