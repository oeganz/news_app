/*
 * Created By Ugan saripudin - oeganz1999@gmail.com
 * Copyright (c) 2021
 *
 *
 */

package com.ganz.newsapp.repository.remote

import com.ganz.newsapp.repository.remote.response.ResponseNews
import com.ganz.newsapp.repository.remote.response.ResponseSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object RemoteData {

    fun getTopHeadLine(
        category:String?,sources:String?,search:String?,pageSize:Int=10,page:Int=0
    ): Flow<ResponseNews> {
        return flow {
            newsClient.create(InfNews::class.java)
                .getTopHeadLines(category?:"",sources?:"",search?:"", pageSize, page)
                .let {
                    emit(it)
                }
        }.flowOn(Dispatchers.IO)
    }

    fun getSources(
        category:String?,lang:String="en"
    ): Flow<ResponseSource> {
        return flow {
            newsClient.create(InfNews::class.java)
                .getSources(category,lang)
                .let {
                    emit(it)
                }
        }.flowOn(Dispatchers.IO)
    }
}