package com.tools.assetmanagement.ui.base


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tools.assetmanagement.api.error.ErrorResult
import com.tools.assetmanagement.api.error.ErrorUtil
import com.tools.assetmanagement.dialog.LoadingConfig
import com.tools.assetmanagement.model.response.BaseResult
import com.tools.assetmanagement.model.response.TagResult
import com.tools.assetmanagement.utils.LogUtil
import kotlinx.coroutines.*
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


open class BaseViewModel : ViewModel() {

    private val AUTH_SECRET = "123456"//前后台协议密钥

    var errorData = MutableLiveData<ErrorResult>()//错误信息
    var loadingConfig = MutableLiveData<LoadingConfig>()//是否显示loading

    private fun showLoading(config: LoadingConfig) {
        loadingConfig.value = config
    }

    private fun dismissLoading() {
        loadingConfig.value = LoadingConfig(false)
    }

    private fun showError(error: ErrorResult) {
        errorData.value = error
    }

    /**
     * 无参
     */
    open fun signNoParams(): LinkedHashMap<String, String?> {
        var params = LinkedHashMap<String, String?>()
        params["sign"] = getSign(params)
        return params
    }

    /**
     * 有参
     */
    open fun signParams(params: LinkedHashMap<String, String?>): LinkedHashMap<String, String?> {
        params["sign"] = getSign(params)
        return params
    }


    /**
     * 签名
     */
    private fun getSign(params: LinkedHashMap<String, String?>): String {
        val sb = StringBuilder()
        params.forEach {
            val key = it.key
            var value = ""
            if (!it.value.isNullOrEmpty()) {
                value = URLEncoder.encode(it.value as String?).replace("\\+", "%20")
            }
            sb.append("$key=$value&")
        }
        val s = sb.toString().substring(0, sb.toString().length - 1).toLowerCase() + AUTH_SECRET
        return encryption(s)
    }


    /**
     * MD5加密
     *
     * @param plainText 明文
     * @return 32位密文
     */
    private fun encryption(plainText: String): String {
        var re_md5 = ""
        try {
            val md: MessageDigest = MessageDigest.getInstance("MD5")
            md.update(plainText.toByteArray())
            val b: ByteArray = md.digest()
            var i: Int
            val buf = StringBuffer("")
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0) i += 256
                if (i < 16) buf.append("0")
                buf.append(Integer.toHexString(i))
            }
            re_md5 = buf.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return re_md5
    }

    /**
     * 请求接口
     * 返回值基于BaseResult的子类
     * 可定制是否显示loading和错误提示
     */
    fun <T> launch(
        block: suspend CoroutineScope.() -> BaseResult<T>,//请求接口方法，T表示data实体泛型，调用时可将data对应的bean传入即可
        liveData: MutableLiveData<T>,
        config: LoadingConfig = LoadingConfig(),
        isShowError: Boolean = true,
        apiIndex: Int = 0
    ) {
        if (config.show) showLoading(config)
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            LogUtil.e("请求异常>>" + e.message)
            val errorResult = ErrorUtil.getError(apiIndex, e)
            errorResult.show = isShowError
            showError(errorResult)
        }) {
            try {
                val result = withContext(Dispatchers.IO) { block() }
                if (result.isSuccess()) {//请求成功
                    liveData.value = result.data
                } else {
                    LogUtil.e("请求错误>>" + result.msg)
                    showError(ErrorResult(result.code, result.msg, isShowError, apiIndex))
                }
            } finally {//请求结束
                if (config.dismissWhenEnd) {
                    dismissLoading()
                }
            }
        }
    }

    fun <T> launch(
        block: suspend CoroutineScope.() -> BaseResult<T>,//请求接口方法，T表示data实体泛型，调用时可将data对应的bean传入即可
        config: LoadingConfig = LoadingConfig(),
        isShowError: Boolean = true,
        apiIndex: Int = 0,
        callback: (T?) -> Unit
    ) {
        if (config.show) showLoading(config)
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            LogUtil.e("请求异常>>" + e.message)
            val errorResult = ErrorUtil.getError(apiIndex, e)
            errorResult.show = isShowError
            showError(errorResult)
        }) {
            try {
                val result = withContext(Dispatchers.IO) { block() }
                if (result.isSuccess()) {//请求成功
                    callback(result.data)
                } else {
                    LogUtil.e("请求错误>>" + result.msg)
                    showError(ErrorResult(result.code, result.msg, isShowError, apiIndex))
                }
            } finally {//请求结束
                if (config.dismissWhenEnd) {
                    dismissLoading()
                }
            }
        }
    }

    fun <V, T> launchWithTag(
        block: suspend CoroutineScope.() -> BaseResult<T>,//请求接口方法，T表示data实体泛型，调用时可将data对应的bean传入即可
        liveData: MutableLiveData<TagResult<V, T>>,
        tagData: V,
        config: LoadingConfig = LoadingConfig(),
        isShowError: Boolean = true,
        apiIndex: Int = 0
    ) {
        if (config.show) showLoading(config)
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            LogUtil.e("请求异常>>" + e.message)
            val errorResult = ErrorUtil.getError(apiIndex, e)
            errorResult.show = isShowError
            showError(errorResult)
        }) {
            try {
                val result = withContext(Dispatchers.IO) { block() }
                if (result.isSuccess()) {//请求成功
                    liveData.value = TagResult(tagData, result.data)
                } else {
                    LogUtil.e("请求错误>>" + result.msg)
                    showError(ErrorResult(result.code, result.msg, isShowError, apiIndex))
                }
            } finally {//请求结束
                if (config.dismissWhenEnd) {
                    dismissLoading()
                }
            }
        }
    }

    /**
     * 请求接口
     * 可定制是否显示loading和错误提示
     */
    fun <T> launch2(
        block: suspend CoroutineScope.() -> T,//请求接口方法，T表示data实体泛型，调用时可将data对应的bean传入即可
        liveData: MutableLiveData<T>,
        config: LoadingConfig = LoadingConfig(),
        isShowError: Boolean = true,
        apiIndex: Int = 0
    ) {
        if (config.show) showLoading(config)
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            LogUtil.e("请求异常>>" + e.message)
            val errorResult = ErrorUtil.getError(apiIndex, e)
            errorResult.show = isShowError
            showError(errorResult)
        }) {
            try {
                val result = withContext(Dispatchers.IO) { block() }
                liveData.value = result
            } finally {//请求结束
                if (config.dismissWhenEnd) {
                    dismissLoading()
                }
            }
        }
    }


    /**
     * 请求接口
     * 可定制是否显示loading和错误提示
     */
    fun <T> viewLaunch2(
        block: suspend CoroutineScope.() -> T,//请求接口方法，T表示data实体泛型，调用时可将data对应的bean传入即可
        liveData: MutableLiveData<T>,
        config: LoadingConfig = LoadingConfig(),
        isShowError: Boolean = true,
        apiIndex: Int = 0
    ) {
        if (config.show) showLoading(config)
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            LogUtil.e("请求异常>>" + e.message)
            val errorResult = ErrorUtil.getError(apiIndex, e)
            errorResult.show = isShowError
            showError(errorResult)
        }) {
            try {
                val result = withContext(Dispatchers.IO) { block() }
                liveData.value = result
            } finally {//请求结束
                if (config.dismissWhenEnd) {
                    dismissLoading()
                }
            }
        }
    }

    fun <T> launch3(
        block: suspend CoroutineScope.() -> BaseResult<T>,//请求接口方法，T表示data实体泛型，调用时可将data对应的bean传入即可
        liveData: MutableLiveData<T>,
        config: LoadingConfig = LoadingConfig(),
        isShowError: Boolean = true,
        apiIndex: Int = 0
    ) {
        if (config.show) showLoading(config)
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            LogUtil.e("请求异常>>" + e.message)
            val errorResult = ErrorUtil.getError(apiIndex, e)
            errorResult.show = isShowError
            showError(errorResult)
        }) {
            try {
                val result = withContext(Dispatchers.IO) { block() }
                if (result.isValidData()) {//请求成功
                    liveData.value = result.data
                } else {
                    LogUtil.e("请求错误>>" + result.msg)
                    showError(ErrorResult(result.code, result.msg, isShowError, apiIndex))
                }
            } finally {//请求结束
                if (config.dismissWhenEnd) {
                    dismissLoading()
                }
            }
        }
    }

}
