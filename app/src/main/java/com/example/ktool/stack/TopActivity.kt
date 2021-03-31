package com.example.ktool.stack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ktool.R

class TopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        findViewById<Button>(R.id.launch).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
            startActivity(Intent(this, ThirdActivity::class.java))
        }
    }
}