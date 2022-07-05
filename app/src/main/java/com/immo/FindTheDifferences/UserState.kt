package com.immo.FindTheDifferences

sealed class UserState {
    object Initial : UserState()
    object Win : UserState()
    object Lose : UserState()
}
