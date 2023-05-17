package com.app.broadwayfashion.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.app.broadwayfashion.R
import com.app.broadwayfashion.databinding.DialogUpdateQuantityBinding
import com.app.broadwayfashion.model.cart.Item

object DialogUtils {
    fun showCustomDialogYesNo(msg: String, context: Context, func: (Int) -> Unit) {
        android.app.AlertDialog.Builder(context)
            .setMessage(msg).setCancelable(false)
            .setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, _ ->
                    func.invoke(1)
                    dialog.dismiss()
                })
            .setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialog, _ ->
                    func.invoke(0)
                    dialog.dismiss()
                })
            .show()
    }

    fun updateQuantityDialog(activity: Activity, item: Item, func: (Int) -> Unit) {
        var binding = DialogUpdateQuantityBinding.inflate(activity.layoutInflater)
        val view = binding.root
        binding.tvTitle.text = item.title
        var quantity = item.quantity?.value ?: 0
        binding.tvQuantity.text = quantity.toString()


        binding.imgIncrement.setOnClickListener {
            if (quantity < 5) {
                quantity++
            }
            binding.tvQuantity.text = quantity.toString()
        }
        binding.imgDecrement.setOnClickListener {
            if (quantity > 1) {
                quantity--
            }
            binding.tvQuantity.text = quantity.toString()
        }

        val alert = AlertDialog.Builder(activity, R.style.MyDialog)
            .setView(view)
            .setCancelable(!false)
            .create()
        binding.tvUpdate.setOnClickListener {
            func.invoke(quantity)
            alert.dismiss()
        }
        binding.imgClose.setOnClickListener {
            alert.dismiss()
        }
        alert.show()

    }
}