package com.app.broadwayfashion.view.fragments

import android.app.Activity
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseFragment
import com.app.broadwayfashion.databinding.CategoryFragmentBinding
import com.app.broadwayfashion.model.CategoryItem
import com.app.broadwayfashion.model.product.ProductItem
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.view.activities.FilterActivity
import com.app.broadwayfashion.view.activities.MyCartActivity
import com.app.broadwayfashion.view.activities.ProductDetailActivity
import com.app.broadwayfashion.view.adapters.CategoriesSearchAdapter
import com.app.broadwayfashion.view.adapters.ProductItemAdapter
import com.app.broadwayfashion.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : BaseFragment<CategoryFragmentBinding>() {
    var title = ""
    var outCategoriesList = arrayListOf<CategoryItem>(
        CategoryItem(id = 111, name = "Men"),
        CategoryItem(id = 112, name = "Women")/*,
        CategoryItem(id = 12345, name = "Kids"),*/
    )
    private var isDataCompleted = false
    private var loading = false
    private val viewModel: ProductViewModel by viewModels()
    private var index = 1
    lateinit var productAdapter: ProductItemAdapter
    private var categoryList = ArrayList<CategoryItem>()
    override fun getLayoutResourceId(): Int {
        return R.layout.category_fragment
    }


    override fun onViewReady(view: View) {
        title = requireArguments().getString(TITLE).toString()
        bindHeader()
        handleObserver()
        handleClick()
        showProgress()
        viewModel.getCategoryList(catId.toString())
        //loadData()

    }

    private fun bindHeader() {
        productAdapter = ProductItemAdapter(ArrayList(), true) {
            startActivity(
                ProductDetailActivity.newInstance(
                    requireContext(),
                    (it.id ?: 0).toString()
                )
            )
        }
        binding.recyclerViewItems.adapter = productAdapter
        binding.inHeader.tvHeaderTitle.text = title
        binding.inHeader.cardSearch.isGone = true
    }

    private fun handleObserver() {
        onScrollRecyclerView()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productListLoad.collectLatest {
                hideProgress()
                binding.loader.isGone = true
                if (!it.isNullOrEmpty()) {
                    binding.tvNoData.isGone = true
                    bindData(it)
                }
                else {
                    if (productAdapter.getListCount() == 0) {
                        binding.tvNoData.isVisible = true
                    }
                    isDataCompleted = true
                }
                loading = false
            }

        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categoryListLoad.collect {
                if (it != null) {
                    categoryList = it
                    catId = categoryList[0].id ?: 0
                    categoryList[0].isSelected = true
                    loadData()
                } else
                    hideProgress()
            }
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
                                viewModel.getProductList(index)
                            }
                        }
                    }
                }
            }
        })
    }

    private fun loadData() {
        viewModel.getProductListByCat(catId, index)
    }

    private fun handleClick() {

        binding.imgFilter.setOnClickListener {
            startForResult.launch(FilterActivity.newInstance(requireContext()))
        }
        binding.inHeader.imgShipping.setOnClickListener {
            startActivity(MyCartActivity.newInstance(requireContext()))
        }
        binding.inHeader.imgBack.setOnClickListener {
            requireActivity()?.onBackPressedDispatcher?.onBackPressed()
        }
    }
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: androidx.activity.result.ActivityResult ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                index = 1
                productAdapter.clearList()
                loading = true
                showProgress()
                isDataCompleted = false
                loadData()
            }
            Activity.RESULT_CANCELED -> {
                // Your code
            }
        }
    }

    private fun bindData(list: MutableList<ProductItem>) {
        isDataCompleted = list.size < AppConfigurations.PRODUCT_ITEM_COUNT

        binding.horizontalRecyclerView.adapter =
            CategoriesSearchAdapter(categoryList) {
                catId = it.id!!
                index = 1
                productAdapter.clearList()
                loading = true
                showProgress()
                isDataCompleted = false
                loadData()
            }
        productAdapter.setNewList(list)

    }

    companion object {
        var catId = 61
        var TITLE = "title"
        fun newInstance(iD: Int) {
            catId = iD
        }
    }
}