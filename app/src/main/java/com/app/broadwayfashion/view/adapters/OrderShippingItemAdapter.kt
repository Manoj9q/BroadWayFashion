package com.app.broadwayfashion.view.adapters




import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.FilterBrandItemBinding


class OrderShippingItemAdapter : RecyclerView.Adapter<OrderShippingItemAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FilterBrandItemBinding =
            FilterBrandItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        holder.binding.tvTitle.text = list[position]


    }

    private var list = arrayListOf<String>(
        "Shipping",
        "Free Shipping",
        "In-Store Pick Up - [Toronto Premium Outlets]",
        "Flat Rate (\$15)",
        "In-Store Pick Up - [CF Markville Mall]",
        "In Store Pickup - [CF Toronto Eaton Centre]"
    )

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return list.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: FilterBrandItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}