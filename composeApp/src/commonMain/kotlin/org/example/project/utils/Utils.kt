package org.example.project.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.network.NetWorkResult

fun <T> toResultFlow(call: suspend () -> NetWorkResult<T?>): Flow<NetWorkResult<T>> {
    return flow {
        emit(NetWorkResult.Loading(true))
        val c = call.invoke()
        c.let { response ->
            try {
                println("response${response.data}")
                emit(NetWorkResult.Success(response.data))
            } catch (e: Exception) {
                emit(NetWorkResult.Error(response.data, e.message ?: ""))
            }
        }
    }
}