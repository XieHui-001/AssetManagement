package com.tools.assetmanagement.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.tools.assetmanagement.dialog.LoadingConfig
import com.tools.assetmanagement.model.TestModel
import com.tools.assetmanagement.model.body.BaseBody
import com.tools.assetmanagement.ui.base.BaseViewModel
import com.tools.assetmanagement.ui.main.repository.MainRepository

class MainViewModel : BaseViewModel() {

    private val repository by lazy { MainRepository() }

    val resultData by lazy { MutableLiveData<TestModel>() }

    fun getDealerResult(body: BaseBody, loadingConfig: LoadingConfig) { //请求已去重
        launch({ repository.getTestValue(body) }, resultData, loadingConfig)
    }
}