package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivitySearchBinding
import com.app.broadwayfashion.model.product.ProductItem
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.utils.hideKeyboard
import com.app.broadwayfashion.utils.showKeyboard
import com.app.broadwayfashion.view.adapters.CategoriesSearchAdapter
import com.app.broadwayfashion.view.adapters.ProductItemAdapter
import com.app.broadwayfashion.view.fragments.SearchFragment
import com.app.broadwayfashion.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchProductActivity : BaseActivity() {

    private var isDataCompleted = false
    private var loading = false
    private var index = 1
    private val viewModel: ProductViewModel by viewModels()
    lateinit var productAdapter: ProductItemAdapter
    lateinit var binding: ActivitySearchBinding
    private var searchText = ""
    override fun getLayoutResourceId(): Any {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        onScrollRecyclerView()
        handleClick()
        handleObserver()
    }

    private fun bindHeader() {
        productAdapter = ProductItemAdapter(ArrayList(), true) {
            startActivity(
                ProductDetailActivity.newInstance(
                    applicationContext,
                    (it.id ?: 0).toString()
                )
            )
        }
        binding.inHeader.etSearch.setText("")
        binding.inHeader.etSearch.hint = "Search for brands & products"
        binding.recyclerViewItems.adapter = productAdapter
        binding.inHeader.tvHeaderTitle.text = "Search"
        binding.inHeader.cardSearch.isVisible = true
        binding.inHeader.etSearch.isEnabled = true
        binding.inHeader.etSearch.isVisible = true
        binding.inHeader.tvSearch.isGone = true
        binding.inHeader.etSearch?.isCursorVisible = true
        binding.inHeader.etSearch?.requestFocus()
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            binding.inHeader.etSearch.showKeyboard()
            binding.inHeader.etSearch.setSelection(0)
        }, 200)

    }

    private fun handleClick() {

        binding.inHeader.imgBack.setOnClickListener {
            onBackPressedDispatcher?.onBackPressed()
        }
        binding.inHeader.imgSearchIcon.setOnClickListener {
            binding.inHeader.etSearch.hideKeyboard()
            index = 1
            isDataCompleted = false
            productAdapter.clearList()
            searchText = binding.inHeader.etSearch.text.toString().trim()
            showProgress()
            loadData()

        }
    }

    private fun bindData(list: MutableList<ProductItem>) {
        isDataCompleted = list.size < AppConfigurations.PRODUCT_ITEM_COUNT
        productAdapter.setNewList(list)

    }

    private fun handleObserver() {
        onScrollRecyclerView()
        lifecycleScope.launch {
            viewModel.productListLoad.collectLatest {
                hideProgress()
                binding.loader.isGone = true
                if (!it.isNullOrEmpty()) {
                    binding.tvNoData.isGone = true
                    bindData(it)
                } else {
                    if (productAdapter.getListCount() == 0) {
                        binding.tvNoData.isVisible = true
                    }
                    isDataCompleted = true
                }
                loading = false
            }
        }

    }

    private fun loadData() {
        if (searchText.isNullOrEmpty()) {
            viewModel.getProductList(index)
        } else {
            viewModel.getProductListByName(searchText, index)
        }
    }

    private fun typeListener() {
        binding.inHeader.etSearch.addTextChangedListener {


        }
    }

    private fun onScrollRecyclerView() {
        val mLayoutManager: LinearLayoutManager =
            binding?.recyclerViewItems?.layoutManager as LinearLayoutManager
        binding?.recyclerViewItems?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { // check for scroll down
                    val visibleItemCount: Int = mLayoutManager.childCount
                    val totalItemCount: Int = mLayoutManager.itemCount
                    val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()

                    if (!isDataCompleted) {
                        if (!loading) {
                            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                                loading = true
                                index++
                                binding.loader.isVisible = true
                                loadData()
                                //  viewModel.getProductList(index)
                            }
                        }
                    }
                }
            }
        })
    }

    companion object {
        private const val TAG = "SearchProductActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, SearchProductActivity::class.java)
        }
    }
}