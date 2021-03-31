package com.example.ktool.matrix

import android.R.attr
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.example.ktool.R
import com.example.ktool.ext.dp
import android.R.attr.centerY

import android.R.attr.centerX


class MatrixImageVIew(context: Context, attributeSet: AttributeSet?) :
    View(context, attributeSet) {

    @SuppressLint("UseCompatLoadingForDrawables")
    private val bitmap = resources.getDrawable(R.drawable.ic_launcher_foreground, null).apply {
        setTint(Color.RED)
    }.toBitmap(300.dp, 300.dp)

    @SuppressLint("UseCompatLoadingForDrawables")
    private val bitmap2 = resources.getDrawable(R.drawable.ic_launcher_foreground, null).apply {
        setTint(Color.CYAN)
    }.toBitmap(300.dp, 300.dp)

    override fun onDraw(canvas: Canvas) {
        test5(canvas)
    }

    fun test5(canvas: Canvas) {
        val temp = Matrix() // 临时Matrix变量
//        getMatrix(temp) // 获取Matrix
        temp.preTranslate(-centerX.toFloat(), -centerY.toFloat()) // 使用pre将旋转中心移动到和Camera位置相同。
        temp.postTranslate(centerX.toFloat(), centerY.toFloat()) // 使用post将图片(View)移动到原来的位置
        canvas.drawBitmap(bitmap, temp, null)
    }

    fun test4(canvas: Canvas) {
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        val matrix = Matrix()
        matrix.postTranslate(0f, 100f.dp)
        canvas.drawBitmap(bitmap, matrix, null)
    }

    /**
     *  使用camera y 轴平移100
     */
    fun test3(canvas: Canvas) {
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        val camera = Camera()
        camera.translate(0f, 100f.dp, 0f)
        camera.applyToCanvas(canvas)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
    }

    /**
     * 居中绘制缩小的图形
     */
    fun test2(canvas: Canvas) {
        val matrix = Matrix()
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        matrix.reset()
        matrix.postScale(0.5f, 0.5f)
        matrix.preTranslate(-pivotX, -pivotY)
        matrix.postTranslate(pivotX, pivotY)
        canvas.drawBitmap(bitmap2, matrix, null)
    }

    fun test1(canvas: Canvas) {
        val matrix = Matrix()
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        matrix.reset()
        matrix.postScale(0.5f, 0.5f)
        canvas.drawBitmap(bitmap, matrix, null)
    }

}