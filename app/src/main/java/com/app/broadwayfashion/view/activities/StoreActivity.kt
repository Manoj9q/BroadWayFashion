package com.app.broadwayfashion.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityStoreBinding
import com.app.broadwayfashion.model.StoreItem
import com.app.broadwayfashion.utils.CommonUtils
import com.app.broadwayfashion.view.adapters.StoreAdapter

class StoreActivity : BaseActivity() {
    lateinit var binding: ActivityStoreBinding


    override fun getLayoutResourceId(): Any {
        binding = ActivityStoreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        bindData()
        handleClick()
        // loadingWebView("https://www.broadwayfashion.ca/store-locations/")
    }

    /*private fun loadingWebView(url: String) {
        binding!!.webView.settings.javaScriptEnabled = true // NOSONAR
        binding!!.webView.settings.javaScriptCanOpenWindowsAutomatically = true
        binding!!.webView.webChromeClient = object : WebChromeClient() {

            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }
        binding?.webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding?.progressBarWebView?.isGone = true
                binding?.webView?.isVisible = true
            }
        }
        binding?.progressBarWebView?.isVisible = true
        binding?.webView?.isGone = true
        binding?.webView?.loadUrl(url)
    }*/
    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "Store"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
    }

    private fun bindData() {
        binding.recyclerViewStores.adapter = StoreAdapter(storeList){
            CommonUtils.openMapMapMapApp(it,applicationContext)
        }

    }

    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
    }

    private var storeList = arrayListOf<StoreItem>(
        StoreItem(
            "Toronto Premium Outlets",
            "13850 Steeles Ave. W. Unit# 355, Halton Hills, ON. L7G 0J1",
            "https://goo.gl/maps/QEViRc9ChZoWEHUp8",
            "(905) 693 4883",
            "Mon-Fri: 10 am - 9 pm\n" +
                    "    Sat: 9:30 am - 9 pm\n"+
                    "    Sun: 11 am - 7 pm",
            R.drawable.store1

        ),
        StoreItem(
            "CF Toronto Eaton Centre",
            "220 Yonge St, Unit 2116A, Toronto, ON M5B 2H1",
            "https://goo.gl/maps/kfeP7XY6TLMFgRfk8",
            "(416) 296 0609",
            "Mon-Fri: 10 am - 9 pm\n" +
                    "    Sat: 10 am - 9 pm\n" +
                    "    Sun: 11 am - 7 pm",
            R.drawable.store2

        ),
        StoreItem(
            "CF Markville Mall",
            "5000 Hwy 7. Unit #2335 (2nd floor) Markham ON, L3R",
            "https://goo.gl/maps/gQCz3ow4APnhsJyF9",
            "(905) 477 0611",
            "Mon-Fri: 10 am - 9 pm\n" +
                    "    Sat: 10 am - 6 pm\n" +
                    "    Sun: 11 am - 6 pm",
            R.drawable.store3

        )
    )

    companion object {
        private const val TAG = "StoreActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, StoreActivity::class.java)
        }
    }
}