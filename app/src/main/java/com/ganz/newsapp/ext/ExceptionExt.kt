/*
 * Created By Ugan saripudin - oeganz1999@gmail.com
 * Copyright (c) 2021
 *
 *
 */

package com.ganz.newsapp.ext

import com.ganz.newsapp.repository.remote.response.ResponseError
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.HttpException
import java.lang.Exception
import java.lang.reflect.Type
import java.util.concurrent.TimeoutException


fun Exception.toErrorResponse():ResponseError{
    return when(this){
        is HttpException->{
            this.response()?.body() as ResponseError
        }
        is TimeoutException->{
            ResponseError(message="Time out..")
        }
        else->{
            ResponseError(message=message?:"Unkown Error")
        }
    }
}
fun Throwable.toErrorResponse():ResponseError{
    return when(this){
        is HttpException->{
           Gson().fromJson(response()?.errorBody()?.string(),ResponseError::class.java)
        }
        is TimeoutException->{
            ResponseError(message="Time out..")
        }
        else->{
            ResponseError(message=message?:"Unkown Error")
        }
    }
}