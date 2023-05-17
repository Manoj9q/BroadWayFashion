package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.utils.IS_DASHBOARD
import com.app.broadwayfashion.utils.LAST_ADMIN_TOKEN_SYNC
import com.app.broadwayfashion.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val TIMER_TASK_VALUE = 18000000L
    private val viewModel: LoginViewModel by viewModels()

    override fun getLayoutResourceId(): Any {
        //binding = SplashActivityBinding.inflate(layoutInflater)
        return R.layout.splash_activity
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {

        var lastTime = preferences.get<Long?>(LAST_ADMIN_TOKEN_SYNC)
        if (lastTime != null) {
            var duration = System.currentTimeMillis() - lastTime
            if (duration > TIMER_TASK_VALUE) {
                viewModel.getAdminToken()

            }


        } else {
            viewModel.getAdminToken()
        }

        Handler(Looper.myLooper()!!).postDelayed({
            if (preferences.get<Boolean>(IS_DASHBOARD) == true) {
                startActivityAndClearStack(DashboardActivity.newInstance(applicationContext))
            } else
                startActivityAndClearStack(LoginActivity.newInstance(applicationContext))

        }, 4000)

    }

    companion object {
        private const val TAG = "SignupActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }
}