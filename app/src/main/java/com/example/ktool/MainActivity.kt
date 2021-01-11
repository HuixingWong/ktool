package com.example.ktool

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ktool.databinding.ActivityMainBinding
import com.example.ktool.delegate.contentView
import com.example.ktool.test.testEntrance

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            bird = Bird("🐧")
        }
        testEntrance(this)
        findViewById<TextView>(R.id.tv).setOnClickListener {
            testEntrance(this)
        }
    }
}
data class Bird (var name: String)