package com.app.broadwayfashion.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.R
import com.app.broadwayfashion.databinding.PaymentItemBinding


class PaymentTypeAdapter(private var size: Int = 3) :
    RecyclerView.Adapter<PaymentTypeAdapter.ViewHolder>() {
    // Array of images


    var icons = arrayListOf<Int>(
        R.drawable.master_card,
        R.drawable.visa_card,
        R.drawable.paypal_card,
        R.drawable.after_pay_card
    )
    var cards = arrayListOf<String>(
        "***** **** **** 1254",
        "***** **** **** 5467",
        "Paypal",
        "Afterpay"
    )

    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: PaymentItemBinding =
            PaymentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        holder.binding.imgIcon.setImageResource(icons[position])
        holder.binding.tvCardNumber.text = cards[position]


    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: PaymentItemBinding) : RecyclerView.ViewHolder(binding.root) {


    }
}