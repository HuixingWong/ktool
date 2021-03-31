package com.example.ktool

import android.app.Activity
import android.app.Application
import android.os.Bundle

class Appp: Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object: MyAppLifecycleCallback() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityCreated(activity, savedInstanceState)
            }
        })
    }

}



open class MyAppLifecycleCallback: Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}