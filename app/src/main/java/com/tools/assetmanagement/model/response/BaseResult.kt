package com.tools.assetmanagement.model.response


data class BaseResult<T>(
    val code: Int = 0,
    val msg: String? = null,
    val data: T? = null
) {
    /**
     * 接口返回是否成功
     */
    fun isSuccess(): Boolean {
        return code == 0
    }

    /**
     * 数据返回是否有效
     */
    fun isValid(): Boolean {
        return isSuccess() && data != null
    }

    /**
     * 避免空数组
     */
    fun isValidData (): Boolean {
        return isSuccess() && data != null && data.toString().length > 2
    }
}