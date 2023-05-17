package com.app.broadwayfashion.view.fragments


import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseFragment
import com.app.broadwayfashion.databinding.WishlistFragmentBinding
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.view.activities.MyCartActivity
import com.app.broadwayfashion.view.activities.ProductDetailActivity
import com.app.broadwayfashion.view.adapters.HorizontalAdapter
import com.app.broadwayfashion.view.adapters.NewArrivalAdapter

class WishListFragment : BaseFragment<WishlistFragmentBinding>() {
    override fun getLayoutResourceId(): Int {
        return R.layout.wishlist_fragment
    }

    override fun onViewReady(view: View) {
        bindHeader()
        bindData()
        handleClick()
    }

    private fun handleClick() {
        binding.inHeader.imgShipping.setOnClickListener {
            startActivity(MyCartActivity.newInstance(requireContext()))
        }
        binding.inHeader.imgBack.setOnClickListener {
            requireActivity()?.onBackPressedDispatcher?.onBackPressed()
        }
    }
    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "WishList"
        binding.inHeader.cardSearch.isGone = true
    }

    private fun bindData() {
        binding.recyclerViewItems.adapter =
            NewArrivalAdapter(AppConfigurations.newArrivalItemList, true){
                startActivity(ProductDetailActivity.newInstance(requireContext()))
            }


    }
}