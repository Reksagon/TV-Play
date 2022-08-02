package com.tevibox.tvplay.di

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

class ActivityProvider(
    application: Application
) {

    var activeActivity: Activity? = null

    init {
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                activeActivity = activity
            }

            override fun onActivityPaused(activity: Activity) {
                activeActivity = null
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }

}