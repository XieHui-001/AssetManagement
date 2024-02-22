package com.tools.assetmanagement.dialog


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.tools.assetmanagement.databinding.DialogLoadingBinding

class LoadingDialog : BaseDialog<DialogLoadingBinding> {

    private var loadingText: String? = null

    constructor(context: Context) : super(context, false)
    constructor(context: Context, loadingText: String?) : super(context, false) {
        this.loadingText = loadingText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        window?.run {
            val lp = attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.alpha = 0.7f
            onWindowAttributesChanged(lp)
        }
    }

    override fun initData() {
        if (!TextUtils.isEmpty(loadingText)) {
            vb.tvLoading.text = loadingText
        }
    }

    override fun initListener() {

    }

    fun updateProgress(value: Int) {
        var progress = value
        if (progress < 0) {
            progress = 0
        } else if (progress > 100) {
            progress = 100
        }
        if (vb.tvProgress.visibility != View.VISIBLE) {
            vb.tvProgress.visibility = View.VISIBLE
        }
        vb.tvProgress.text = "$progress%"
    }
}