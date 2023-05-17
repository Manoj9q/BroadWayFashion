package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityOrderTrackDetailBinding
import com.app.broadwayfashion.model.order.Billing
import com.app.broadwayfashion.model.order.OrderResponseItem
import com.app.broadwayfashion.model.order.Shipping
import com.app.broadwayfashion.utils.CommonUtils
import com.app.broadwayfashion.utils.getCustomSerializableExtra
import com.app.broadwayfashion.view.adapters.TrackAdapter

class OrderTrackDetailActivity : BaseActivity() {
    lateinit var binding: ActivityOrderTrackDetailBinding
    override fun getLayoutResourceId(): Any {
        binding = ActivityOrderTrackDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindData()
        bindHeader()
        handleClick()
    }

    private fun bindData() {
        //  binding.recyclerViewTrack.adapter = TrackAdapter()
        var orderInfo = intent.getCustomSerializableExtra<OrderResponseItem>(ORDER_ITEM)
        binding.tvOrderNo.text = orderInfo?.orderKey
        var item = orderInfo!!.lineItems?.find { it.id == orderInfo.itemId }
        if (item != null) {
            binding.tvSubTotalValue.text = "$ ${item.price!!}"
            binding.tvAmount.text = "$ ${item.price!!}"
        } else {
            binding.tvSubTotalValue.text = "$ ${orderInfo.total!!}"
            binding.tvAmount.text = "$ ${orderInfo.total}"
        }

        binding.tvPlacedDate.text =
            CommonUtils.getLocalTimeZoneDate("MMM, dd yyyy hh:mm a", orderInfo.dateCreated ?: "")
        bindPriceTotal(orderInfo)
        getShippingAddress(orderInfo.shipping, orderInfo.billing)

        if (orderInfo.status ?: "" != "completed") {
            binding.lnMore.isGone = true
        }
        binding.lnMore.isGone = true
    }

    private fun bindPriceTotal(orderInfo: OrderResponseItem) {
        binding.tvHstValue.text = " ${orderInfo.totalTax}"
        binding.tvShippingValue.text = " ${orderInfo.shippingTotal}"
        binding.tvTotalValue.text = " ${orderInfo.total}"

    }

    private fun getShippingAddress(shipping: Shipping?, billing: Billing?) {
        val address =
            "${shipping?.firstName ?: ""} ${shipping?.lastName ?: ""}\n " +
                    "${billing?.phone ?: ""}\n" +
                    "${shipping?.company ?: ""} ,${shipping?.address1 ?: ""}, ${shipping?.city ?: ""}, " +
                    "${shipping?.state ?: ""}, ${shipping?.postcode ?: ""}"
        binding.tvShipAddress.text = address
    }

    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "Order Detail"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
    }


    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }

    }

    companion object {
        private const val TAG = "OrderTrackDetailActivity"
        private const val ORDER_ITEM = "order_item"
        fun newInstance(context: Context, model: OrderResponseItem): Intent {
            return Intent(context, OrderTrackDetailActivity::class.java).apply {
                putExtra(ORDER_ITEM, model)
            }
        }
    }
}