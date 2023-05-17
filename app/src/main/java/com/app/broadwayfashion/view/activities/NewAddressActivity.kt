package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityAddressBinding
import com.app.broadwayfashion.model.customer.Billing
import com.app.broadwayfashion.model.customer.Shipping
import com.app.broadwayfashion.utils.USER_SELF_ID
import com.app.broadwayfashion.utils.showToast
import com.app.broadwayfashion.viewmodel.LoginViewModel
import com.app.broadwayfashion.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewAddressActivity : BaseActivity() {
    lateinit var binding: ActivityAddressBinding

    private val loginViewModel: LoginViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getLayoutResourceId(): Any {
        binding = ActivityAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        handleClick()
        handleObserver()
        getUserShippingAddress()
    }
    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
        binding.inHeader.imgShipping.setOnClickListener {
            startActivity(ShippingDetailActivity.newInstance(applicationContext,"profile"))
        }
    }
    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "My Address"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.setImageResource(R.drawable.ic_edit_name)
        binding.inHeader.imgShipping.scaleX = 1.2f
        binding.inHeader.imgShipping.scaleY = 1.2f
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
                if (intent.getStringExtra(ShippingDetailActivity.FROM) == "profile")
                    finish()
                else
                    startActivity(OrderDetailsSummeryActivity.newInstance(applicationContext,null,null))
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
            etFirstName.text = shipping.firstName ?: ""
            etLastName.text = shipping.lastName ?: ""
            etCompanyName.text = shipping.company ?: ""
            etCountry.text = shipping.country ?: ""
            etStreet.text = shipping.address1 ?: shipping.address2 ?: ""
            etCity.text = shipping.city ?: ""
            etProvince.text = shipping.state ?: ""
            etPostal.text = shipping.postcode ?: ""
            etPhone.text = shipping.phone ?: ""
            etEmail.text = billing?.email ?: ""
        }
    }
    companion object {
        private const val TAG = "NewAddressActivity"
        var FROM = "from"
        fun newInstance(context: Context, from: String = "order"): Intent {
            return Intent(context, NewAddressActivity::class.java).apply {
                putExtra(FROM, from)
            }
        }
    }
}