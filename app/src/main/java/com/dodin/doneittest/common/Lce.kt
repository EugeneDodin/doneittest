package com.dodin.doneittest.common

sealed class Lce<out T> {
    data class Success<T>(val data: T) : Lce<T>()
    data class Error(val message: String) : Lce<Nothing>()
    object Loading: Lce<Nothing>()
}