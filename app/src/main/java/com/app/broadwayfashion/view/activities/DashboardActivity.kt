package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.DashboardActivityBinding
import com.app.broadwayfashion.utils.IS_DASHBOARD
import com.app.broadwayfashion.utils.USER_SELF_ID
import com.app.broadwayfashion.utils.setupWithNavController
import com.app.broadwayfashion.view.fragments.HomeFragment
import com.app.broadwayfashion.view.fragments.ProfileFragment
import com.app.broadwayfashion.view.fragments.SearchFragment
import com.app.broadwayfashion.view.fragments.WishListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity() {
    lateinit var binding: DashboardActivityBinding
    lateinit var navController: NavController
    override fun getLayoutResourceId(): Any {
        binding = DashboardActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        setBlackTextIconStatusBar()
        if (!preferences.getString(USER_SELF_ID, "").isNullOrEmpty())
            preferences.put(true, IS_DASHBOARD)
        else
            preferences.put(false, IS_DASHBOARD)
        overrideStatusBar()
        // window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.transparent)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.isVisible = true
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            item.isChecked = true
            val frag = navHostFragment.childFragmentManager.fragments[0]
            when (item.itemId) {
                R.id.idHome -> {
                    if (frag !is HomeFragment) {
                        navController.navigate(R.id.homeFragment)
                    }
                }
                R.id.idSearch -> {
                    if (frag !is SearchFragment) {
                        //   navController.navigate(R.id.friendsFragment)
                        navController.navigate(
                            R.id.searchFragment,
                            null,
                            NavOptions.Builder()
                                .setPopUpTo(R.id.searchFragment, false).build()
                        )
                    }
                }
                R.id.idWishList -> {
                    if (frag !is WishListFragment) {
                        navController.navigate(
                            R.id.wishListFragment,
                            null,
                            NavOptions.Builder()
                                .setPopUpTo(R.id.wishListFragment, false).build()
                        )

                    }
                }
                R.id.idProfile -> {
                    if (preferences.getString(USER_SELF_ID).isNullOrEmpty()) {
                        navController.navigate(
                            R.id.profileWithoutLogin,
                            null,
                            NavOptions.Builder()
                                .setPopUpTo(R.id.homeFragment, false).build()
                        )
                    } else {
                        if (frag !is ProfileFragment) {
                            // navController.navigate(R.id.profileFragment)
                            navController.navigate(
                                R.id.profileFragment,
                                null,
                                NavOptions.Builder()
                                    .setPopUpTo(R.id.homeFragment, false).build()
                            )
                        }
                    }
                }
            }
            return@setOnItemSelectedListener true
        }
        if (intent.hasExtra(NAVIGATE)) {
            if (intent.getStringExtra(NAVIGATE) == "profile") {
                binding.bottomNavigationView.selectedItemId = R.id.idProfile
            }
            else if (intent.getStringExtra(NAVIGATE) == "myOrder") {
               startActivity(MyOrderActivity.newInstance(applicationContext))
            }
        }
    }

    override fun onBackPressed() {

        onBackPressedDispatcher.onBackPressed()
    }

    companion object {
        private const val TAG = "DashboardActivity"
        private const val NAVIGATE = "navigate"
        fun newInstance(context: Context, navigate: String = "home"): Intent {
            return Intent(context, DashboardActivity::class.java).apply {
                putExtra(NAVIGATE, navigate)

            }
        }
    }
}