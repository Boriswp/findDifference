package com.immo.findTheDifferences

import androidx.lifecycle.ViewModel
import com.immo.findTheDifferences.remote.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {
}