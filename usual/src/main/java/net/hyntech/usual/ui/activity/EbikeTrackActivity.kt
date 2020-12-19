package net.hyntech.usual.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.TimeUtils
import net.hyntech.baselib.utils.ToastUtil
import net.hyntech.baselib.utils.UIUtils
import net.hyntech.common.base.BaseViewActivity
import net.hyntech.common.global.Constants
import net.hyntech.common.model.vo.BundleEbikeVo
import net.hyntech.common.ui.adapter.EbikeNoAdapter
import net.hyntech.common.widget.dialog.CommonDialog
import net.hyntech.common.widget.popu.EBikeListPopu
import net.hyntech.usual.R
import net.hyntech.usual.databinding.ActivityEbikeTrackBinding
import net.hyntech.usual.vm.TrackViewModel
import razerdp.basepopup.BasePopupWindow
import java.util.*
import net.hyntech.common.R as CR

class EbikeTrackActivity:BaseViewActivity<ActivityEbikeTrackBinding,TrackViewModel>() {

    private var tvTitle:TextView? = null
    private var ivArrowIcon: ImageView? = null
    private var tvStartTime:TextView? = null
    private var tvEndTime:TextView? = null

    private val buyDialog: CommonDialog by lazy { CommonDialog(this,UIUtils.getString(CR.string.common_warm),
        UIUtils.getString(CR.string.common_content_nobuy_service),
        UIUtils.getString(CR.string.common_close),
        UIUtils.getString(CR.string.common_buy_now),object :
            CommonDialog.OnClickListener{
            override fun onCancleClick() {
                //关闭
                onFinish()
            }
            override fun onConfirmClick() {
                //立即购买
                startActivity(Intent(this@EbikeTrackActivity,AddValServiceActivity::class.java).putExtra(Constants.BundleKey.EXTRA_ID,ebikeId))
            } }) }

    private lateinit var ebikeId:String
    private lateinit var ebikeList:List<BundleEbikeVo>

    private val ebikeAdapter by lazy { EbikeNoAdapter(this)}

    private val ebikePopu by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { EBikeListPopu<EbikeNoAdapter.ViewHolder, EbikeNoAdapter>(this,ebikeAdapter).apply {
        this.popupGravity = Gravity.BOTTOM
        this.setOnPopupWindowShowListener { ivArrowIcon?.background = UIUtils.getDrawable(CR.drawable.ic_arrow_up) }
        this.onDismissListener = object : BasePopupWindow.OnDismissListener(){ override fun onDismiss() { ivArrowIcon?.background = UIUtils.getDrawable(
            CR.drawable.ic_arrow_down) } } }
    }


    private val startTimePickerView: TimePickerView by lazy {
        TimePickerBuilder(this, object :OnTimeSelectListener{
            override fun onTimeSelect(date: Date?, v: View?) {
                tvStartTime?.text = TimeUtils.date2String(date)
            }
        }).setType(booleanArrayOf(true, true, true, true, true, true))
            .setTitleText("开始时间")
            .setCancelColor(UIUtils.getColor(CR.color.common_color_gray))
            .setSubmitColor(UIUtils.getColor(CR.color.common_colorTheme))
            .setTitleBgColor(UIUtils.getColor(CR.color.common_white))
            .setTitleColor(UIUtils.getColor(CR.color.common_black))
            .setTextColorOut(UIUtils.getColor(CR.color.common_color_gray))
            .setBgColor(UIUtils.getColor(CR.color.common_default_background))
            .build()
    }

    private val endTimePickerView: TimePickerView by lazy {
        TimePickerBuilder(this, object :OnTimeSelectListener{
            override fun onTimeSelect(date: Date?, v: View?) {
                tvEndTime?.text = TimeUtils.date2String(date)
            }
        }).setType(booleanArrayOf(true, true, true, true, true, true))
            .setTitleText("结束时间")
            .setCancelColor(UIUtils.getColor(CR.color.common_color_gray))
            .setSubmitColor(UIUtils.getColor(CR.color.common_colorTheme))
            .setTitleBgColor(UIUtils.getColor(CR.color.common_white))
            .setTitleColor(UIUtils.getColor(CR.color.common_black))
            .setTextColorOut(UIUtils.getColor(CR.color.common_color_gray))
            .setBgColor(UIUtils.getColor(CR.color.common_default_background))
            .build()
    }

    private val viewModel by viewModels<TrackViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_ebike_track

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    private var startTime:String = ""
    private var endTime:String = ""
    override fun initData(savedInstanceState: Bundle?) {
        tvTitle = findViewById(R.id.tv_title)
        ivArrowIcon = findViewById(R.id.iv_arrow_icon)
        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtil.showToast(it)
        })

        viewModel.notBuyServiceEvent.observe(this, Observer {
            showBuyDialog()
        })

        findViewById<LinearLayout>(R.id.ll_left)?.setOnClickListener {
            onFinish()
        }

        findViewById<LinearLayout>(R.id.ll_title)?.setOnClickListener {
            onClickTitle()
        }
        tvStartTime?.setOnClickListener {
            if(!startTimePickerView.isShowing){
                startTimePickerView.show()
            }
        }
        tvEndTime?.setOnClickListener {
            if(!endTimePickerView.isShowing){
                endTimePickerView.show()
            }
        }



        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH,-1)
        startTime = TimeUtils.date2String(calendar.time)
        tvStartTime?.text = startTime
        endTime = TimeUtils.getNowString()
        tvEndTime?.text = endTime

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
        this.ebikeId = vo.ebikeId
        this.currentEbike = vo
        tvTitle?.text = vo.ebikeNo

        viewModel.locationSearch(vo.ebikeNo,startTime,endTime)
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

    private fun showBuyDialog(){
        if(!buyDialog.isShowing){
            buyDialog.show()
        }
    }
}