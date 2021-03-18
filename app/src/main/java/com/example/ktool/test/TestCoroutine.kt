package com.example.ktool.test

import kotlinx.coroutines.*



fun main() {
}

fun test2() {
    val job = GlobalScope.launch {
        println(Thread.currentThread().name)
        test()
    }
    job.cancel()
    Thread.sleep(5000)
}



suspend fun test() {
    val result = suspendCancellableCoroutine<String> { continuation ->
        println(Thread.currentThread().name)
        continuation.resumeWith(Result.success("fuck"))
        continuation.invokeOnCancellation {
            println(Thread.currentThread().name)
            println("取消了")
        }
    }
    println(result)
}