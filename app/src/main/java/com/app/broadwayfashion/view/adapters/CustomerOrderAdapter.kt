package com.app.broadwayfashion.view.adapters



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.MyOrderItemBinding
import com.app.broadwayfashion.model.cart.Item
import com.app.broadwayfashion.model.order.OrderResponseItem
import com.app.broadwayfashion.utils.loadImageFromUrl


class CustomerOrderAdapter(private var list: List<OrderResponseItem>, private val func: (Pair<Int, Item>) -> Unit) :
    RecyclerView.Adapter<CustomerOrderAdapter.ViewHolder>() {
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
        holder.binding.apply {
            tvStatus.isGone = true

         /*   tvProductName.text = obj.name
            tvQuantity.text = "Quantity: ${obj.quantity?.value?.toString()}"
            tvSize.text = "Size: ${obj.meta?.variation?.size}"
            tvPrice.text = "$ ${obj.price}"
            if (obj.featuredImage != null)
                imgProduct.loadImageFromUrl(obj.featuredImage)*/
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