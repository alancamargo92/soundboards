package com.ukdev.carcadasalborghetti.model

sealed class Result<out T>

data class Success<out T>(val body: T) : Result<T>()
object GenericError : Result<Nothing>()
object NetworkError : Result<Nothing>()
