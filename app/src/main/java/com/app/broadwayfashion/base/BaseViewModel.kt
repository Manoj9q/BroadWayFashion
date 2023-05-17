package com.app.broadwayfashion.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class BaseViewModel constructor(
    private val context: Context
) : ViewModel() {

    private val _errorOnLoad: MutableSharedFlow<String?> = MutableSharedFlow()
    val errorOnLoad: SharedFlow<String?> = _errorOnLoad


    private val _apiErrorLiveData = MutableLiveData<String?>()
    val apiErrorLiveData: LiveData<String?> = _apiErrorLiveData




    private val _successMessageLiveData = MutableLiveData<String?>()
    val successMessageLiveData: LiveData<String?> = _successMessageLiveData

    private val _loading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val loading: SharedFlow<Boolean> = _loading

    fun setErrorResponse(str: String) {
        _apiErrorLiveData.postValue(str)
    }

    fun setSuccessResponse(str: String) {
        _successMessageLiveData.postValue(str)
    }

    suspend fun setEmitError(str: String)  {
        _errorOnLoad.emit(str)
    }


    suspend fun setLoading(boolean: Boolean) {
        _loading.emit(boolean)
    }

    fun isResponseSuccess(res: Response<Any?>) =
        res.isSuccessful && (res.code() == 200 || res.code() == 201) && res.body() != null

    fun isResponseSuccessNoBody(res: Response<Any>) =
        res.isSuccessful && res.code() == 200
}