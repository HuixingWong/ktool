package com.example.ktool.coroutine.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
suspend fun main() {

    println("withIndex===============")
    flowOf(1,2,3).withIndex().collect {
        it.index
        it.value
        println(it)
    }
    println("scan===============")
    flowOf(1,2,3,4,5,6,7).scan(0){ a,v ->
       a+v
    }.collect {
        println(it)
    }
    println("dropWhile===============")
    flowOf(1,10,2,3,4,5).dropWhile {
        it != 10
    }.collect {
        println(it)
    }

    println("list dropWhile")
    listOf<Int>(1,2,3,4,5,6,7).dropWhile {
        it !=2
    }.forEach {
        println(it)
    }

    println("take=============")
    flowOf(2,3,5,4,1).take(3).collect {
        println(it)
    }

    println("take while ============")
    flowOf(5,6,4,3,2,9).takeWhile {
        it != 4
    }.collect {
        println(it)
    }

    println("combine")
    flowOf(1,2,3).combine(flowOf("A","B","C")) { a, b ->
        "$a $b"
    }.collect {
        println(it)
    }

    println("combineTransform")
    flowOf(1,2,3).combineTransform(flowOf("A","B","C")) { a, b ->
        emit("$a $b")
    }.collect {
        println(it)
    }

}