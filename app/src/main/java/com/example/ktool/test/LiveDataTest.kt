package com.example.ktool.test

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.ktool.Bird
import com.example.ktool.coroutine.SingleRunner
import com.example.ktool.event.ForwardLiveEvent
import com.example.ktool.livedata.combineWith
import combineTuple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

val bird1 = MutableLiveData<Bird>()

val bird2 = MutableLiveData<Bird>()

val title = bird1.combineWith(bird2) { bird1, bird2 ->
    "${bird1?.name} ${bird2?.name}"
}


fun testEntrance(activity: AppCompatActivity) {
//    testForward(activity)
//    testHash()
    testRunner()
}

val singleRunner = SingleRunner()
var count = AtomicInteger(0)
val random = Random
fun testRunner() {
    GlobalScope.launch {
        singleRunner.afterPrevious {
            val random = random.nextBoolean()
            if (random){
                count.incrementAndGet()
                delay(2000)
            } else {
                count.incrementAndGet()
                delay(500)
            }
            print("打印"+ count.get())
        }
    }
}

fun testCombine() {
    combineTuple(bird1, bird2).observeForever {
        print(it.first?.name)
        print(it.second?.name)
        print("分割线")
    }
    bird1.value = Bird("ersha")
    bird2.value = Bird("haha")
}


fun testForward(activity: AppCompatActivity) {
    val forwardLivedata = ForwardLiveEvent<Bird>(bird1)
    forwardLivedata.observe(activity) {
        print(it?.name)
    }
    GlobalScope.launch(Dispatchers.Main) {
        delay(2000)
        print("start")
        bird1.value = Bird("ostrich")
    }
}

fun print(str: String?) {
    println("😂😂😂$str")
}


