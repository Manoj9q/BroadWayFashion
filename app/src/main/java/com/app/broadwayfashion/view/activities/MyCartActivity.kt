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
import com.app.broadwayfashion.databinding.ActivityMyCartBinding
import com.app.broadwayfashion.model.cart.AddToCartResponse
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.utils.CommonUtils
import com.app.broadwayfashion.utils.DialogUtils
import com.app.broadwayfashion.utils.USER_SELF_ID
import com.app.broadwayfashion.utils.showCustomDialog
import com.app.broadwayfashion.view.adapters.CartsAdapter
import com.app.broadwayfashion.view.adapters.CustomerCardAdapter
import com.app.broadwayfashion.view.adapters.NewArrivalAdapter
import com.app.broadwayfashion.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyCartActivity : BaseActivity() {
    lateinit var binding: ActivityMyCartBinding
    private val viewModel: CartViewModel by viewModels()

    override fun getLayoutResourceId(): Any {
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        binding.cardTotal.isGone = true
        bindHeader()
        //bindData()
        handleClick()
        handleObserver()
        getCartInfo()
    }

    private fun bindData(response: AddToCartResponse) {
        if (!response.items.isNullOrEmpty()) {
            binding.recyclerViewCarts.adapter = CustomerCardAdapter(response.items) {
                if (it.first == 1) {
                    var item = it.second
                    DialogUtils.updateQuantityDialog(this, item) { quantity ->
                        if (quantity > 0) {
                            updateCardInfo(item.itemKey ?: "", quantity.toString())
                        }
                    }

                } else {
                    DialogUtils.showCustomDialogYesNo(
                        "Are you sure you want to remove item from cart?",
                        window.context
                    ) { type ->
                        if (type == 1) {
                            removeItemFromCart(it.second.itemKey ?: "")

                        }
                    }

                }
            }
            binding.cardTotal.isVisible = true
            binding.tvTotalPrice.text = "$${response.totals?.total!!.toDouble()/100}"
        } else {
            binding.cardTotal.isGone = true
            binding.recyclerViewCarts.adapter = CustomerCardAdapter(emptyList()) {}
            showCustomDialog("No item found in cart.", window.context) {
                finish()
            }
        }
    }

    private fun updateCardInfo(itemKey: String, quantity: String) {
        showProgress()

        if (preferences.getString(USER_SELF_ID).isNullOrEmpty()) {
            var cartKey = "${CommonUtils.getAddToCartUserId(preferences)}AB"
            viewModel.updateCartItem(itemKey, quantity, cartKey)
        } else
            viewModel.updateCartItemByUser(itemKey, quantity)
    }

    private fun removeItemFromCart(itemKey: String) {
        showProgress()

        if (preferences.getString(USER_SELF_ID).isNullOrEmpty()) {
            var cartKey = "${CommonUtils.getAddToCartUserId(preferences)}AB"
            viewModel.removeCartItem(itemKey, cartKey)
        } else {
            viewModel.removeCartItemByUser(itemKey)
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


    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "My Cart"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
    }

    private fun bindData() {
        binding.recyclerViewCarts.adapter = CartsAdapter()
        binding.recyclerViewSimilars.adapter =
            NewArrivalAdapter(AppConfigurations.newArrivalItemList) {}
    }

    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
        binding.tvCheckOut.setOnClickListener {
            startActivity(ShippingDetailActivity.newInstance(applicationContext))
        }
        binding.tvBrowsing.setOnClickListener {
            startActivityAndClearStack(DashboardActivity.newInstance(applicationContext))
        }
    }

    companion object {
        private const val TAG = "MyCartActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, MyCartActivity::class.java)
        }
    }
}