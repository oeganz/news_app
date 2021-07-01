package com.ganz.newsapp.repository.remote.response


import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class Article(
    @SerializedName("author")
    var author: String = "",
    @SerializedName("content")
    var content: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("publishedAt")
    var publishedAt: String = "",
    @SerializedName("source")
    var source: Source = Source(),
    @SerializedName("title")
    var title: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("urlToImage")
    var urlToImage: String = ""
){
    fun getTime():String{
        return try {
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale("id"))
            val date = df.parse(publishedAt.split(".")[0])
            if(date!=null)
                SimpleDateFormat("dd MMM yyyy hh:mm", Locale("id")).format(date)
            else publishedAt
        } catch (e: Exception) {
            publishedAt
        }
    }
}