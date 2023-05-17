package com.app.broadwayfashion.view.activities


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.OnboardedActivityBinding
import com.app.broadwayfashion.view.adapters.OnBoardingViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnBoardingActivity : BaseActivity() {
    lateinit var binding: OnboardedActivityBinding
    private var ivArrayDotsPager: Array<ImageView?> = arrayOfNulls(3)
    override fun getLayoutResourceId(): Any {
        binding = OnboardedActivityBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        setBlackTextIconStatusBar()
        overrideStatusBar2()
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.transparent)
        binding.viewPager.adapter = OnBoardingViewPagerAdapter()
        ivArrayDotsPager[0]?.setImageResource(R.color.theme_color)
        handleViewPagerScroll()
        handleClick()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun handleClick() {
        binding.tvSkip.setOnClickListener {
            startActivityAndClearStack(LoginActivity.newInstance(applicationContext))
        }
        binding.imgNext.setOnClickListener {
            if (binding.viewPager.currentItem > 1) {
                startActivityAndClearStack(LoginActivity.newInstance(applicationContext))
            } else
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }

    }


    private fun handleViewPagerScroll() {
        setSelectedDotIndicator(0)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.tvSkip.isGone = true
                    setSelectedDotIndicator(position)
                    binding.imgNext.setImageResource(R.drawable.ic_tick_icon)
                } else {
                    binding.tvSkip.isVisible = true
                    setSelectedDotIndicator(position)
                    binding.imgNext.setImageResource(R.drawable.ic_arrow_right)
                }
            }
        })
    }

    private fun setSelectedDotIndicator(position: Int) {
        binding.indicator1.background =
            ContextCompat.getDrawable(applicationContext, R.drawable.ic_circle_grey)
        binding.indicator2.background =
            ContextCompat.getDrawable(applicationContext, R.drawable.ic_circle_grey)
        binding.indicator3.background =
            ContextCompat.getDrawable(applicationContext, R.drawable.ic_circle_grey)

        if (position == 0) {
            binding.indicator1.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_circle_white)
        } else if (position == 1) {
            binding.indicator2.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_circle_white)
        } else if (position == 2) {
            binding.indicator3.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_circle_white)
        }

    }

    companion object {
        private const val TAG = "OnBoardingActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, OnBoardingActivity::class.java)
        }
    }
}