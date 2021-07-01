package com.ganz.newsapp.repository.remote

import com.ganz.newsapp.BuildConfig
import com.ganz.newsapp.utils.Log
import com.google.gson.GsonBuilder
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
/**
created by Oeganz 17/10/2019 00:39
 */
fun createNetworkClient(baseUrl: String) =
    retrofitClient(baseUrl, httpClient(null))
 fun httpClient(header:String?): OkHttpClient {

    val log by lazy {Log("HTTPCLIENT") }
    val clientBuilder = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
     if(header!=null && header.isNotEmpty())
     addReqInterceptor(clientBuilder,header)
    // clientBuilder.addInterceptor(NetworkInterceptor())
    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor { message -> log.d(message) }
        logging.level=HttpLoggingInterceptor.Level.BODY
         // val logging2 = HttpLoggingInterceptor()
        //logging.level=HttpLoggingInterceptor.Level.HEADERS
        clientBuilder.addInterceptor(logging)

        //clientBuilder.addInterceptor(logging2)
    }

    return clientBuilder.build()
}
private fun addReqInterceptor(httpClient: OkHttpClient.Builder,s:String) {
    httpClient.addInterceptor { chain ->
        val request =
            chain.request().newBuilder().addHeader("X-Api-Key",s).build()
        chain.proceed(request)
    }

}
private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        //.addConverterFactory(MoshiConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create( GsonBuilder()
            .setLenient()
            .create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

val  newsClient =  retrofitClient(BuildConfig.MAIN_URL, httpClient(BuildConfig.NEWS_KEY))