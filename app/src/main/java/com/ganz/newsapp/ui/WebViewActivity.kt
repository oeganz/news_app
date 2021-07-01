/*
 * Created By Ugan saripudin - oeganz1999@gmail.com
 * Copyright (c) 2021
 *
 *
 */

package com.ganz.newsapp.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.ganz.newsapp.R
import com.google.android.material.snackbar.Snackbar

class WebViewActivity :AppCompatActivity() {
    companion object {
        fun show(activity: Activity,url:String){
            activity.startActivity(
                Intent(activity,WebViewActivity::class.java).apply {
                    putExtra("url",url)
                }
            )
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        findViewById<WebView>(R.id.vBack).let {
            it.setOnClickListener {
                finish()
            }
        }
        findViewById<WebView>(R.id.vWebView).let {
            val url=intent.getStringExtra("url")
            it.settings.javaScriptEnabled=true

            if(url!=null)
                it.loadUrl(url)
            else
                Snackbar.make(it, "Url Not Found",
                Snackbar.LENGTH_INDEFINITE)
                .setAction("Back") { v ->
                  finish()
                }.show()
        }
    }
}