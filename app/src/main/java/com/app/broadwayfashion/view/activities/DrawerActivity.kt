package com.app.broadwayfashion.view.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityNavigationBinding
import com.app.broadwayfashion.model.NavigationItem
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.view.adapters.NavigationAdapter

class DrawerActivity : BaseActivity() {

    var navigationLists = arrayListOf<NavigationItem>(
        NavigationItem("Men", R.drawable.men),
        NavigationItem("Women", R.drawable.women),
        NavigationItem("Outerwear", R.drawable.outerwear),
        NavigationItem("Sale", R.drawable.sale),
        NavigationItem("Stores", R.drawable.store),
        NavigationItem("GiftCard", R.drawable.gift_card),
        NavigationItem("Contact", R.drawable.contact)

    )
    lateinit var binding: ActivityNavigationBinding
    override fun getLayoutResourceId(): Any {
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        binding.recyclerViewItems.adapter = NavigationAdapter(navigationLists) {
            if (it.title.equals("Contact", true)) {
                startActivity(ContactsActivity.newInstance(applicationContext))
                Handler(Looper.myLooper()!!).postDelayed(Runnable { finish() }, 1000)
            } else if (it.title.equals("GiftCard", true)) {
                startActivity(GiftCardActivity.newInstance(applicationContext))
                Handler(Looper.myLooper()!!).postDelayed(Runnable { finish() }, 1000)
            } else if (it.title.equals("Stores", true)) {
                startActivity(StoreActivity.newInstance(applicationContext))
                Handler(Looper.myLooper()!!).postDelayed(Runnable { finish() }, 1000)
            } else {
                var intent = Intent()
                intent.putExtra("title", it.title)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

        }
        binding.imgCancel.setOnClickListener {
            finish()
        }
        binding.lnClose.setOnClickListener {
            finish()
        }

    }

    companion object {
        private const val TAG = "LoginActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, DrawerActivity::class.java)
        }
    }

}