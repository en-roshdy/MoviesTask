package com.example.moviestask.utils

data class BaseResponse<T>(
    val status: Boolean? = false,
    var throwable: Throwable? = null,
    var message: String = "",
    var messages: List<String> = ArrayList(),
    var code: String? = null,
    var data: T? = null
)