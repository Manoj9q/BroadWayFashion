package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityMyAddressBinding
import com.app.broadwayfashion.view.adapters.MyAddressAdapter

class MyAddressActivity : BaseActivity() {
    lateinit var binding: ActivityMyAddressBinding
    override fun getLayoutResourceId(): Any {
        binding = ActivityMyAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        handleClick()
        bindData()
    }

    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }

    }

    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "My Addresses"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
    }

    private fun bindData() {
        binding.recyclerViewAddresses.adapter = MyAddressAdapter()
    }

    companion object {
        private const val TAG = "MyAddressActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, MyAddressActivity::class.java)
        }
    }
}