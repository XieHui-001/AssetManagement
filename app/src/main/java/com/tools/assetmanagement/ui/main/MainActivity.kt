package com.tools.assetmanagement.ui.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.Gravity
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tools.assetmanagement.R
import com.tools.assetmanagement.api.error.ErrorResult
import com.tools.assetmanagement.databinding.ActivityMainBinding
import com.tools.assetmanagement.dialog.MessageDialog
import com.tools.assetmanagement.ui.base.BaseActivity
import com.tools.assetmanagement.ui.main.viewmodel.MainViewModel
import com.tools.assetmanagement.utils.LogUtil
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.resume

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initVM() {
        vm.resultData.observe(this) {
            if (it == null) {
                ToastUtils.showLong("WTF")
            }
        }
    }

    override fun initData() {
        runBlocking {
            fetchData().collect { result ->
                if (result.isSuccess) {
                    result.onSuccess {
                        LogUtils.e("成功: 数据${it}")
                        delay(1000)
                    }
                } else {
                    LogUtils.e("失败: ${result.exceptionOrNull()}")
                }
            }
        }

        lifecycleScope.launch {
            showCustomerDialog("首充", "送999金币")
            showCustomerDialog("年末回馈", "送999金币")
            showCustomerDialog("神秘奖励", "送999金币")
        }
    }

    override fun initListener() {
        vb.run {

        }
    }

    override fun errorResult(errorResult: ErrorResult) {

    }

    private fun fetchData(): Flow<Result<String>> = flow {
        try {
            val data = withTimeout(5000) {
                test()
            }
            emit(Result.success(data))
        } catch (e: TimeoutCancellationException) {
            emit(Result.failure(e))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }


    private suspend fun showCustomerDialog(title: String, content: String) =
        suspendCancellableCoroutine { continuation ->
            MessageDialog(mContext, object : MessageDialog.Action {
                override fun dismiss() {
                    continuation.resume(Unit)
                }
            }).setTitle(title).setMessage(content).setMessageGravity(Gravity.CENTER).show()
        }

    private suspend fun test(): String {
        return "1233"
    }

}