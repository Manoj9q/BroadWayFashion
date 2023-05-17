package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityForgotNewPasswordActivityBinding
import com.app.broadwayfashion.utils.CommonUtils
import com.app.broadwayfashion.utils.commonApiErrorMessage
import com.app.broadwayfashion.utils.showCustomDialog
import com.app.broadwayfashion.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotNewPasswordActivity : BaseActivity() {
    var email: String = ""

    var isShowPassword = false
    private val viewModel: LoginViewModel by viewModels()
    lateinit var binding: ActivityForgotNewPasswordActivityBinding
    override fun getLayoutResourceId(): Any {
        binding = ActivityForgotNewPasswordActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        setBlackTextIconStatusBar()
        overrideStatusBar()
        email = intent?.getStringExtra(EMAIl) ?: ""
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
            validate()
        }

        binding.imgEye.setOnClickListener {
            isShowPassword = !isShowPassword
            if (isShowPassword) {
                binding.etPassword.transformationMethod = null
                binding.imgEye.setImageResource(R.drawable.p_hide)
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod()

                binding.imgEye.setImageResource(R.drawable.p_show)
            }
            binding.etPassword.setSelection(binding.etPassword.length())
        }
    }

    private fun validate() {
        binding.apply {
            if (etCode.text.toString().trim().length < 4) {
                tvError.text = "Please enter 4 digit code."
            } else if (etPassword.text.toString().trim().isNullOrEmpty()) {
                tvError.text = "Please enter new password."
            } else if (!CommonUtils.isValidPasswordFormat(etPassword.text.toString().trim())) {
                tvError.text =
                    "Please enter At least 5 character length."
                    //"Please enter At least 8 character length including UPPER/lower/special character and numbers."
            } else if (etConfirmPassword.text.toString().trim().isNullOrEmpty()) {
                tvError.text = "Please confirm password."
            } else if (etPassword.text.toString().trim() != etConfirmPassword.text.toString().trim()) {
                tvError.text = "Please enter correct confirm password."
            } else {
                showProgress()
                viewModel.setNewPassword(
                    email,
                    etPassword.text.toString().trim(),
                    etCode.text.toString().trim()
                )

            }
        }
    }

    private suspend fun handleObserver() {
        viewModel.apiErrorLiveData.observe(this@ForgotNewPasswordActivity) {
            if (it != null) {
                hideProgress()
                showCustomDialog(it, window.context) {}
            }
        }
        viewModel.emailOtpResponse.collectLatest {
            hideProgress()
            if (it != null) {
                if (it.data?.status == 200) {
                    startActivityAndClearStack(LoginActivity.newInstance(applicationContext))
                } else {
                    showCustomDialog(it.message ?: commonApiErrorMessage, window.context) {}
                }
            } else {
                showCustomDialog(commonApiErrorMessage, window.context) {}
            }
        }


    }

    companion object {
        private const val TAG = "ForgotNewPasswordActivity"
        private const val EMAIl = "email"
        fun newInstance(context: Context, email: String): Intent {
            return Intent(context, ForgotNewPasswordActivity::class.java).apply {
                putExtra(EMAIl, email)
            }
        }
    }
}