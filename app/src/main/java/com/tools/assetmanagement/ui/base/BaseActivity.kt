package com.tools.assetmanagement.ui.base

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.tools.assetmanagement.api.error.ErrorResult
import com.tools.assetmanagement.dialog.LoadingDialog
import com.tools.assetmanagement.event.EventCode
import com.tools.assetmanagement.event.EventMessage
import com.tools.assetmanagement.utils.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {
    lateinit var mContext: AppCompatActivity
    lateinit var vm: VM
    lateinit var vb: VB

    private var loadingDialog: LoadingDialog? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initResources()

        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz1 = type.actualTypeArguments[0] as Class<VM>
        vm = ViewModelProvider(this)[clazz1]

        vb = getViewBinding()

        setContentView(vb.root)

        mContext = this
        init()
        initVM()
        initData()
        initListener()
        LogUtil.e(getClassName());

        initImmersionBar()
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected open fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true, 0.2f)
            .transparentStatusBar()
            .init()
    }

    /**
     * 防止系统字体影响到app的字体
     *
     * @return
     */
    open fun initResources(): Resources? {
        val res: Resources = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    //事件传递
    @Subscribe
    fun onEventMainThread(msg: EventMessage) {
        handleEvent(msg)
    }

    open fun getClassName(): String? {
        val className = "BaseActivity"
        try {
            return javaClass.name
        } catch (e: Exception) {
        }
        return className
    }

    abstract fun getViewBinding(): VB

    abstract fun initVM()

    abstract fun initData()

    abstract fun initListener()

    private fun init() {
        EventBus.getDefault().register(this)
        //loading
        vm.loadingConfig.observe(this){
            if (it.show) {
                showLoading(it.loadingText, it.loadingCancellable)
            } else {
                dismissLoading()
            }
        }
        //错误信息
        vm.errorData.observe(this){
            if (it.show) {
                if (!TextUtils.isEmpty(it.errorMsg)) {
                    ToastUtils.showShort(it.errorMsg)
                }
            }
            errorResult(it)
        }
    }

    fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(mContext)
        }
        loadingDialog?.show()
    }

    fun showLoading(text: String) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(mContext, text)
        }
        loadingDialog?.show()
    }

    fun showLoading(flag: Boolean) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(mContext)
        }
        loadingDialog?.setCancelable(flag)
        loadingDialog?.show()
    }

    fun showLoading(text: String, flag: Boolean) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(mContext, text)
        }
        loadingDialog?.setCancelable(flag)
        loadingDialog?.show()
    }

    fun dismissLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    /**
     * 消息、事件接收回调
     */
    open fun handleEvent(msg: EventMessage) {
        if (msg.code == EventCode.LOGIN_OUT) {
            finish()
        }
    }

    /**
     * 接口请求错误回调
     */
    open fun errorResult(errorResult: ErrorResult) {}
}