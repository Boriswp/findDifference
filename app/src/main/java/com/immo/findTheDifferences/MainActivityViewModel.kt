package com.immo.findTheDifferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immo.findTheDifferences.Utils.prepareToJsonConvert
import com.immo.findTheDifferences.Utils.readTextFile
import com.immo.findTheDifferences.remote.FilesData
import com.immo.findTheDifferences.remote.MainRepository
import com.immo.findTheDifferences.remote.UserFiles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


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


    suspend fun getCurrLvl(): Pair<Int, Int> {
        val job = viewModelScope.async {
            val currLvl = repository.getCurrentLvl()
            when (_lvlDataViewState.value) {
                is LvlDataViewState.Success -> {
                    val id =
                        (_lvlDataViewState.value as LvlDataViewState.Success).response.filesData[currLvl].level_id
                    return@async Pair(currLvl, id)
                }
                else -> {
                    return@async Pair(-1, -1)
                }
            }
        }
        return job.await()
    }

    var isFirstLaunch = true

    init {
        viewModelScope.launch(Dispatchers.IO) {
            isFirstLaunch = repository.isFirstLaunch()
        }
    }

    private val _lvlDataViewState = MutableStateFlow<LvlDataViewState>(LvlDataViewState.Initial)
    val lvlDataViewState = _lvlDataViewState.asStateFlow()

    val state =
        networkStatusTracker.networkStatus
            .map(
                onUnavailable = { InternetState.Error },
                onAvailable = { InternetState.Fetched },
            )

    fun setCurrLvl(lvlId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setCurrentLvl(lvlId)
        }
    }

    fun showError() {
        _lvlDataViewState.value = LvlDataViewState.Error("")
    }


    fun dropLvls() {
        setCurrLvl(0)
        viewModelScope.launch(Dispatchers.IO) {
            repository.setIsFirstLaunch()
        }
        prepareLvls()
    }


    private fun createErrorHandler() = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
        _lvlDataViewState.value = LvlDataViewState.Error(throwable.toString())
    }

    val IoDispatchers: CoroutineContext
        get() = Dispatchers.IO + createErrorHandler()

    fun prepareLvls() {
        _lvlDataViewState.value = LvlDataViewState.Initial
        viewModelScope.launch(IoDispatchers) {
            val result = repository.getTxtFile()
            var ids = repository.getIds().ids
            val currLvl = repository.getCurrentLvl()
            if (result.isSuccessful) {
                val fileStream: InputStream? = result.body()?.byteStream()
                val sXml: String = fileStream?.let { readTextFile(it) }.toString()
                val str = prepareToJsonConvert(sXml)
                val json = JSONObject(str)
                val array = json.getJSONArray(Const.JSON_ARRAY)
                val dataArray = arrayListOf<FilesData>()
                for (i in 0 until array.length()) {
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
                }
                if (ids.isEmpty()) {
                    if (isFirstLaunch) {
                        ids = arrayListOf(0, 1, 2)
                        ids = ids.plus((3 until dataArray.size).shuffled())
                    } else {
                        ids = dataArray.indices.shuffled()
                    }
                    repository.setIds(ids)
                } else if (dataArray.size > ids.size) {
                    ids = ids.plus((ids.size until dataArray.size).shuffled())
                }
                _lvlDataViewState.value = LvlDataViewState.Success(
                    UserFiles(
                        filesData = dataArray,
                        indexes = ids,
                        currLvl = currLvl
                    )
                )
            }
        }
    }
}