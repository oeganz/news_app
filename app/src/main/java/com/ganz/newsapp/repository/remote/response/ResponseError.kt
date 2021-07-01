/*
 * Created By Ugan saripudin - oeganz1999@gmail.com
 * Copyright (c) 2021
 *
 *
 */

package com.ganz.newsapp.repository.remote.response


import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status")
    var status: String = ""
)