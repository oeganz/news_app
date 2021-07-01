package com.ganz.newsapp.repository.remote
import com.ganz.newsapp.repository.remote.response.ResponseNews
import com.ganz.newsapp.repository.remote.response.ResponseSource
import io.reactivex.Single
import retrofit2.http.*

/**
created by Oeganz 10/12/2019 23:51
 */
interface InfNews {
    //sources, q, language, country, category.
    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("category") category:String,
        @Query("sources") sources:String,
        @Query("q") search:String,
        @Query("pageSize") pageSize:Int,
        @Query("page") page:Int,
        @Query("language") language:String="en",
        @Query("country") country:String=""
    ):ResponseNews

    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category") categery:String?,
        @Query("language") lang:String?,
    ):ResponseSource
}