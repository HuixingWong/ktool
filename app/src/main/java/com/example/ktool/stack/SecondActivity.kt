package com.example.ktool.stack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ktool.R
val TAG = "😂😂"
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.e(TAG, "onCreate: ", )
        title = "second"
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG, "onRestart: " )
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause: ", )
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e(TAG, "onNewIntent: ", )
    }

    override fun onResume() {
        Log.e(TAG, "onResume: ", )
        super.onResume()
    }
    
    
}