package com.ganz.newsapp.utils

import timber.log.Timber
import java.lang.Exception

/**
created by Oeganz 16/10/2019 17:05
 */
class Log(tag:String="GLOBAL") {
    companion object{
        fun d (tag:String,s:String)= Log(tag).d(s)
        fun e (tag:String,s:Exception)= Log(tag).e(s)
        fun t (tag:String,s:Exception)= Log(tag).t(s)
    }
    val log by lazy {
        Timber.tag(tag)
    }


    fun e(exception: Exception){
        log.e(exception)
    }
    fun t(exception: Throwable){
        log.e(exception)
    }

    fun d(string: String){
        log.d(string)
    }




}