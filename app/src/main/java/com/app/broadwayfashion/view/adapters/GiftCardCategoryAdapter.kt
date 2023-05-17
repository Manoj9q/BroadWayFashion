package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.CardCategoriesItemBinding
import com.app.broadwayfashion.databinding.MyCartItemBinding


class GiftCardCategoryAdapter : RecyclerView.Adapter<GiftCardCategoryAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CardCategoriesItemBinding =
            CardCategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    class ViewHolder(val binding: CardCategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}