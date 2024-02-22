package com.tools.assetmanagement.dialog


import android.content.Context
import android.graphics.Color
import android.text.method.LinkMovementMethod
import com.tools.assetmanagement.R
import com.tools.assetmanagement.databinding.DialogPrivacyBinding
import com.tools.assetmanagement.utils.SpannableStringUtils

class PrivacyDialog(context: Context, val actionListener: ActionListener? = null):
    BaseDialog<DialogPrivacyBinding>(context) {

    override fun initData() {
        vb.tvContent.movementMethod = LinkMovementMethod.getInstance()
        vb.tvContent.highlightColor = Color.TRANSPARENT //去掉阴影
        vb.tvContent.text = SpannableStringUtils.getPrivacySpanString(context, R.color.colorActiveBlue, context.getString(R.string.text_launch_tips), "《隐私政策》", "《用户协议》","位置信息")
    }

    override fun initListener() {
        vb.btnNegative.setOnClickListener {
            actionListener?.onActionClick(false)
            dismiss()
        }
        vb.btnPositive.setOnClickListener {
            actionListener?.onActionClick(true)
            dismiss()
        }
    }

    interface ActionListener {
        fun onActionClick(flag: Boolean)
    }

}