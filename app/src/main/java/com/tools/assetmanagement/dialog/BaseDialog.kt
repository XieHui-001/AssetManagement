package com.tools.assetmanagement.dialog


import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.SizeUtils
import com.tools.assetmanagement.R
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType


abstract class BaseDialog<VB : ViewBinding> : Dialog {

    lateinit var vb: VB

    private var hasPadding = true
    private var hasAlpha = 1f

    protected constructor(context: Context) : super(context, R.style.Dialog)
    protected constructor(context: Context, hasPadding: Boolean) : super(context, R.style.Dialog) {
        this.hasPadding = hasPadding
    }

    protected constructor(context: Context, themeResId: Int) : super(context, themeResId)
    protected constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)

    fun setHasPadding(value: Boolean): BaseDialog<VB> {
        this.hasPadding = value
        return this
    }

    fun setAlpha(value: Float): BaseDialog<VB> {
        this.hasAlpha = value
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = javaClass.genericSuperclass as ParameterizedType

        try {
            val clazz = type.actualTypeArguments[0] as Class<VB>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            vb = method.invoke(null, layoutInflater) as VB
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        setContentView(vb.root)
        setCanceledOnTouchOutside(false)

        if (hasPadding) {
            val padding = SizeUtils.dp2px(40.0f)
            window?.run {
                decorView.setPadding(padding, 0, padding, 0) //设置左右边距
            }
        }

        window?.run {
            val lp = attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.alpha = hasAlpha
            onWindowAttributesChanged(lp)
        }

        initData()
        initListener()
    }

    protected abstract fun initData()
    protected abstract fun initListener()
}