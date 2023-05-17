package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.HomeBrandImageItemBinding
import com.app.broadwayfashion.utils.loadImageFromUrl


class BrandImageHomeAdapter(private var list: MutableList<String>) :
    RecyclerView.Adapter<BrandImageHomeAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: HomeBrandImageItemBinding =
            HomeBrandImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        holder.binding.imageView.loadImageFromUrl(list[position])


    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return list.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: HomeBrandImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}