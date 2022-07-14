package com.immo.findTheDifferences

sealed class UserState {
    object Initial : UserState()
    object Win : UserState()
    object Lose : UserState()
    object ShowAd : UserState()
}
