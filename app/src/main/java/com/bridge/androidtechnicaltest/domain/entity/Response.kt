package com.bridge.androidtechnicaltest.domain.entity

sealed class Response<T> {
    abstract val data: T?

    data class Loading<T>(override val data: T? = null) : Response<T>()

    data class Success<T>(override val data: T) : Response<T>()

    data class Error<T>(
        val exception: Throwable? = null,
        val errorMessage: String = "An unknown error occurred",
        val httpCode: Int? = null,
        override val data: T? = null
    ) : Response<T>()
}