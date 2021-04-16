package com.example.ktool.test

import android.graphics.Color
import android.os.Bundle
import android.view.ViewParent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ktool.R
import com.example.ktool.ext.log
import com.example.ktool.source.FpsMonitor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestFpsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_fps)
        //这里的打印结果是DecorView
        test()
        lifecycleScope.launch {
            delay(3000)
            //这里的打印结果是 ViewRootImpl
            test()
        }

        testFps()
    }

    var current = 0
    var colorList = listOf(Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED, Color.MAGENTA, Color.BLACK)
    private fun testFps() {
        findViewById<Button>(R.id.start).setOnClickListener {
            FpsMonitor.startMonitor { fps ->
                findViewById<Button>(R.id.show).apply {
                    text = String.format("fps: %s", fps)
                    setTextColor(colorList[(current++) % 6])
                }
            }

            Thread.sleep(2000)
        }
        findViewById<Button>(R.id.stop).setOnClickListener {
            FpsMonitor.stopMonitor()
            findViewById<Button>(R.id.show).text = ""
        }
    }

    fun test() {
        var parent = window.decorView as ViewParent
        while (parent.parent != null) {
            parent = parent.parent
        }
        log(parent.javaClass.simpleName + parent)
    }


}