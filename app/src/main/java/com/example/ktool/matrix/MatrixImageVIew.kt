package com.example.ktool.matrix

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
        test2(canvas)
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