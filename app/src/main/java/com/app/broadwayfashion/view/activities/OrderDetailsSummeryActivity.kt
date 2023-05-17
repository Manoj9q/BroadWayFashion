package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityOrderDetailsBinding
import com.app.broadwayfashion.model.cart.AddToCartResponse
import com.app.broadwayfashion.model.cart.Customer

import com.app.broadwayfashion.model.customer.Billing
import com.app.broadwayfashion.model.customer.Shipping
import com.app.broadwayfashion.model.orderrequest.LineItem
import com.app.broadwayfashion.model.orderrequest.OrderRequestModel
import com.app.broadwayfashion.model.orderrequest.ShippingLine
import com.app.broadwayfashion.utils.CommonUtils
import com.app.broadwayfashion.utils.USER_SELF_ID
import com.app.broadwayfashion.utils.getCustomSerializableExtra
import com.app.broadwayfashion.utils.showCustomDialog

import com.app.broadwayfashion.view.adapters.OrderListAdapter
import com.app.broadwayfashion.view.adapters.OrderShippingItemAdapter
import com.app.broadwayfashion.viewmodel.CartViewModel
import com.paypal.android.sdk.payments.PayPalConfiguration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderDetailsSummeryActivity : BaseActivity() {
    lateinit var binding: ActivityOrderDetailsBinding
    private val viewModel: CartViewModel by viewModels()
    var addToCardData: AddToCartResponse? = null
    var shipping: Shipping? = null
    var billing: Billing? = null
    override fun getLayoutResourceId(): Any {
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        getIntentData()
        bindData()
        bindHeader()
        handleClick()
        handleObserver()
        getCartInfo()
    }

    private fun getIntentData() {
        if (intent.hasExtra(SHIPPING_ADDRESS) && intent.hasExtra(BILLING_ADDRESS)) {
            shipping = intent.getCustomSerializableExtra<Shipping>(SHIPPING_ADDRESS)
            billing = intent.getCustomSerializableExtra<Billing>(BILLING_ADDRESS)
        }
    }

    private fun getCartInfo() {
        showProgress()
        if (preferences.getString(USER_SELF_ID).isNullOrEmpty()) {
            var cartKey = "${CommonUtils.getAddToCartUserId(preferences)}AB"
            viewModel.getCartInfo(cartKey)
        } else {
            viewModel.getCartInfoByUser()
        }

    }

    private fun handleObserver() {
        lifecycleScope.launch {
            viewModel.cartAdded.collectLatest {
                hideProgress()
                if (it != null) {
                    bindData(it)
                    if (shipping != null)
                        getShippingAddress()
                    else
                        getShippingAddressFromCustomer(it.customer)
                }

            }
        }
        lifecycleScope.launch {
            viewModel.errorOnLoad.collectLatest {
                hideProgress()
                if (!it.isNullOrEmpty())
                    showCustomDialog(it, window.context) {}
            }
        }
        viewModel.apiErrorLiveData.observe(this) {
            if (!it.isNullOrEmpty()) {
                showCustomDialog(it, window.context) {
                    finish()
                }
            }
        }
    }

    private fun bindData(response: AddToCartResponse) {
        if (!response.items.isNullOrEmpty()) {
            addToCardData = response
            binding.recyclerViewOrders.adapter = OrderListAdapter(response.items) {
            }
            binding.tvSubTotalValue.text = "$${response.totals?.subtotal!!.toDouble() / 100}"
            binding.tvShippingValue.text = "$${response.totals.shippingTotal!!.toDouble() / 100}"
            binding.tvHstValue.text = "$${response.totals?.totalTax!!.toDouble() / 100}"
            binding.tvTotalValue.text = "$${response.totals?.total!!.toDouble() / 100}"
        } else {

            binding.recyclerViewOrders.adapter = OrderListAdapter(emptyList()) {}
            showCustomDialog("No item found in cart.", window.context) {
                finish()
            }
        }
    }

    private fun bindData() {
        binding.recyclerViewShips.adapter = OrderShippingItemAdapter()
    }

    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "Order Details"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
    }


    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
        binding.imgEdit.setOnClickListener {
            finish()
        }

        binding.tvPay.setOnClickListener {
            startActivity(PaymentActivity.newInstance(applicationContext, bindOrderData()))
        }
        binding.rlShipping.setOnClickListener {
            if (binding.recyclerViewShips.isVisible) {
                binding.recyclerViewShips.isGone = true
                binding.imgRotate.rotation = binding.imgRotate.rotation + 180
            } else {
                binding.recyclerViewShips.isVisible = true
                binding.imgRotate.rotation = binding.imgRotate.rotation + 180
            }
        }
    }

    private fun bindOrderData(): OrderRequestModel {
        var request = OrderRequestModel()
        request.shipping = shipping
        request.billing = billing
        request.paymentMethod = "cod"
        request.customerId = preferences.get(USER_SELF_ID)
        request.setPaid = true
        request.status = "processing"
        for (obj in addToCardData!!.items!!) {
            request.lineItems!!.add(LineItem(obj.id, obj.quantity?.value, obj.id))
        }
        request.shippingLines!!.add(ShippingLine("flat_rate", "Flat Rate", "10.00"))
        return request

    }

    private fun getShippingAddressFromCustomer(customer: Customer?) {
        if (customer != null) {
            val shipping = customer.shippingAddress

            val address =
                "${shipping?.shippingFirstName ?: ""} ${shipping?.shippingFirstName ?: ""}\n " +
                        "${customer.billingAddress?.billingPhone ?: ""}\n" +
                        "${shipping?.shippingCompany ?: ""} ,${shipping?.shippingAddress1 ?: ""}, ${shipping?.shippingCity ?: ""}, " +
                        "${shipping?.shippingState ?: ""}, ${shipping?.shippingPostcode ?: ""}"
            binding.tvTitleAddressDetail.text = address

        }

    }

    private fun getShippingAddress() {
        val address =
            "${shipping?.firstName ?: ""} ${shipping?.lastName ?: ""}\n " +
                    "${billing?.phone ?: ""}\n" +
                    "${shipping?.company ?: ""} ,${shipping?.address1 ?: ""}, ${shipping?.city ?: ""}, " +
                    "${shipping?.state ?: ""}, ${shipping?.postcode ?: ""}"
        binding.tvTitleAddressDetail.text = address
    }

    companion object {
        private const val TAG = "OrderDetailsActivity"
        private const val SHIPPING_ADDRESS = "ship_address"
        private const val BILLING_ADDRESS = "bill_address"
        fun newInstance(context: Context, shipping: Shipping?, billing: Billing?): Intent {
            return Intent(context, OrderDetailsSummeryActivity::class.java).apply {
                if (shipping != null) {
                    putExtra(SHIPPING_ADDRESS, shipping)
                    putExtra(BILLING_ADDRESS, billing)
                }
            }
        }
    }
}