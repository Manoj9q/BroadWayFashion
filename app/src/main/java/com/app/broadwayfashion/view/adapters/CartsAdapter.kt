package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.MyCartItemBinding


class CartsAdapter(private var size: Int = 3) : RecyclerView.Adapter<CartsAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MyCartItemBinding =
            MyCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview


    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: MyCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}