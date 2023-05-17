package com.app.broadwayfashion.view.activities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import com.app.broadwayfashion.R
import com.app.broadwayfashion.base.BaseActivity
import com.app.broadwayfashion.databinding.ActivityContactUsBinding
import com.app.broadwayfashion.model.NavigationItem
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.view.adapters.ContactsAdapter

class ContactsActivity : BaseActivity() {

    private var contactItems = arrayListOf<NavigationItem>(
        NavigationItem("(905) 693 4883", R.drawable.ic_contact_profile),
        NavigationItem("info@broadwayfashion.ca", R.drawable.ic_email),
        NavigationItem("broadwayfashion.ca", R.drawable.website, "https://www.broadwayfashion.ca/"),
        NavigationItem(
            "broadwayfashion",
            R.drawable.instagram,
            "https://www.instagram.com/broadwayfashion/"
        ),
        NavigationItem(
            "Broadway Fashion",
            R.drawable.ic_facebook,
            "https://www.facebook.com/broadwayfashion/"
        ),
        NavigationItem("BFashionTO", R.drawable.ic_twitter, "https://twitter.com/broadwayfashion/")
    )
    lateinit var binding: ActivityContactUsBinding
    override fun getLayoutResourceId(): Any {
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        return binding.root

    }

    private fun startIntent(text: String) {
        val intentB = Intent(Intent.ACTION_VIEW, Uri.parse(text))
        intentB.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentB)
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        bindHeader()
        bindData()
        handleClick()
    }

    private fun bindHeader() {
        binding.inHeader.tvHeaderTitle.text = "Contact Us"
        binding.inHeader.cardSearch.isGone = true
        binding.inHeader.imgShipping.isInvisible = true
    }

    private fun bindData() {

        binding.recyclerViewContacts.adapter =
            ContactsAdapter(contactItems) {
                if (it.title == "info@broadwayfashion.ca") {
                    setEmail()
                } else if (it.title == "(905) 693 4883") {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+(905) 693 4883"))
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                } else {
                    val intentB = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                    intentB.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intentB)
                }
            }
    }

    private fun handleClick() {
        binding.inHeader.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun setEmail() {
        try {

            val mailTo = "mailto:" + "info@broadwayfashion.ca" +
                    "?&subject=" + Uri.encode("") +
                    "&body=" + Uri.encode("")
            val emailIntent = Intent(Intent.ACTION_VIEW)
            emailIntent.data = Uri.parse(mailTo)
            startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                "There is no email apps installed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val TAG = "ContactsActivity"
        fun newInstance(context: Context): Intent {
            return Intent(context, ContactsActivity::class.java)
        }
    }
}