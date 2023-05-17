package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.R
import com.app.broadwayfashion.databinding.MyOrderItemBinding
import com.app.broadwayfashion.model.order.LineItem
import com.app.broadwayfashion.utils.loadImageFromUrl
import com.app.broadwayfashion.utils.setCustomColor


class MyOrderAdapter(
    private var list: MutableList<LineItem>,
    private val func: (LineItem) -> Unit
) :
    RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MyOrderItemBinding =
            MyOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        var obj = list[position]
        holder.itemView.setOnClickListener {
            func.invoke(obj)
        }
        holder.binding.apply {
            tvStatus.isVisible = true
            tvProductName.text = obj.name
            tvStatus.text = obj.status
            tvQuantity.text = "Quantity: ${obj.quantity?.toString()}"

            tvPrice.text = "$ ${obj.price!!}"
            if (obj.image != null)
                imgProduct.loadImageFromUrl(obj.image?.src)
            tvSize.text = "Size: NA"

            var meta = obj.metaData?.find { it?.key == "pa_size" }
            if (meta != null) {
                tvSize.text = "Size: ${meta.displayValue}"
            }
            if (obj.status == "completed") {
                tvStatus.setCustomColor(R.color.filter_green)
            } else {
                tvStatus.setCustomColor(R.color.red_light)
            }

        }
    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return list.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: MyOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}