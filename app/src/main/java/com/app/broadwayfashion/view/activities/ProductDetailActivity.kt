package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityProductDetailBinding
import com.app.broadwayfashion.model.product.Image
import com.app.broadwayfashion.model.product.ProductItem
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.utils.CommonUtils
import com.app.broadwayfashion.utils.USER_SELF_ID
import com.app.broadwayfashion.utils.showCustomDialog
import com.app.broadwayfashion.view.adapters.FilterSizeAdapter
import com.app.broadwayfashion.view.adapters.NewArrivalAdapter
import com.app.broadwayfashion.view.adapters.ProductImagePagerAdapter
import com.app.broadwayfashion.view.adapters.ProductItemAdapter
import com.app.broadwayfashion.viewmodel.CartViewModel
import com.app.broadwayfashion.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity() {
    lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    var productId = "0"
    var quantity = 1
    var productItem: ProductItem? = null
    override fun getLayoutResourceId(): Any {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        binding.lnAddCart.isGone = true
        binding.viewNestScroll.isGone = true
        productId = intent?.getStringExtra(PRODUCT_ID) as String
        setBlackTextIconStatusBar()
        overrideStatusBar2()
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.transparent)
        // bindData2()
        handleClick()
        handleObserver()
        showProgress()
        viewModel.getProductDetail(id = productId)

    }

    private fun handleObserver() {
        lifecycleScope.launch {
            viewModel.productLoad.collectLatest {
                if (it != null) {
                    productItem = it
                    if (!it.relatedIds.isNullOrEmpty()) {
                        viewModel.getSimilarProduct(it.relatedIds.toString())
                    }
                    delay(1000)
                    hideProgress()
                    bindData(it)
                }

            }
        }
        lifecycleScope.launch {
            cartViewModel.cartAdded.collectLatest {
                hideProgress()
                if (it != null) {
                    startActivity(MyCartActivity.newInstance(applicationContext))
                }

            }
        }
        lifecycleScope.launch {
            viewModel.productListLoad.collectLatest {
                hideProgress()
                if (it != null)
                    bindSimilarData(it)

            }
        }

        lifecycleScope.launch {
            viewModel.errorOnLoad.collectLatest {
                hideProgress()
                if (!it.isNullOrEmpty())
                    showCustomDialog(it, window.context) {}

            }
        }
        cartViewModel.apiErrorLiveData.observe(this) {
            if (!it.isNullOrEmpty()) {
                hideProgress()
                showCustomDialog(it, window.context) {}
            }
        }
    }

    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
        binding.inHeader.imgShipping.setOnClickListener {
            startActivity(MyCartActivity.newInstance(applicationContext))
        }
        binding.tvAddToCart.setOnClickListener {
            validateAddToCart()
        }

        binding.imgDecrement.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvQuantity.text = quantity.toString()
            }
        }
        binding.imgIncrement.setOnClickListener {
            /* if ((productItem?.stockQuantity ?: 0).toInt() > quantity) {
                 quantity++
                 binding.tvQuantity.text = quantity.toString()
             }*/
            if (quantity < 10) {
                quantity++
                binding.tvQuantity.text = quantity.toString()
            }
        }
    }

    private fun validateAddToCart() {
        var productIdMain = productItem?.id.toString()
        var productId = productItem?.id.toString()
        if (!productItem?.variations.isNullOrEmpty()) {
            productId = (productItem?.variations?.get(0)).toString()
        }
        showProgress()
       
        if (preferences.getString(USER_SELF_ID).isNullOrEmpty()) {
            var cartKey = "${CommonUtils.getAddToCartUserId(preferences)}AB"
            cartViewModel.addToCart(cartKey, quantity.toString(), productId, productIdMain)
        }
        else
            cartViewModel.addToCartByUser(quantity.toString(), productId, productIdMain)
    }

    private fun bindSimilarData(list: MutableList<ProductItem>) {
        if (!list.isNullOrEmpty()) {
            binding.tvSimilar.isVisible = true
            binding.recyclerViewSimilars.isVisible = true
            binding.recyclerViewSimilars.adapter = ProductItemAdapter(list) {
                startActivity(
                    newInstance(
                        applicationContext,
                        (it.id ?: 0).toString()
                    )
                )
            }
        } else {
            binding.tvSimilar.isGone = true
            binding.recyclerViewSimilars.isGone = true
        }
    }

    private fun bindData(item: ProductItem) {
        if (!item.relatedIds.isNullOrEmpty()) {
            viewModel.getSimilarProduct(item.relatedIds.toString())
        }
        binding.viewNestScroll.isVisible = true
        binding.lnAddCart.isVisible = true
        if (item.priceHtml.isNullOrEmpty())
            item.priceHtml = "NA"

        var text = HtmlCompat.fromHtml(
            item.priceHtml ?: "$${if (item.onSale == true) item.salePrice else item.price}",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )


        if (text.contains(" ")) {
            binding.tvPriceOld.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            var breakText = text.split(" ")
            binding.tvPriceOld.text = breakText[0]
            binding.tvPriceNew.text = breakText[1]
        } else {
            binding.tvPriceOld.text = ""
            binding.tvPriceNew.text = text
        }
      //  binding.tvPrice.text = "$${if (item.onSale == true) item.salePrice else item.price}"
        binding.tvProductTitle.text = item.name
        var desc = item.description.toString().replace("\\<[^>]*>", "")
        binding.tvDesc.text =
            HtmlCompat.fromHtml(item.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString()
        // binding.tvDesc.text = item.description ?: ""
        binding.viewPagerImages.adapter =
            ProductImagePagerAdapter(item.images as MutableList<Image>, true)
        binding.dotImage.attachTo(binding.viewPagerImages)
        var obj = item.attributes?.find {
            it?.name == "Size"
        }
        if (obj != null) {
            if (obj.options != null) {
                var adapter = FilterSizeAdapter(){}
                binding.recyclerViewSize.adapter = adapter
                adapter.selected = obj.options!![0]
                adapter.setList(obj.options!!)
            }
        }

    }

    private fun bindData2() {
        binding.recyclerViewSimilars.adapter =
            NewArrivalAdapter(AppConfigurations.newArrivalItemList) {}
        binding.recyclerViewSize.adapter = FilterSizeAdapter(){}

    }

    companion object {
        private const val TAG = "ProductDetailActivity"
        private const val PRODUCT_ID = "product_id"
        fun newInstance(context: Context, productId: String = "0"): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId ?: "")
            }
        }
    }
}