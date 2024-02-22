package com.tools.assetmanagement.ui

import android.app.Activity
import android.os.Build
import androidx.lifecycle.lifecycleScope
import com.tools.assetmanagement.App
import com.tools.assetmanagement.R
import com.tools.assetmanagement.databinding.ActivitySplashBinding
import com.tools.assetmanagement.dialog.PrivacyDialog
import com.tools.assetmanagement.ui.base.BaseActivity
import com.tools.assetmanagement.ui.base.BaseViewModel
import com.tools.assetmanagement.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<BaseViewModel,ActivitySplashBinding>() {

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun initVM() {}

    override fun initData() {
//        lifecycleScope.launch {
//            delay(2 * 1000)
//            finish()
//
//            if (Build.VERSION.SDK_INT >= 34) {
//                overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN,R.anim.slide_right_in, R.anim.slide_left_out)
//            }else{
//                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out)
//            }
//        }
        PrivacyDialog(mContext).show()
    }

    override fun initListener() {

    }
}