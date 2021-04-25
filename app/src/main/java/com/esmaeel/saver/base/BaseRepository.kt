package com.esmaeel.saver.base


import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException


abstract class BaseRepository(private val contextProviders: ContextProviders) {

    open val UNKNOWN_ERROR = /*resourcesHandler.getString(R.string.error)*/ "UNKNOWN_ERROR"
    val NETWORK_ERROR = /*resourcesHandler.getString(R.string.internet_error_message)*/
        "NETWORK_ERROR"
    val NETWORK_ERROR_TIMEOUT = /*resourcesHandler.getString(R.string.time_out_message)*/
        "NETWORK_TIME_OUT_ERROR"

    fun <T> networkHandler(fetch: suspend () -> T) = flow {
        try {
            emit(fetch.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is TimeoutCancellationException -> {
                    throw Exception(NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    throw Exception(NETWORK_ERROR)
                }
                else -> {
                    throw Exception(throwable.message)
                }
            }
        }

    }.flowOn(contextProviders.IO)
}
