package com.immo.findTheDifferences

import androidx.lifecycle.ViewModel
import com.immo.findTheDifferences.remote.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


sealed class InternetState {
    object Fetched : InternetState()
    object Error : InternetState()
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: MainRepository,
    networkStatusTracker: NetworkStatusTracker
) :
    ViewModel() {

    val state =
        networkStatusTracker.networkStatus
            .map(
                onUnavailable = { InternetState.Error },
                onAvailable = { InternetState.Fetched },
            )


}