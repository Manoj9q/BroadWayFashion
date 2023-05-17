package com.app.broadwayfashion.view.fragments


import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseFragment
import com.app.broadwayfashion.databinding.ProfileFragmentBinding
import com.app.broadwayfashion.model.NavigationItem
import com.app.broadwayfashion.utils.CommonUtils
import com.app.broadwayfashion.utils.DialogUtils
import com.app.broadwayfashion.utils.IS_DASHBOARD
import com.app.broadwayfashion.utils.USER_EMAIL
import com.app.broadwayfashion.utils.USER_FIRST_NAME
import com.app.broadwayfashion.utils.USER_LAST_NAME
import com.app.broadwayfashion.utils.USER_SELF_ID
import com.app.broadwayfashion.view.activities.GiftCardActivity
import com.app.broadwayfashion.view.activities.MyAccountActivity
import com.app.broadwayfashion.view.activities.MyAddressActivity
import com.app.broadwayfashion.view.activities.MyCartActivity
import com.app.broadwayfashion.view.activities.MyOrderActivity
import com.app.broadwayfashion.view.activities.NewAddressActivity
import com.app.broadwayfashion.view.activities.ShippingDetailActivity
import com.app.broadwayfashion.view.activities.SplashActivity
import com.app.broadwayfashion.view.adapters.NavigationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>() {

    var navigationListsProfile = arrayListOf<NavigationItem>(
        NavigationItem("My Account", R.drawable.ic_profile),
        NavigationItem("My Address", R.drawable.location),
        NavigationItem("My Orders", R.drawable.orders),
        NavigationItem("My Gift Cards", R.drawable.gift_card),
        NavigationItem("Logout", R.drawable.ic_signout)

    )

    override fun getLayoutResourceId(): Int {
        return R.layout.profile_fragment
    }

    override fun onViewReady(view: View) {
        bindHeader()
        bindData()
        handleClick()
        bindUserInfo()
    }

    private fun handleClick() {
        binding.inHeader.imgShipping.setOnClickListener {
            startActivity(MyCartActivity.newInstance(requireContext()))
        }
        binding.inHeader.imgBack.setOnClickListener {
            requireActivity()?.onBackPressedDispatcher?.onBackPressed()
        }
        binding.lnLogout.setOnClickListener {
            handleLogout()
        }

        binding.imgInstagram.setOnClickListener {
            CommonUtils.openMapMapMapApp("https://www.instagram.com/broadwayfashion/",requireContext())
        }
        binding.imgFacebook.setOnClickListener {
            CommonUtils.openMapMapMapApp("https://www.facebook.com/broadwayfashion/",requireContext())
        }
        binding.imgTwitter.setOnClickListener {
            CommonUtils.openMapMapMapApp("https://twitter.com/broadwayfashion/",requireContext())
        }
    }
    private fun handleLogout(){
        DialogUtils.showCustomDialogYesNo(
            "Are you sure you want to logout?",
            requireActivity().window.context
        ) { type ->
            if (type == 1) {
                preferences.put(false, IS_DASHBOARD)
                preferences.removeUserLogoutData()
                startActivityAndClearStack(SplashActivity.newInstance(requireContext()))
            }


        }
    }

    private fun bindUserInfo() {
        var name = "${preferences.getString(USER_FIRST_NAME, "")} ${
            preferences.getString(
                USER_LAST_NAME, ""
            )
        }"
        var email = preferences.getString(USER_EMAIL, "")
        binding.tvUserName.text = name
        binding.tvEmail.text = email
    }

    private fun bindData() {
        binding.recyclerViewProfileItems.adapter =
            NavigationAdapter(navigationListsProfile) {
                if (it.title.equals("My Account", true)) {
                    startActivity(MyAccountActivity.newInstance(requireContext()))
                } else if (it.title.equals("My Address", true)) {
                    startActivity(NewAddressActivity.newInstance(requireContext(),"profile"))
                } else if (it.title.equals("My Orders", true)) {
                    startActivity(MyOrderActivity.newInstance(requireContext()))
                } else if (it.title.equals("My Gift Cards", true)) {
                    startActivity(GiftCardActivity.newInstance(requireContext()))
                }
                else if (it.title.equals("Logout", true)) {
                    handleLogout()
                }
            }
    }

    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "Profile"
        binding.inHeader.cardSearch.isGone = true
    }
}