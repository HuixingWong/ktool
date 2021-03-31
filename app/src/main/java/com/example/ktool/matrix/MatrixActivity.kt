package com.example.ktool.matrix

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ktool.databinding.ActivityMatrixBinding
import com.example.ktool.matrix.flip.FlipShareView
import com.example.ktool.matrix.flip.ShareItem

class MatrixActivity : AppCompatActivity() {
    lateinit var binding: ActivityMatrixBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatrixBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.matrix.setOnClickListener {
            FlipShareView.Builder(this, binding.matrix)
                .addItem(ShareItem("Facebook"))
                .addItem(ShareItem("Twitter"))
                .addItem(ShareItem("Google+"))
                .addItem(ShareItem("http://www.wangyuwei.me", Color.WHITE, -0xa88f76))
                .setSeparateLineColor(0x30000000)
                .setBackgroundColor(0x60000000)
                .setAnimType(FlipShareView.TYPE_SLIDE)
                .create()
        }
    }
}