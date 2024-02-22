package com.tools.assetmanagement.api.error


data class ErrorResult (
    var errorCode: Int = 0,
    var errorMsg: String? = "",
    var show: Boolean = false,
    var apiIndex: Int = 0//表示api类型（确定是哪个api）
)