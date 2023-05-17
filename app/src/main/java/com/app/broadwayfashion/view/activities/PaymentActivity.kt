package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityPaymentBinding
import com.app.broadwayfashion.model.orderrequest.OrderRequestModel
import com.app.broadwayfashion.utils.DialogUtils
import com.app.broadwayfashion.utils.getCustomSerializableExtra
import com.app.broadwayfashion.utils.showCustomDialog
import com.app.broadwayfashion.utils.showToast
import com.app.broadwayfashion.view.adapters.PaymentTypeAdapter
import com.app.broadwayfashion.viewmodel.CartViewModel
import com.app.broadwayfashion.viewmodel.OrderViewModel
import kotlinx.coroutines.launch

class PaymentActivity : BaseActivity() {
    lateinit var binding: ActivityPaymentBinding
    private val viewModel: CartViewModel by viewModels()
    private val orderViewModel: OrderViewModel by viewModels()
    override fun getLayoutResourceId(): Any {
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        bindData()
        handleClick()
        handleObserver()
    }

    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
        binding.tvMakePayment.setOnClickListener {
            lifecycleScope.launch {

                DialogUtils.showCustomDialogYesNo(
                    "Are you sure you want to Place the order?",
                    window.context
                ) { type ->
                    if (type == 1) {
                        handleClearCart()

                    }


                }


            }
        }
    }

    private fun handleObserver() {
        orderViewModel.apiErrorLiveData.observe(this) {
            if (!it.isNullOrEmpty()) {
                hideProgress()
                showCustomDialog(it, window.context) {}
            }
        }
        orderViewModel.successMessageLiveData.observe(this) {
            if (!it.isNullOrEmpty()) {
                hideProgress()
                viewModel.clearAllCart()
                showCustomDialog("Order placed successfully", window.context) {
                    startActivityAndClearStack(DashboardActivity.newInstance(applicationContext,"myOrder"))
                    finish()
                }
            }
        }
    }

    private  fun handleClearCart() {
        var orderRequest = intent.getCustomSerializableExtra<OrderRequestModel>(ORDER_REQUEST)
        if (orderRequest != null) {
            showProgress()
            orderViewModel.createOrder(orderRequest)
        }
        else
            showToast("Something went wrong")


    }

    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "Payment"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
    }

    private fun bindData() {
        binding.recyclerViewCards.adapter = PaymentTypeAdapter()

    }

    companion object {
        private const val TAG = "PaymentActivity"
        private const val ORDER_REQUEST = "PaymentActivity"
        fun newInstance(context: Context, orderRequest: OrderRequestModel): Intent {
            return Intent(context, PaymentActivity::class.java).apply {
                putExtra(ORDER_REQUEST, orderRequest)
            }
        }
    }
}