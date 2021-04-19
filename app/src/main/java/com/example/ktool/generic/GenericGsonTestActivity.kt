package com.example.ktool.generic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ktool.R
import com.example.ktool.ext.log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class GenericGsonTestActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic_gson_test)
        test()
    }

    private fun test() {
        val mimi = Pet("mimi", "cat")

        log(mimi.toJson())

        val erhuo = User("erhuo", 18, mimi)
        log(erhuo.toJson())

        val jsonStr = erhuo.toJson()

        val fromJson = gson.fromJson(jsonStr, User::class.java)
        log(fromJson.pet?.javaClass?.name)

        val from2 = fromJson<User<Pet>>(jsonStr!!)
        log(from2.pet?.javaClass?.name)
        log(from2.pet?.name)

        val newUser = NewUser("erhuo", 18, mimi.toJson()).toJson()
//    println(erhuo.toJson())
        val fromJson1 = gson.fromJson(newUser, NewUser::class.java)
        log(fromJson1.toString())
        log(fromJson1.pet)
        val fromJson2 = gson.fromJson(fromJson1.pet, Pet::class.java)
        log(fromJson2.name)

//
//    val wanger = User("wanger", 24, null)
//    println(wanger.toJson())

        val erjson = erhuo.toJson()
        val jobj = JSONObject(erjson!!)
        val pet = jobj.get("pet")
        log(pet.toString())

        val petObj = gson.fromJson(pet.toString(), Pet::class.java)
        log(petObj.javaClass.simpleName)
    }

}

val gson = Gson()

fun Any.toJson(): String? {
    return gson.toJson(this)
}

private inline fun <reified T> fromJson(json: String): T {
    return Gson().fromJson(json, object: TypeToken<T>(){}.type)
}

data class User<T>(val name: String, val age: Int, val pet: T?)

data class NewUser(val name: String, val age: Int, val pet: String?)

data class Pet(val name: String, val type: String)