package com.example.ktool.matrix

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.ktool.databinding.ActivityMatrixBinding
import com.example.ktool.matrix.flip.FlipShareView
import com.example.ktool.matrix.flip.ShareItem
import kotlin.math.roundToInt

/**
 * https://www.gcssloop.com/customview/matrix-3d-camera
 */

class MatrixActivity : AppCompatActivity() {
    lateinit var binding: ActivityMatrixBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatrixBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.matrix.setOnClickListener { v ->
            rotate(v)
            initRound()
        }
    }

    private fun initRound() {
        val dWidth = binding.androidImgMatrix.drawable.intrinsicWidth
        val dHeight = binding.androidImgMatrix.drawable.intrinsicHeight

        val vWidth = binding.androidImgMatrix.measuredWidth
        val vHeight = binding.androidImgMatrix.measuredHeight
        binding.androidImgMatrix.apply {
            matrix.apply {
                setTranslate(
                    ((vWidth - dWidth) * 0.5f).roundToInt().toFloat(),
                    ((vHeight - dHeight) * 0.5f).roundToInt().toFloat()
                )
                setScale(2f,2f)
            }
        }
    }

    private fun rotate(v: View) {
        // 计算中心点（这里是使用view的中心作为旋转的中心点）
        // 计算中心点（这里是使用view的中心作为旋转的中心点）
        val centerX: Float = v.width / 2.0f
        val centerY: Float = v.height / 2.0f

        //括号内参数分别为（上下文，开始角度，结束角度，x轴中心点，y轴中心点，深度，是否扭曲）

        //括号内参数分别为（上下文，开始角度，结束角度，x轴中心点，y轴中心点，深度，是否扭曲）
        val rotation =
            Rotate3dAnimation(this, 0f,
                360f, centerX, centerY, 0f, true)

        rotation.duration = 3000 //设置动画时长

//        rotation.fillAfter = true //保持旋转后效果

        rotation.fillBefore = true

        rotation.interpolator = LinearInterpolator() //设置插值器
        v.startAnimation(rotation)
    }

    private fun showFlip() {
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