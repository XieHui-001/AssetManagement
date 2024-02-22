package com.tools.assetmanagement.api


import com.tools.assetmanagement.model.TestModel
import com.tools.assetmanagement.model.body.BaseBody
import com.tools.assetmanagement.model.response.BaseResult
import retrofit2.http.*


interface ApiService {

    companion object {
        private const val FLAG_SAVE_TOKEN = "flag_save_token:true"
    }

    @POST("test")
    suspend fun test(@Body body: BaseBody): BaseResult<TestModel>
}