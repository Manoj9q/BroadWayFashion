package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityForgotPasswordBinding
import com.app.broadwayfashion.utils.commonApiErrorMessage
import com.app.broadwayfashion.utils.showCustomDialog
import com.app.broadwayfashion.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModels()

    lateinit var binding: ActivityForgotPasswordBinding
    override fun getLayoutResourceId(): Any {
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        setBlackTextIconStatusBar()
        overrideStatusBar()

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.transparent)
        handleClick()
        lifecycleScope.launch {
            handleObserver()
        }
    }

    private fun handleClick() {
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.tvSubmit.setOnClickListener {
            checkValidation()
        }
    }

    private fun checkValidation() {
        binding.tvError.text = ""
        if (binding.etUserName.text.toString().trim().isNullOrEmpty()) {
            binding.tvError.text = "Please enter registered email address"
            binding.tvError.isVisible = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etUserName.text.toString().trim())
                .matches()
        ) {
            binding.tvError.isVisible = true
            binding.tvError.text = "Please enter valid Email Address"
        } else {
            showProgress()
            viewModel.setOtpPasswordRequest(binding.etUserName.text.toString().trim())
        }
    }

    private suspend fun handleObserver() {
        viewModel.apiErrorLiveData.observe(this@ForgotPasswordActivity) {
            if (it != null) {
                hideProgress()
                showCustomDialog(it, window.context) {}
            }
        }
        viewModel.emailOtpResponse.collectLatest {
            hideProgress()
            if (it != null) {
                if (it.data?.status == 200) {
                    startActivity(
                        ForgotNewPasswordActivity.newInstance(
                            applicationContext,
                            binding.etUserName.text.toString().trim()
                        )
                    )
                } else {
                    showCustomDialog(it.message ?: commonApiErrorMessage, window.context) {}
                }
            } else {
                showCustomDialog(commonApiErrorMessage, window.context) {}
            }
        }


    }

    companion object {
        private const val TAG = "ForgotPasswordActivity"

        fun newInstance(context: Context): Intent {
            return Intent(context, ForgotPasswordActivity::class.java)
        }
    }
}