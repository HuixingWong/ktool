package com.example.ktool.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewParent
import androidx.lifecycle.lifecycleScope
import com.example.ktool.R
import com.example.ktool.ext.log
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
    }

    fun test() {
        var parent = window.decorView as ViewParent
        while (parent.parent != null) {
            parent = parent.parent
        }
        log(parent.javaClass.simpleName + parent)
    }


}