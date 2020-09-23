package com.study.marvelstudio.model

interface IMarvelCommonResponse {
    fun isSuccess(): Boolean

    fun getErrorMessage(): String
}