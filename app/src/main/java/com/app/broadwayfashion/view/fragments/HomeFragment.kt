package com.app.broadwayfashion.view.fragments

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.transition.Slide
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseFragment
import com.app.broadwayfashion.databinding.HomeFragmentBinding
import com.app.broadwayfashion.model.product.ProductItem
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.utils.DialogUtils
import com.app.broadwayfashion.view.activities.DashboardActivity
import com.app.broadwayfashion.view.activities.DrawerActivity
import com.app.broadwayfashion.view.activities.MyCartActivity
import com.app.broadwayfashion.view.activities.ProductDetailActivity
import com.app.broadwayfashion.view.adapters.BrandImageHomeAdapter
import com.app.broadwayfashion.view.adapters.HomePagerAdapter
import com.app.broadwayfashion.view.adapters.HotWinterAdapter
import com.app.broadwayfashion.view.adapters.NewArrivalAdapter
import com.app.broadwayfashion.view.adapters.ProductItemAdapter
import com.app.broadwayfashion.view.custom.SpeedyLinearLayoutManager
import com.app.broadwayfashion.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>() {
    lateinit var brandImageHomeAdapter: BrandImageHomeAdapter
    private val viewModel: ProductViewModel by viewModels()
    val duration = 10L
    var pixelsToMove = 30

    private var SCROLLING_RUNNABLE: Runnable? = null
    private val mHandler = Handler(Looper.getMainLooper())
    override fun getLayoutResourceId(): Int {
        return R.layout.home_fragment
    }

    override fun onViewReady(view: View) {
        handleClick()
        setObserver()
        bindData()
        setAnimation()
        viewModel.getSimilarProduct("38698,37708,39892,35717,39895")
        handleBack()
        handleScroll()
    }

    private fun handleBack() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                DialogUtils.showCustomDialogYesNo(
                    "Are you sure you want to exit from App",
                    requireActivity().window.context
                ) {
                    if (it == 1)
                        requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun setObserver() {

        lifecycleScope.launch {
            viewModel.productListLoad.collectLatest {
                hideProgress()
                if (it != null)
                    bindSimilarData(it)

            }
        }
    }

    private val drawerActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            CategoriesFragment.newInstance(0)
            var title = it?.data?.getStringExtra("title") ?: ""
            if (!title.isNullOrEmpty()) {
                Handler(Looper.myLooper()!!).postDelayed(Runnable {
                    redirectTarget(title)
                }, 500)
            }

        }

    private fun redirectTarget(title: String, id: Int = 0) {
        if (title.equals("Outerwear", true)) {
            OutwearFragment.newInstance(if (id > 0) id else 111)
            navigateTo(HomeFragmentDirections.outwearFragment(title))
        } else {
            if (title.equals("Men", true))
                CategoriesFragment.newInstance(61)
            else
                if (title.equals("Women", true))
                    CategoriesFragment.newInstance(69)
            navigateTo(HomeFragmentDirections.categoriesFragment(title))
        }
    }

    private fun bindData() {
        binding.recyclerViewBrands.layoutManager =
            SpeedyLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        brandImageHomeAdapter = BrandImageHomeAdapter(AppConfigurations.brandImageList)
        binding.recyclerViewBrands.adapter = brandImageHomeAdapter
        binding.viewPagerCollection.adapter = HomePagerAdapter() {
            if (!it.isNullOrEmpty()) {
                redirectTarget(it)
            } else
                (requireActivity() as DashboardActivity).binding.bottomNavigationView.selectedItemId =
                    R.id.idSearch
        }
        binding.dotsCollection.attachTo(binding.viewPagerCollection)
        binding.recyclerViewArrivals.adapter =
            NewArrivalAdapter(AppConfigurations.newArrivalItemList) {
                startActivity(ProductDetailActivity.newInstance(requireContext()))
            }

        binding.recyclerViewHotWinters.adapter =
            HotWinterAdapter(AppConfigurations.newHotItemList) {
                if (it == 111) {
                    redirectTarget("Outerwear")
                } else if (it == 112) {
                    redirectTarget("Outerwear", 112)
                } else
                    redirectTarget("Men", 69)
            }
    }

    private fun handleScroll() {


        val linearLayoutManager =
            binding.recyclerViewBrands.layoutManager as SpeedyLinearLayoutManager


        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < brandImageHomeAdapter.itemCount - 1) {
                    linearLayoutManager.smoothScrollToPosition(
                        binding.recyclerViewBrands,
                        RecyclerView.State(),
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1
                    )

                } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == brandImageHomeAdapter.itemCount - 1) {

                    binding.recyclerViewBrands.smoothScrollToPosition(0)

                }
            }
        }, 0, 4000)
    }


    private fun handleClick() {
        binding.imgNewArrival.setOnClickListener {
            navigateTo(HomeFragmentDirections.searchFragment())
        }
        binding.imgOuterWinter.setOnClickListener {
            redirectTarget("Outerwear")
        }
        binding.imgNavigation.setOnClickListener {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity())
            // startActivity(DrawerActivity.newInstance(requireContext()), options.toBundle())

            drawerActivity.launch(DrawerActivity.newInstance(requireContext()), options)
        }
        binding.imgShipping.setOnClickListener {
            startActivity(MyCartActivity.newInstance(requireContext()))
        }
    }

    private fun bindSimilarData(list: MutableList<ProductItem>) {
        if (!list.isNullOrEmpty()) {
            binding.recyclerViewArrivals.isVisible = true
            binding.recyclerViewArrivals.adapter = ProductItemAdapter(list) {
                startActivity(
                    ProductDetailActivity.newInstance(
                        requireContext(),
                        (it.id ?: 0).toString()
                    )
                )
            }
        }
    }

    private fun setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            val slide = Slide()
            slide.slideEdge = Gravity.RIGHT
            slide.duration = 300
            slide.interpolator = AccelerateDecelerateInterpolator()
            requireActivity().window.exitTransition = slide
            requireActivity().window.enterTransition = slide
        }
    }

    companion object {
        var SCROLLING_RUNNABLE = 3000
    }
}