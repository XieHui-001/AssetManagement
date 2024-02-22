package com.tools.assetmanagement.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import com.tools.assetmanagement.databinding.DialogMessageBinding

class MessageDialog(context: Context,private val action: Action? = null) : BaseDialog<DialogMessageBinding>(context) {

    private var title: String = "--" // 默认title
    private var message: String = "--" // 默认信息
    private var titleGravity : Int = Gravity.CENTER // 默认title显示位置
    private var messageGravity : Int = Gravity.LEFT // 默认message显示位置
    private var titleFontSize : Float = 18f
    private var messageFontSize : Float = 14f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setOnDismissListener {
            action?.dismiss()
        }
    }

    override fun initData() {
        vb.run {
            tvTitle.apply {
                text = title
                gravity = titleGravity
                textSize = titleFontSize
            }

            tvMessage.apply {
                text = message
                gravity = messageGravity
                textSize = messageFontSize
            }
        }
    }

    override fun initListener() {
        vb.run {
            btnClose.setOnClickListener {
                dismiss()
                setOnDismissListener(null)
            }
        }
    }


    fun setTitle(value: String): MessageDialog {
        this.title = value
        return this
    }

    fun setMessage(value: String): MessageDialog {
        this.message = value
        return this
    }

    fun setTitleGravity(value : Int): MessageDialog{
        this.titleGravity = value
        return this
    }

    fun setMessageGravity(value : Int) : MessageDialog{
        this.messageGravity = value
        return this
    }

    fun setTitleFontSize(value : Float) : MessageDialog{
        this.titleFontSize = value
        return this
    }

    fun setMessageFontSize(value : Float) : MessageDialog{
        this.messageFontSize = value
        return this
    }

    interface Action{
        fun dismiss()
    }
}