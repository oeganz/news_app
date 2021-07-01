package com.ganz.newsapp.base

import android.app.Application
import timber.log.Timber

class BaseApp:Application() {
    companion object {
        var instance: BaseApp? = null
        const val TAG = "BASEAPP"
        fun getSingle(): BaseApp {
            if (instance == null) {
                synchronized(BaseApp::class.java) {
                    if (instance == null)
                        instance = BaseApp()
                }
            }
            return instance!!
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance=this
        initLogger()
    }

    fun initLogger(){
        Timber.plant(Timber.DebugTree())
    }
}