package com.study.marvelstudio.response

interface IResponse {
    fun isSuccess(): Boolean

    fun getErrorMessage(): String
}