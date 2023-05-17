package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivitySignupBinding
import com.app.broadwayfashion.model.request.SignupRequest
import com.app.broadwayfashion.utils.CommonUtils
import com.app.broadwayfashion.utils.showCustomDialog
import com.app.broadwayfashion.utils.showToast
import com.app.broadwayfashion.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupActivity : BaseActivity() {
    lateinit var binding: ActivitySignupBinding
    var isShowPassword = false
    var isShowConfirmPassword = false
    private val viewModel: LoginViewModel by viewModels()
    override fun getLayoutResourceId(): Any {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        setBlackTextIconStatusBar()
        overrideStatusBar2()
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.transparent)
        lifecycleScope.launch {
            setObserver()
        }

        handleClick()
    }

    private fun handleClick() {
        binding.imgEyeConfirm.setOnClickListener {
            isShowConfirmPassword = !isShowConfirmPassword
            if (isShowConfirmPassword) {
                binding.etConfirmPassword.transformationMethod = null
                binding.imgEyeConfirm.setImageResource(R.drawable.p_hide)
            } else {
                binding.etConfirmPassword.transformationMethod = PasswordTransformationMethod()

                binding.imgEyeConfirm.setImageResource(R.drawable.p_show)
            }
            binding.etConfirmPassword.setSelection(binding.etConfirmPassword.length())
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
        binding.lnLogin.setOnClickListener {
            startActivityAndClearStack(LoginActivity.newInstance(applicationContext))
        }
        binding.tvSignUp.setOnClickListener {
            validation()
        }
        binding.tvTerms.setOnClickListener {
            CommonUtils.openMapMapMapApp("https://www.broadwayfashion.ca/privacy-policy",applicationContext)
        }
    }

    private suspend fun setObserver() {
        viewModel.apiErrorLiveData.observe(this@SignupActivity) {
            if (it != null) {
                hideProgress()
                showCustomDialog(it, window.context) {}
            }
        }
        viewModel.successMessageLiveData.observe(this@SignupActivity) {
            if (it != null) {
                hideProgress()
                showToast(it)
                startActivityAndClearStack(LoginActivity.newInstance(applicationContext))
            }
        }
        viewModel.errorOnLoad.collectLatest {
            if (it != null) {
                hideProgress()
                showCustomDialog(it, window.context) {}
            }
        }
        lifecycleScope.launch {
            viewModel.userSignupData.collectLatest {
                if (it != null) {
                    hideProgress()
                    startActivityAndClearStack(LoginActivity.newInstance(applicationContext))
                }
            }
        }


    }

    private fun validation() = lifecycleScope.launch {
        binding.apply {
            tvErrorValidation.text = ""
            tvErrorValidation.isVisible = true
            if (etFirstName.text.toString().trim().isNullOrEmpty()) {
                tvErrorValidation.text = "Please enter first name"
            } else if (etLastName.text.toString().trim().isNullOrEmpty()) {
                tvErrorValidation.text = "Please enter last name"
            }  else if (etEmail.text.toString().trim().isNullOrEmpty()) {
                tvErrorValidation.text = "Please enter email address"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString().trim()).matches()) {
                tvErrorValidation.text = "Please enter valid email address"
            } else if (etPassword.text.toString().trim().isNullOrEmpty()) {
                tvErrorValidation.text = "Please enter password"
            }

            else if (!CommonUtils.isValidPasswordFormat(etPassword.text.toString())) {
                tvErrorValidation.text =
                    "Please enter At least 5 character length "
                    //"Please enter At least 8 character length including UPPER/lower/special character and numbers."
            }
            else if (etConfirmPassword.text.toString().trim().isNullOrEmpty()) {
                tvErrorValidation.text = "Please enter confirm password"
            }
            else if (etConfirmPassword.text.toString().trim()!= etPassword.text.toString().trim()) {
                tvErrorValidation.text = "Your confirm password doesn't match "
            }

            else if (!chkBox.isChecked) {
                tvErrorValidation.text = "Please agree to Terms and Conditions"
            }
            else {
                tvErrorValidation.text = ""
                showProgress()
                signup()

            }
        }
    }

    private fun signup() = lifecycleScope.launch {
        binding.apply {
            var request = SignupRequest(
                etFirstName.text.toString().trim(),
                etLastName.text.toString().trim(),
                etEmail.text.toString().trim(),
                etEmail.text.toString().trim(),
                etPassword.text.toString().trim()
            )
            viewModel.signupRequest(request)
        }

    }


    companion object {
        private const val TAG = "SignupActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, SignupActivity::class.java)
        }
    }
}