package com.example.ktool.event

import androidx.lifecycle.*

/**
 * 转发传入的livedata的事件监听，
 * 并且只作出一次反应，
 * 而且支持多观察者
 */

class ForwardLiveEvent<T>(liveData: LiveData<T>) : MutableLiveData<T>() {

    private val liveDataToObserve: LiveData<T>
    private val pendingMap: MutableMap<Int, Boolean>

    init {
        val outputLiveData = MediatorLiveData<T>()
        outputLiveData.addSource(liveData) { currentValue ->
            outputLiveData.value = currentValue
        }
        liveDataToObserve = outputLiveData
        pendingMap = HashMap()
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        pendingMap[observer.hashCode()] = false

        // Observe the internal MutableLiveData
        liveDataToObserve.observe(
                owner,
                Observer { t ->
                    if (pendingMap[observer.hashCode()] == true) { // don't trigger if the observer wasn't registered
                        observer.onChanged(t)
                        pendingMap[observer.hashCode()] = false
                    }
                }
        )
    }

    /**
     * 如何修改传入的livedata的setvalue方法
     */
    override fun setValue(t: T?) {
        pendingMap.forEach { pendingMap[it.key] = true }
        super.setValue(t)
    }
}
