package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityProductImageBinding
import com.app.broadwayfashion.model.product.Image
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.view.adapters.ProductImagePagerAdapter


open class ProductImageActivity : BaseActivity() {
    lateinit var binding: ActivityProductImageBinding

    override fun getLayoutResourceId(): Any {
        binding = ActivityProductImageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindData()
        handleClick()
    }

    private fun handleClick() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun bindData() {
        binding.viewPagerImages.adapter =
            ProductImagePagerAdapter(AppConfigurations.selectedImageList as MutableList<Image>)
        var position = intent.getIntExtra(POSITION, 0)
        binding.viewPagerImages.currentItem = position
        binding.dotImage.attachTo(binding.viewPagerImages)

    }

    companion object {
        private const val TAG = "ProductImageActivity"
        private const val POSITION = "position"

        fun newInstance(context: Context, position: Int): Intent {
            return Intent(context, ProductImageActivity::class.java).apply {
                putExtra(POSITION, position)
            }
        }
    }


}