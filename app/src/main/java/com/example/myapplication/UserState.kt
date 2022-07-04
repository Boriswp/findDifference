package com.example.myapplication

sealed class UserState {
    object Initial : UserState()
    object Win : UserState()
    object Lose : UserState()
}
