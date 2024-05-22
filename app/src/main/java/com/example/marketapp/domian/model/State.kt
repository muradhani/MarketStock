package com.example.marketapp.domian.model

sealed class State<out T> {
    data class Success<T>(var data:T?):State<T>()
    data class Error<T>(val message:String?):State<T>()
    object Loading: State<Nothing>()
    fun toData():T? = if (this is Success) data else null
}