package com.immo.findTheDifferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immo.findTheDifferences.Utils.prepareToJsonConvert
import com.immo.findTheDifferences.Utils.readTextFile
import com.immo.findTheDifferences.remote.FilesData
import com.immo.findTheDifferences.remote.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
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

    private val _lvlDataViewState = MutableStateFlow<LvlDataViewState>(LvlDataViewState.Initial)
    val lvlDataViewState = _lvlDataViewState.asStateFlow()

    val state =
        networkStatusTracker.networkStatus
            .map(
                onUnavailable = { InternetState.Error },
                onAvailable = { InternetState.Fetched },
            )

    fun getTxtFileData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getTxtFile()
            if (result.isSuccessful) {
                try {
                    val fileStream: InputStream? = result.body()?.byteStream()
                    val sXml: String = fileStream?.let { readTextFile(it) }.toString()
                    val str = prepareToJsonConvert(sXml)
                    val json = JSONObject(str)
                    val array = json.getJSONArray(Const.JSON_ARRAY)
                    val dataArray = arrayListOf<FilesData>()
                    for (i in 0 until array.length()) {
                        try {
                            val oneObject: JSONObject = array.getJSONObject(i)
                            val newFilesData = FilesData(
                                level_id = oneObject.getInt(Const.JSON_ID),
                                picture_background = oneObject.getString(Const.JSON_BACKGROUND),
                                picture_foreground = oneObject.getString(Const.JSON_FOREGROUND),
                                object_height = oneObject.getInt(Const.JSON_OBJECT_HEIGHT),
                                object_width = oneObject.getInt(Const.JSON_OBJECT_WIDTH),
                                padding_left = oneObject.getInt(Const.JSON_PADDING_LEFT),
                                padding_top = oneObject.getInt(Const.JSON_PADDING_TOP)
                            )
                            dataArray.add(newFilesData)
                        } catch (e: JSONException) {
                            _lvlDataViewState.value = LvlDataViewState.Error(e.toString())
                        }
                    }
                    _lvlDataViewState.value = LvlDataViewState.Success(dataArray)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _lvlDataViewState.value = LvlDataViewState.Error(e.toString())
                }
            }
        }
    }
}