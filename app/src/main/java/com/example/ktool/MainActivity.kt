package com.example.ktool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ktool.databinding.ActivityMainBinding
import com.example.ktool.delegate.contentView

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            bird = Bird("🐧")
        }
    }
}
data class Bird (var name: String)