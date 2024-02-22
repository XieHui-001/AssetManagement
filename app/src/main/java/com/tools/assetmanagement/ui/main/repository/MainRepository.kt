package com.tools.assetmanagement.ui.main.repository

import com.tools.assetmanagement.api.ControlledRunner
import com.tools.assetmanagement.api.retrofit.apiService
import com.tools.assetmanagement.model.TestModel
import com.tools.assetmanagement.model.body.BaseBody
import com.tools.assetmanagement.model.response.BaseResult

class MainRepository  {

    private val controlledRunner by lazy { ControlledRunner<BaseResult<TestModel>>() }
    suspend fun getTestValue(body: BaseBody) : BaseResult<TestModel> {
        // 如果已经有一个正在运行的请求，那么就返回它。如果没有的话，开启一个新的请求。
        return controlledRunner.joinPreviousOrRun {
            apiService.test(body)
        }
    }
}