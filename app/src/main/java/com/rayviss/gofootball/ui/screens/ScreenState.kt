package com.rayviss.gofootball.ui.screens

sealed class ScreenState<out T> {
    object Empty : ScreenState<Nothing>()
    object Loading : ScreenState<Nothing>()
    data class Success<T>(val data: T) : ScreenState<T>()
    data class Error(val message: String) : ScreenState<Nothing>()
}