package org.example.project.network

import org.example.project.constants.ApiStatus

sealed class NetWorkResult<out T>(val status: ApiStatus, val data: T?, val message: String?) {
    data class Success<out T>(val _data: T?) :
        NetWorkResult<T>(status = ApiStatus.SUCCESS, data = _data, message = null)
    data class Error<out T>(val _data: T?, val exception: String) :
        NetWorkResult<T>(status = ApiStatus.ERROR, data = _data, message = exception)
    data class Loading<out T>(val isLoading: Boolean) :
        NetWorkResult<T>(status = ApiStatus.LOADING, data = null, message = null)
}