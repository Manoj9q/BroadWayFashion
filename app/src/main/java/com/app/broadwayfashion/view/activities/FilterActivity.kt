package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityFilterBinding
import com.app.broadwayfashion.model.ColorItem
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.view.adapters.ColorAdapter
import com.app.broadwayfashion.view.adapters.FilterBrandAdapter
import com.app.broadwayfashion.view.adapters.FilterSizeAdapter
import com.app.broadwayfashion.view.adapters.FilterSortByAdapter

class FilterActivity : BaseActivity() {
    lateinit var binding: ActivityFilterBinding

    var hashMap = HashMap<String, String>()
    var size = ""
    var brand = ""
    var color = ""
    var orderby = "date"
    var order = "desc"
    var sortText = "desc"

    private fun colorList(applicationContext: Context) = arrayListOf<ColorItem>(
        ColorItem(1155, colorCode = applicationContext.getColor(R.color.filter_aqua)),
        ColorItem(57, colorCode = applicationContext.getColor(R.color.filter_black)),
        ColorItem(99, colorCode = applicationContext.getColor(R.color.filter_blue)),
        ColorItem(186, colorCode = applicationContext.getColor(R.color.filter_brown)),
        ColorItem(187, colorCode = applicationContext.getColor(R.color.filter_grey)),
        ColorItem(185, colorCode = applicationContext.getColor(R.color.filter_green)),
        ColorItem(1274, colorCode = applicationContext.getColor(R.color.filter_lavender)),
        ColorItem(188, colorCode = applicationContext.getColor(R.color.filter_pink)),
        ColorItem(183, colorCode = applicationContext.getColor(R.color.filter_red)),
        ColorItem(1058, colorCode = applicationContext.getColor(R.color.filter_yellow)),
        ColorItem(189, colorCode = applicationContext.getColor(R.color.filter_white))
    )


    override fun getLayoutResourceId(): Any {
        binding = ActivityFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        handleClick()
        bindData()
    }

    private fun bindData() {
        binding.recyclerViewSorts.adapter = FilterSortByAdapter() {
            orderby = "date"
            order = "desc"
            sortText = it

            if (it == "Price: High to Low") {
                order = "desc"
                orderby = "price"
            } else if (it == "Price: Low to High") {
                order = "asc"
                orderby = "price"
            } else if (it == "Popularity") {
                order = "desc"
                orderby = "popularity"
            } else if (it == "Average Rating") {
                order = "desc"
                orderby = "rating"
            } else if (it == "Latest") {
                order = "desc"
                orderby = "date"
            } else {
                order = "desc"
                orderby = it
            }
        }
        binding.recyclerViewSizes.adapter = FilterSizeAdapter() {
            size = it
        }
        binding.recyclerViewBrands.adapter = FilterBrandAdapter() {

            brand = it
        }
        binding.recyclerViewColors.adapter = ColorAdapter(colorList(applicationContext)) {
            color = it
        }
        (binding.recyclerViewSizes.adapter as FilterSizeAdapter).setSelectedItem(AppConfigurations.size)
        (binding.recyclerViewColors.adapter as ColorAdapter).setSelectedItem(AppConfigurations.color)
        (binding.recyclerViewSorts.adapter as FilterSortByAdapter).setSelectedItem(AppConfigurations.sortText)


        if (!AppConfigurations.brand.isNullOrEmpty())
            binding.rlBrand.performClick()
    }

    private fun handleClick() {

        binding.tvClearFilter.setOnClickListener {
            AppConfigurations.resetFilter()
            var intent = Intent()

            setResult(RESULT_OK, intent)
            finish()
        }
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
        binding.tvApply.setOnClickListener {
            AppConfigurations.resetFilter()
            AppConfigurations.brand = brand
            AppConfigurations.color = color
            AppConfigurations.size = size
            AppConfigurations.orderBy = orderby
            AppConfigurations.order = order
            AppConfigurations.sortText = sortText
            var intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.rlBrand.setOnClickListener {
            if (binding.recyclerViewBrands.isVisible) {
                binding.recyclerViewBrands.isGone = true
                binding.imgRotate.rotation = binding.imgRotate.rotation + 180
            } else {
                binding.recyclerViewBrands.isVisible = true
                binding.imgRotate.rotation = binding.imgRotate.rotation + 180
            }
        }

    }

    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "Filter"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
    }

    companion object {
        private const val TAG = "FilterActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, FilterActivity::class.java)
        }
    }
}