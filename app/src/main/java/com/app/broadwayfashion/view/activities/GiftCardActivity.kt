package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityGiftCardBinding
import com.app.broadwayfashion.view.adapters.GiftCardCategoryAdapter
import com.app.broadwayfashion.view.adapters.GiftCardOccasionAdapter

class GiftCardActivity : BaseActivity() {
    lateinit var binding: ActivityGiftCardBinding
    override fun getLayoutResourceId(): Any {
        binding = ActivityGiftCardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        bindData()
        handleClick()
    }

    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "Gift Cards"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
    }

    private fun bindData() {
        binding.recyclerViewCardCategories.adapter = GiftCardCategoryAdapter()
        binding.recyclerViewCardOccasions.adapter = GiftCardOccasionAdapter()

    }

    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        private const val TAG = "GiftCardActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, GiftCardActivity::class.java)
        }
    }
}