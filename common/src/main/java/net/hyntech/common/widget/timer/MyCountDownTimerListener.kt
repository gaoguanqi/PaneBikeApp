package com.maple.player.widget.timer

interface MyCountDownTimerListener {
    fun onStart()
    fun onTick(millisUntilFinished: Long)
    fun onFinish()
}