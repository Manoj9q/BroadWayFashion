package com.app.broadwayfashion.base

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.utils.AppSharePreferences
import com.app.broadwayfashion.utils.MessageManager
import com.app.broadwayfashion.utils.ProgressLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


abstract class BaseFragment<BINDING : ViewDataBinding> : Fragment() {

    @Inject
    lateinit var preferences: AppSharePreferences
    internal val messageManager by lazy { MessageManager(requireActivity()) }
    lateinit var binding: BINDING
    abstract fun getLayoutResourceId(): Int
    abstract fun onViewReady(view: View)


    open fun onNavigateUpCallBack(): OnBackPressedCallback = NavigateUpOnBackPressed()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false)
        AppConfigurations.resetFilter()
        onViewReady(binding.root)
        return binding!!.root
    }

    protected fun navigateTo(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }

    protected fun navigateToNoBackStack(navDirections: NavDirections) {
        findNavController().navigate(navDirections)

    }

    private fun navigateUpOnBackPressed(enabled: Boolean) {
        if (enabled) requireActivity().onBackPressedDispatcher
            .addCallback(this, onNavigateUpCallBack())
    }

    inner class NavigateUpOnBackPressed : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            try {

                findNavController().navigateUp()
            } catch (ex: Exception) {
            }

        }
    }

    fun showProgress() {
        Log.d("KAM_LOG", "SHOW LOADING...")
        val ft = requireActivity().supportFragmentManager.beginTransaction()
        val dialog = ProgressLoader()
        //dialog.isCancelable = false
        dialog.show(ft, ProgressLoader::class.qualifiedName)
    }

    fun hideProgress() {
        Log.d("KAM_LOG", "HIDE LOADING...")
        val progressFragment: Fragment? =
            requireActivity().supportFragmentManager.findFragmentByTag(ProgressLoader::class.qualifiedName)
        if (progressFragment is ProgressLoader) {
            progressFragment.dismiss()
        }
    }
    fun startActivityAndClearStack(i: Intent) {
        var intent = i
        intent = intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }

    }
}