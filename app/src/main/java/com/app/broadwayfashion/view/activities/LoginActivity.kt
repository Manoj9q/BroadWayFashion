package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityLoginBinding
import com.app.broadwayfashion.utils.USER_SELF_ID
import com.app.broadwayfashion.utils.showCustomDialog
import com.app.broadwayfashion.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    lateinit var binding: ActivityLoginBinding
    var isShowPassword = false
    private val viewModel: LoginViewModel by viewModels()
    override fun getLayoutResourceId(): Any {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        overrideStatusBar2()
        overrideStatusBar()
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.transparent)
        handleClick()

        lifecycleScope.launch {
            handleObserver()
        }
    }

    private fun handleClick() {
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

        binding.tvSkip.setOnClickListener {
            preferences.setString(USER_SELF_ID, "")
            startActivityAndClearStack(DashboardActivity.newInstance(applicationContext))

        }
        binding.lnSignup.setOnClickListener {
            startActivity(SignupActivity.newInstance(applicationContext))

        }
        binding.tvForgotPassword.setOnClickListener {
            startActivity(ForgotPasswordActivity.newInstance(applicationContext))
        }

        binding.tvLogin.setOnClickListener {
            checkValidation()
        }
    }

    private suspend fun handleObserver() {
        viewModel.apiErrorLiveData.observe(this@LoginActivity) {
            if (it != null) {
                hideProgress()
                showCustomDialog(it, window.context) {}
            }
        }
        viewModel.userLoadData.collectLatest {
            hideProgress()
            if (it != null) {
                startActivityAndClearStack(DashboardActivity.newInstance(applicationContext))
            }
        }
        viewModel.loginData.collectLatest {
            /*   if (it != null) {
                   hideProgress()
                   startActivity(DashboardActivity.newInstance(applicationContext))
               }*/
        }
        viewModel.apiErrorOnLoad.collectLatest {
            if (!it.isNullOrEmpty()) {
                hideProgress()
                showCustomDialog(it, window.context) {}
            }
        }
        viewModel.errorOnLoad.collectLatest {
            if (!it.isNullOrEmpty()) {
                hideProgress()
                showCustomDialog(
                    HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                    window.context
                ) {}
            }
        }

    }

    private fun checkValidation() {
        binding.tvErrorValidation.text = ""

        if (binding.etUserName.text.isNullOrEmpty()) {
            binding.tvErrorValidation.isVisible = true
            binding.tvErrorValidation.text = "Please enter username or email."
        }
        if (binding.etPassword.text.isNullOrEmpty()) {
            binding.tvErrorValidation.isVisible = true
            binding.tvErrorValidation.text = "Please enter username or email."
        } else {
            binding.tvErrorValidation.text = ""
            showProgress()
            viewModel.userLogin(
                binding.etUserName.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}