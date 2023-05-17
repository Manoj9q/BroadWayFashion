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
import com.app.broadwayfashion.databinding.ActivityMyOrderBinding
import com.app.broadwayfashion.model.order.LineItem
import com.app.broadwayfashion.model.order.OrderResponse
import com.app.broadwayfashion.utils.DialogUtils
import com.app.broadwayfashion.utils.USER_SELF_ID
import com.app.broadwayfashion.utils.showCustomDialog
import com.app.broadwayfashion.utils.showToast
import com.app.broadwayfashion.view.adapters.MyOrderAdapter
import com.app.broadwayfashion.viewmodel.CartViewModel
import com.app.broadwayfashion.viewmodel.OrderViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyOrderActivity : BaseActivity() {
    lateinit var binding: ActivityMyOrderBinding
    private val viewModel: OrderViewModel by viewModels()
    override fun getLayoutResourceId(): Any {
        binding = ActivityMyOrderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        handleClick()
        handleObserver()
        getApiData()
    }

    private fun getApiData() {
        showProgress()
        var userId = preferences.getString(USER_SELF_ID)
        viewModel.getOderList(userId)
    }

    private fun bindData(response: OrderResponse) {
        if (response.isNullOrEmpty()) {
            noData()
        } else {
            var itemList = mutableListOf<LineItem>()
            for (res in response) {
                for (os in res.lineItems!!) {
                    os.status = res.status ?: ""
                    os.orderId = res.id ?: 0
                    itemList.add(os)
                }
            }
            binding.recyclerViewMyOrders.adapter = MyOrderAdapter(itemList) { sel ->
                var obj = response.find { it.id == sel.orderId }
                if (obj != null) {
                    obj.itemId = sel.id ?: 0
                    startActivity(OrderTrackDetailActivity.newInstance(applicationContext, obj))
                }
            }
            binding.recyclerViewMyOrders.isVisible = true
            binding.tvNoData.isGone = true
        }

    }

    private fun noData() {
        binding.recyclerViewMyOrders.isGone = true
        binding.tvNoData.isVisible = true
    }

    private fun handleObserver() {
        lifecycleScope.launch {
            viewModel.orderResponse.collectLatest {
                hideProgress()
                if (!it.isNullOrEmpty()) {
                    bindData(it)
                } else
                    noData()
            }
        }
        viewModel.apiErrorLiveData.observe(this) {
            if (!it.isNullOrEmpty()) {
                hideProgress()
                noData()

            }
        }

    }

    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "My Orders"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
    }


    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        private const val TAG = "MyOrderActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, MyOrderActivity::class.java)
        }
    }
}