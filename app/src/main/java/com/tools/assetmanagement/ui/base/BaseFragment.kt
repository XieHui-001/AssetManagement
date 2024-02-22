package com.tools.assetmanagement.ui.base

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import com.tools.assetmanagement.api.error.ErrorResult
import com.tools.assetmanagement.dialog.LoadingDialog
import com.tools.assetmanagement.event.EventMessage
import com.tools.assetmanagement.utils.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType


abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {
    lateinit var mContext: FragmentActivity
    var contentView: View? = null

    lateinit var vm: VM
    private var _vb: VB? = null
    val vb by lazy { _vb!! }

    private var loadingDialog: LoadingDialog? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz1 = type.actualTypeArguments[0] as Class<VM>
        vm = ViewModelProvider(this).get(clazz1)

        try {
            val clazz2 = type.actualTypeArguments[1] as Class<VB>
            val method = clazz2.getMethod("inflate", LayoutInflater::class.java)
            _vb = method.invoke(null, layoutInflater) as VB
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        mContext = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null == contentView) {
            contentView = vb.root
            init()
            initVM()
            initData()
            initListener()
            LogUtil.e(getClassName())
        }

        return contentView
    }

    private fun init() {
        EventBus.getDefault().register(this)
        //loading
        vm.loadingConfig.observe(viewLifecycleOwner){
            if (it.show) {
                showLoading(it.loadingText, it.loadingCancellable)
            } else {
                dismissLoading()
            }
        }
        //错误信息
        vm.errorData.observe(viewLifecycleOwner){
            if (it.show) {
                if (!TextUtils.isEmpty(it.errorMsg)) {
                    ToastUtils.showShort(it.errorMsg)
                }
            }
            errorResult(it)
        }
    }

    //事件传递
    @Subscribe
    fun onEventMainThread(msg: EventMessage) {
        handleEvent(msg)
    }

    open fun getClassName(): String? {
        val className = "BaseFragment"
        try {
            return javaClass.name
        } catch (e: Exception) {
        }
        return className
    }

    abstract fun initVM()

    abstract fun initData()

    abstract fun initListener()

    //是否第一次执行onResume
    var isFirstLoad = true

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            lazyLoadData()
            isFirstLoad = false
        }
    }

    override fun onPause() {
        super.onPause()
    }


    abstract fun lazyLoadData()

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
    open fun handleEvent(msg: EventMessage) {}

    /**
     * 接口请求错误回调
     */
    open fun errorResult(errorResult: ErrorResult) {}

    override fun onDestroyView() {
        super.onDestroyView()
        contentView = null
    }
}
