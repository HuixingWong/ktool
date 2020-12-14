package com.example.ktool.operator

data class Point(val x: Int, val y: Int)

operator fun Point.unaryMinus() = Point(-x, -y)

operator fun Point.unaryPlus()  = Point(x*2, y *2)

val point = Point(10, 20)

fun main() {
    println(-point)  // 输出“Point(x=-10, y=-20)”
}