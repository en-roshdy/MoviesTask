package com.example.moviestask.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException

data class BaseResponse<T>(
    val status: Boolean? = false,
    var throwable: Throwable? = null,
    var message: String = "",
    var messages: List<String> = ArrayList<String>(),
    var code: String? = null,
    var errorString: String? = null,
    var errorResponse: ErrorResponse? = null,
//    val links: Links? = null,
    var data: T? = null
){


    fun getErrors(): ErrorResponse?{
        var errorResponse : ErrorResponse? = null
        if (errorString!= null){
            val gson = Gson()
            val type = object : TypeToken<ErrorResponse>() {}.type

            errorResponse =  gson.fromJson<ErrorResponse>(errorString, type)
        }
        return  errorResponse
    }



    fun errorToString(e: Throwable): String {
        return (e as? HttpException)?.response()?.errorBody()?.string()!!

    }
}
