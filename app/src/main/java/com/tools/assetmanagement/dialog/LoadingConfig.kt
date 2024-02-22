package com.tools.assetmanagement.dialog


data class LoadingConfig(
    val show: Boolean = false,          //是否展示加载
    val dismissWhenEnd: Boolean = true, //结束时是否关闭加载
    val loadingText: String = "加载中",
    val loadingCancellable: Boolean = true
)
