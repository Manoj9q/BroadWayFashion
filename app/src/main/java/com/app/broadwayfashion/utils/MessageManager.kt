package com.app.broadwayfashion.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MessageManager @Inject constructor(private val activity: Activity) {
    private val resources get() = activity.resources
    private val rootView get() = activity.findViewById<View>(android.R.id.content)

    fun showMessage(message: String) =
        showSnackBar(message)

    fun showMessage(@StringRes messageRes: Int) =
        showSnackBar(resources.getString(messageRes))

    fun showError(error: String? = null) =
        error?.let { showSnackBar(it) }

    fun showError(@StringRes errorRes: Int) =
        showSnackBar(resources.getString(errorRes))

    private fun showSnackBar(text: String) =
        Snackbar
            .make(rootView, text, Snackbar.LENGTH_LONG)
            .show()

    fun showCustomAlertDialog(msg: String, context: Context, func: (Int) -> Unit) {
        AlertDialog.Builder(context)
            .setMessage(msg).setCancelable(false)
            .setPositiveButton(
                "OK",
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                    func.invoke(1)
                })
            .show()
    }
    fun showCustomAlertDialogNeg(msg: String, context: Context, func: (Int) -> Unit) {
        AlertDialog.Builder(context)
            .setMessage(msg).setCancelable(false)
            .setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                    func.invoke(1)
                })
            .setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                    func.invoke(0)
                })
            .show()
    }
}
