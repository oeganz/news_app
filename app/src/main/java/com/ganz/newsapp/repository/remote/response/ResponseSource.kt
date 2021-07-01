package com.ganz.newsapp.repository.remote.response


import com.google.gson.annotations.SerializedName

data class ResponseSource(
    @SerializedName("sources")
    var sources: List<Source> = listOf(),
    @SerializedName("status")
    var status: String = ""
)