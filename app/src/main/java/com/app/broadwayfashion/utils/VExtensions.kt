package com.app.broadwayfashion.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources.Theme
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.ThemeUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.app.broadwayfashion.model.response.ErrorResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.navigation.NavigationBarView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.hdodenhof.circleimageview.CircleImageView
import de.hdodenhof.circleimageview.R
import retrofit2.Response

var commonApiErrorMessage = "Something went wrong"

public fun NavigationBarView.setupWithNavController(navController: NavController) {
    NavigationUI.setupWithNavController(this, navController)
}

inline fun <reified T : java.io.Serializable> Intent.getCustomSerializableExtra(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

fun AppCompatImageView.loadImageFromLocalWithPlaceHolder(photoPath: String?, placeholder: Int) {
    if (photoPath == null || photoPath.isBlank())
        return
    Glide.with(context).load(photoPath).placeholder(placeholder).into(this)

}

fun AppCompatImageView.loadImageFromUrl(photoPath: String?) {
    if (photoPath == null || photoPath.isBlank())
        return
    Glide.with(context).load(photoPath).into(this)

}

fun AppCompatImageView.loadImageFromUrl(photoPath: String?, func: (Boolean) -> Unit) {
    if (photoPath == null || photoPath.isBlank())
        return
    Glide.with(context).load(photoPath).listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            func.invoke(false)
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            func.invoke(true)
            return false
        }
    }).into(this)


}

fun CircleImageView.loadImageFromUrl(photoPath: String?) {
    if (photoPath == null || photoPath.isBlank())
        return
    Glide.with(context).load(photoPath).into(this)

}

fun TextView.setCustomColor(color: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, color))
}

fun View.setCustomBackgroundTintList(color: Int) {
    this.backgroundTintList = this.context?.resources?.getColorStateList(color, null)
}


fun showCustomDialog(msg: String, context: Context, func: (Int) -> Unit) {
    AlertDialog.Builder(context)
        .setMessage(msg).setCancelable(false)
        .setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { dialog, which ->
                func.invoke(1)
            })
        .show()
}

fun errorResponse(response: Response<Any>): ErrorResponse? {
    val type = object : TypeToken<ErrorResponse>() {}.type
    return Gson().fromJson(response.errorBody()!!.charStream(), type)
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(message: String) {

    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun View.showKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.also {
        it.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.also {
        it.hideSoftInputFromWindow(applicationWindowToken, 0)
    }
}



