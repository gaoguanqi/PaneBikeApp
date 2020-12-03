package net.hyntech.common.widget.timer

import android.os.CountDownTimer
import com.maple.player.widget.timer.MyCountDownTimerListener

class MyCountDownTimer(
    millisInFuture: Long,
    val countDownInterval: Long,
    val listener: MyCountDownTimerListener
) : CountDownTimer(millisInFuture, countDownInterval) {


    override fun onTick(millisUntilFinished: Long) {
        listener.onTick(millisUntilFinished/countDownInterval)
    }


    override fun onFinish() {
        listener.onFinish()
    }
}