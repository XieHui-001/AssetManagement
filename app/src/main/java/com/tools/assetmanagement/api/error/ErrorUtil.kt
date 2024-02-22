package com.tools.assetmanagement.api.error


import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

object ErrorUtil {

    fun getError(e: Throwable): ErrorResult {
        val errorResult = ErrorResult()
        errorResult.errorMsg = e.message
        when (e) {
            is HttpException -> errorResult.errorCode = e.code()
            is ConnectException -> errorResult.errorMsg = "网络连接超时，请稍后再试"
            is UnknownHostException -> errorResult.errorMsg = "网络连接异常，请稍后再试"
        }
        if (errorResult.errorMsg.isNullOrEmpty()) errorResult.errorMsg = "网络请求失败，请重试"
        return errorResult
    }

    fun getError(apiIndex: Int, e: Throwable): ErrorResult {
        val errorResult = ErrorResult()
        errorResult.apiIndex = apiIndex
        errorResult.errorMsg = e.message
        when (e) {
            is HttpException -> errorResult.errorCode = e.code()
            is ConnectException -> errorResult.errorMsg = "网络连接超时，请稍后再试"
            is UnknownHostException -> errorResult.errorMsg = "网络连接异常，请稍后再试"
        }
        if (errorResult.errorMsg.isNullOrEmpty()) errorResult.errorMsg = "网络请求失败，请重试"
        return errorResult
    }

}