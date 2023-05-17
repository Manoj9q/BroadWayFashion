package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityEditAccountBinding
import com.app.broadwayfashion.utils.CommonUtils
import com.app.broadwayfashion.utils.USER_EMAIL
import com.app.broadwayfashion.utils.USER_FIRST_NAME
import com.app.broadwayfashion.utils.USER_LAST_NAME
import com.app.broadwayfashion.utils.showCustomDialog
import com.app.broadwayfashion.utils.showToast
import com.app.broadwayfashion.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditAccountActivity : BaseActivity() {
    lateinit var binding: ActivityEditAccountBinding

    private val viewModel: LoginViewModel by viewModels()
    override fun getLayoutResourceId(): Any {
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        handleClick()
        bindUserInfo()
        lifecycleScope.launch {
            handleObserver()
        }
    }
    private suspend fun handleObserver() {
        viewModel.successMessageLiveData.observe(this@EditAccountActivity) {
            if (!it.isNullOrEmpty()) {
                hideProgress()
                showCustomDialog(it, window.context) {
                    startActivityAndClearStack(DashboardActivity.newInstance(applicationContext,"profile"))
                }
            }
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
    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
        binding.tvUpdate.setOnClickListener {
            validation()
        }

    }

    private fun validation() = lifecycleScope.launch {
        binding.apply {
            tvErrorValidation.text = ""
            tvErrorValidation.isVisible = true
            if (etFName.text.toString().trim().isNullOrEmpty()) {
                tvErrorValidation.text = "Please enter First name"
            } else if (etLName.text.toString().trim().isNullOrEmpty()) {
                tvErrorValidation.text = "Please enter Last name"
            } else if (etEmail.text.toString().trim().isNullOrEmpty()) {
                tvErrorValidation.text = "Please enter Email address"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString().trim())
                    .matches()
            ) {
                tvErrorValidation.text = "Please enter valid Email Address"
            } else {
                tvErrorValidation.text = ""
                showProgress()
                viewModel.updateProfile(
                    etFName.text.toString().trim(),
                    etLName.text.toString().trim(),
                    etEmail.text.toString().trim()
                )
            }
        }
    }

    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "Edit Account"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
    }

    private fun bindUserInfo() {
        var fName = preferences.getString(USER_FIRST_NAME, "")
        var lName = preferences.getString(USER_LAST_NAME, "")
        var email = preferences.getString(USER_EMAIL, "")
        binding.etFName.setText(fName)
        binding.etLName.setText(lName)
        binding.etEmail.setText(email)
    }

    companion object {
        private const val TAG = "EditAccountActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, EditAccountActivity::class.java)
        }
    }
}