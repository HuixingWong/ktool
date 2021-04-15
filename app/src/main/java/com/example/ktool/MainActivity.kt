package com.example.ktool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ktool.databinding.ActivityMainBinding
import com.example.ktool.delegate.contentView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(),CoroutineScope {

    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    protected fun finalize() {
        println("finalize😂😂😂")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            bird = Bird("🐧")
        }
//        testHuawei()

//        test()
        testAsync()
    }

    private fun testHuawei() {
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO){
                testHuaweiDelay()
            }
//            var result = testHuaweiDelay()
            println("😂$result")
        }
    }

    private suspend fun testHuaweiDelay(): String? {
        return withTimeoutOrNull(5000) {
            delay(6000)
            "123"
        }
    }

    fun test() {
        lifecycleScope.launch {
            println("😂"+Thread.currentThread().name)
        }
        GlobalScope.launch {
            println("😂"+Thread.currentThread().name)
        }
    }

    fun testAsync() {
        val result = async {
            delay(300)
            "123"
        }
        launch {
            binding.bird = Bird(result.await())
        }
    }

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Main

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

}

data class Bird (var name: String)