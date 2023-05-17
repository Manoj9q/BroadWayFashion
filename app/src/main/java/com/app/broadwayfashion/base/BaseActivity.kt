package com.app.broadwayfashion.base


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app.broadwayfashion.R
import com.app.broadwayfashion.utils.AppSharePreferences
import com.app.broadwayfashion.utils.CommonUtils
import com.app.broadwayfashion.utils.MessageManager
import com.app.broadwayfashion.utils.ProgressLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var preferences: AppSharePreferences
    fun setBlackTextIconStatusBar() {

    }
    fun setBlackTextIconStatusBar2() {
        if (Build.VERSION.SDK_INT > 29)
            window.decorView.windowInsetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
    }
    fun overrideStatusBar(){

    }


    fun overrideStatusBar2(){
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }
    abstract fun getLayoutResourceId(): Any

    internal val messageManager by lazy { MessageManager(this) }
    abstract fun onViewReady(savedInstanceState: Bundle?, intent: Intent?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (CommonUtils.isAppOpen == null) {
            // triggerRebirth(applicationContext)
        }
        if (Build.VERSION.SDK_INT in 21..29) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }

          /*     if(Build.VERSION.SDK_INT>29)
                   window.decorView.windowInsetsController?.setSystemBarsAppearance(
                       0,
                       WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                   )*/

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.white)
        var view = getLayoutResourceId()
        if (view is View)
            setContentView(view)
        else
            setContentView(view as Int)
        supportActionBar?.hide()
        onViewReady(savedInstanceState, intent)
    }

    private fun triggerRebirth(context: Context) {
        val packageManager: PackageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    fun startActivityAndClearStack(i: Intent) {
        var intent = i
        intent = intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    fun changeStatusBarBackgroundColor(color: Int) {
        window.statusBarColor = ContextCompat.getColor(applicationContext, color)
    }

    fun setStatusIconColorIsWhite(boolean: Boolean) {
        if (boolean) {
            if (Build.VERSION.SDK_INT > 29)
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
        } else {
            if (Build.VERSION.SDK_INT > 29)
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )

        }
    }

    fun showProgress() {
        Log.d("KAM_LOG", "SHOW LOADING...")
        val ft = supportFragmentManager.beginTransaction()
        val dialog = ProgressLoader()
        dialog.isCancelable = true
        dialog.show(ft, ProgressLoader::class.qualifiedName)
    }

    fun hideProgress() {
        Log.d("KAM_LOG", "HIDE LOADING...")
        val progressFragment: Fragment? =
            supportFragmentManager.findFragmentByTag(ProgressLoader::class.qualifiedName)
        if (progressFragment is ProgressLoader) {
            if (progressFragment.isAdded)
                progressFragment.dismiss()
        }
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