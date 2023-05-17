package com.app.broadwayfashion.view.adapters



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.MyCartItemBinding
import com.app.broadwayfashion.databinding.MySavedAddressItemBinding


class MyAddressAdapter :  RecyclerView.Adapter<MyAddressAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MySavedAddressItemBinding =
            MySavedAddressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview



    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return 3
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: MySavedAddressItemBinding) :
        RecyclerView.ViewHolder(binding.root) {



    }
}