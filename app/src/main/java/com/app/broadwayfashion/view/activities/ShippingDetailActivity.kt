package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityShippingDetailBinding
import com.app.broadwayfashion.model.customer.Billing
import com.app.broadwayfashion.model.customer.Shipping
import com.app.broadwayfashion.utils.USER_SELF_ID
import com.app.broadwayfashion.utils.showToast
import com.app.broadwayfashion.viewmodel.LoginViewModel
import com.app.broadwayfashion.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ShippingDetailActivity : BaseActivity() {
    lateinit var binding: ActivityShippingDetailBinding

    private val loginViewModel: LoginViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getLayoutResourceId(): Any {
        binding = ActivityShippingDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        handleClick()
        handleObserver()
        getUserShippingAddress()
    }


    private fun getUserShippingAddress() {
        showProgress()
        loginViewModel.getUserDetailById(preferences.getString(USER_SELF_ID))
    }

    private fun handleObserver() {
        loginViewModel.apiErrorLiveData.observe(this) {
            if (!it.isNullOrEmpty()) {
                hideProgress()
                showToast(it)
            }
        }
        profileViewModel.apiErrorLiveData.observe(this) {
            if (!it.isNullOrEmpty()) {
                hideProgress()
                showToast(it)
            }
        }
        profileViewModel.successMessageLiveData.observe(this) {
            if (!it.isNullOrEmpty()) {
                hideProgress()
                showToast(it)
                if (intent.getStringExtra(FROM) == "profile")
                    startActivityAndClearStack(DashboardActivity.newInstance(applicationContext,"profile"))
                else {
                    startActivity(OrderDetailsSummeryActivity.newInstance(applicationContext,null,null))
                    finish()
                }
            }
        }
        lifecycleScope.launch {
            loginViewModel.userLoadData.collectLatest {
                hideProgress()
                if (it?.shipping != null) {
                    bindData(it.shipping, it.billing)
                }
            }
        }

    }

    private fun bindData(shipping: Shipping, billing: Billing?) {
        binding.apply {
            etFirstName.setText(shipping.firstName ?: "")
            etLastName.setText(shipping.lastName ?: "")
            etCompanyName.setText(shipping.company ?: "")
            etCountry.setText(shipping.country ?: "")
            etStreet.setText(shipping.address1 ?: shipping.address2 ?: "")
            etCity.setText(shipping.city ?: "")
            etProvince.setText(shipping.state ?: "")
            etPostal.setText(shipping.postcode ?: "")
            etPhone.setText(shipping.phone ?: "")
            etEmail.setText(billing?.email ?: "")
        }
    }

    private fun validationHandle() {
        binding.apply {
            tvError.text = ""
            if (etFirstName.text?.trim().isNullOrEmpty()) {
                tvError.text = "Please enter First name"
            } else if (etLastName.text?.trim().isNullOrEmpty()) {
                tvError.text = "Please enter Last name"
            } else if (etCountry.text?.trim().isNullOrEmpty()) {
                tvError.text = "Please enter Country/Region name"
            } else if (etStreet.text?.trim().isNullOrEmpty()) {
                tvError.text = "Please enter your Address detail"
            } else if (etCity.text?.trim().isNullOrEmpty()) {
                tvError.text = "Please enter City name"
            } else if (etProvince.text?.trim().isNullOrEmpty()) {
                tvError.text = "Please enter Province name"
            } else if (etPostal.text?.trim().isNullOrEmpty()) {
                tvError.text = "Please enter Postal code"
            } else if (etPhone.text?.trim().isNullOrEmpty()) {
                tvError.text = "Please enter Phone number"
            } else if (etEmail.text?.trim().isNullOrEmpty()) {
                tvError.text = "Please enter Email address"
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString().trim()).matches()) {
                tvError.text = "Please enter valid email address"
            }
            else {
                var shipping = Shipping()
                var billing = Billing()
                shipping.firstName = etFirstName.text?.trim().toString()
                shipping.lastName = etLastName.text?.trim().toString()
                shipping.company = etCompanyName.text?.trim().toString()
                shipping.country = etCountry.text?.trim().toString()
                shipping.address1 = etStreet.text?.trim().toString()
                shipping.city = etCity.text?.trim().toString()
                shipping.state = etProvince.text?.trim().toString()
                shipping.postcode = etPostal.text?.trim().toString()
                shipping.phone = etPhone.text?.trim().toString()
                billing.email = etEmail.text?.trim().toString()
                billing.phone = etPhone.text?.trim().toString()

                billing.firstName = shipping.firstName
                billing.lastName = shipping.lastName
                billing.company = shipping.company
                billing.country = shipping.country
                billing.city = shipping.city
                billing.address1 = shipping.address1
                billing.state = shipping.state
                billing.postcode = shipping.postcode
                showProgress()

                if (intent.getStringExtra(FROM) == "profile")
                    profileViewModel.updateProfileAddress(shipping, billing)
                else {
                    startActivity(OrderDetailsSummeryActivity.newInstance(applicationContext,shipping,billing))
                    finish()
                }


            }
        }
    }

    private fun bindHeader() {

        binding.inHeader.tvHeaderTitle.text = "Address Details"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
        if (intent.getStringExtra(FROM) == "profile")
            binding.tvSubmit.text = "Update Address"
    }


    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
        binding.tvSubmit.setOnClickListener {
            validationHandle()
            //startActivity(OrderDetailsActivity.newInstance(applicationContext))
        }
    }

    companion object {
        private const val TAG = "ShippingDetailActivity"
        var FROM = "from"
        fun newInstance(context: Context, from: String = "order"): Intent {
            return Intent(context, ShippingDetailActivity::class.java).apply {
                putExtra(FROM, from)
            }
        }
    }
}
