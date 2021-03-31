package com.example.ktool

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import androidx.appcompat.app.AppCompatActivity

class BinderTestActivity: AppCompatActivity() {

    private lateinit var wakeLock: PowerManager.WakeLock

    companion object {
        private const val TIMEOUT_DURATION = 6000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag")
        wakeLock.acquire(TIMEOUT_DURATION)
    }

    override fun onDestroy() {
        super.onDestroy()
        wakeLock.release()
    }

}