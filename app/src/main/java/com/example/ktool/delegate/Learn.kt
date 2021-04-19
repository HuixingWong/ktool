package com.example.ktool.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Example {
    var p: String by Delegate()
}

class Delegate {
    var value: String?= ""
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$value, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.value = value
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

class FormatDelegate : ReadWriteProperty<Any?, String> {
    private var formattedString: String = ""

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): String {
        return formattedString
    }

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: String
    ) {
        formattedString = value.toLowerCase().capitalize()
    }
}

class Person(name: String, lastname: String) {
    var name: String by FormatDelegate()
    var lastname: String by FormatDelegate()
    var updateCount = 0
}

fun main() {
    var p: String by Delegate()
    p = "123"
    println(p)
//    println(p)
}