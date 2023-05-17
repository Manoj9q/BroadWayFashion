package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isGone
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityMyAccountBinding
import com.app.broadwayfashion.utils.USER_EMAIL
import com.app.broadwayfashion.utils.USER_FIRST_NAME
import com.app.broadwayfashion.utils.USER_LAST_NAME

class MyAccountActivity : BaseActivity() {
    lateinit var binding: ActivityMyAccountBinding
    override fun getLayoutResourceId(): Any {
        binding = ActivityMyAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        handleClick()
        bindUserInfo()
    }

    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "My Account"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.setImageResource(R.drawable.ic_edit_name)
        binding.inHeader.imgShipping.scaleX = 1.2f
        binding.inHeader.imgShipping.scaleY = 1.2f
    }

    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
        binding.inHeader.imgShipping.setOnClickListener {
            startActivity(EditAccountActivity.newInstance(applicationContext))
        }
    }
    private fun bindUserInfo() {
        var fName = preferences.getString(USER_FIRST_NAME, "")
        var lName = preferences.getString(USER_LAST_NAME, "")
        var email = preferences.getString(USER_EMAIL, "")
        binding.tvFName.text = fName
        binding.tvLName.text = lName
        binding.tvEmail.text = email
    }
    companion object {
        private const val TAG = "MyAccountActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, MyAccountActivity::class.java)
        }
    }
}