package com.immo.findTheDifferences

import com.immo.findTheDifferences.remote.FilesData

sealed class LvlDataViewState {
    object Initial : LvlDataViewState()
    data class Success(val response: List<FilesData>) : LvlDataViewState()
    data class Error(val errors: String) : LvlDataViewState()
}
