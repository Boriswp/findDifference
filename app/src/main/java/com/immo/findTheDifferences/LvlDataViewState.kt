package com.immo.findTheDifferences

import com.immo.findTheDifferences.remote.UserFiles

sealed class LvlDataViewState {
    object Initial : LvlDataViewState()
    data class Success(val response: UserFiles) : LvlDataViewState()
    data class Error(val errors: String) : LvlDataViewState()
}
