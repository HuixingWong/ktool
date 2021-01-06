package com.example.ktool.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.ktool.Bird

val profile = MutableLiveData<Bird>()

val user = MutableLiveData<Bird>()

val title = profile.combineWith(user) { bird1, bird2 ->
    "${bird1?.name} ${bird2?.name}"
}

fun <T, K, R> LiveData<T>.combineWith(
    liveData: LiveData<K>,
    block: (T?, K?) -> R
): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(this) {
        result.value = block(this.value, liveData.value)
    }
    result.addSource(liveData) {
        result.value = block(this.value, liveData.value)
    }
    return result
}