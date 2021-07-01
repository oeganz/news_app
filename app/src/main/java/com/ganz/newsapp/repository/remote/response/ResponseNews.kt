package com.ganz.newsapp.repository.remote.response


import com.google.gson.annotations.SerializedName

data class ResponseNews(
    @SerializedName("articles")
    var articles: List<Article>? = listOf(),
    @SerializedName("status")
    var status: String = "",
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("totalResults")
    var totalResults: Int = 0
)