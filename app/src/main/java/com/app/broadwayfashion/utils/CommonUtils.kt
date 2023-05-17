package com.app.broadwayfashion.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


object CommonUtils {
    var isAppOpen: Int? = null

    fun isValidPasswordFormat(password: String): Boolean {
        return password.length > 4
/*        val passwordREGEX = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$"
        );
        return passwordREGEX.matcher(password).matches()*/
    }

    fun getAddToCartUserId(preferences: AppSharePreferences): String {
        var id = preferences.getString(USER_SELF_ID, "")
        if (id.isNullOrEmpty()) {
            id = preferences.getString(GUEST_ADD_TO_CART, "")
            if (id.isNullOrEmpty()) {
                id = System.currentTimeMillis().toString()
                preferences.setString(GUEST_ADD_TO_CART, id)
            }
            return id

        } else
            return id
    }
    fun openMapMapMapApp(url:String, context: Context){
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun getLocalTimeZoneDate(format: String = "MMM dd yyyy", strDate: String): String {
        if (strDate.isNullOrEmpty())
            return ""
        return try {
            var formatUtc = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

            var formatLocal = SimpleDateFormat(format, Locale.getDefault())

            val date =
                formatUtc.parse(strDate)
            formatLocal.format(date!!)
        } catch (ex: Exception) {
            strDate
        }

    }
}