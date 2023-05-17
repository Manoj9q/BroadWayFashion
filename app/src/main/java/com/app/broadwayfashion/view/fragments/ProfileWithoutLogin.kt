package com.app.broadwayfashion.view.fragments

import android.view.View
import androidx.core.view.isGone
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseFragment
import com.app.broadwayfashion.databinding.ProfileNoLoginBinding
import com.app.broadwayfashion.view.activities.LoginActivity
import com.app.broadwayfashion.view.activities.MyCartActivity

class ProfileWithoutLogin : BaseFragment<ProfileNoLoginBinding>() {


    override fun getLayoutResourceId(): Int {
        return R.layout.profile_no_login
    }

    override fun onViewReady(view: View) {
        bindHeader()
        handleClick()

    }

    private fun handleClick() {
        binding.tvLoggedIn.setOnClickListener {
            startActivityAndClearStack(LoginActivity.newInstance(requireContext()))
        }
        binding.inHeader.imgShipping.setOnClickListener {
            startActivity(MyCartActivity.newInstance(requireContext()))
        }
        binding.inHeader.imgBack.setOnClickListener {
            requireActivity()?.onBackPressedDispatcher?.onBackPressed()
        }
    }


    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "Profile"
        binding.inHeader.cardSearch.isGone = true
    }
}