package com.app.broadwayfashion.view.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings.ZoomDensity
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.ProductImageItemBinding
import com.app.broadwayfashion.model.product.Image
import com.app.broadwayfashion.utils.AppConfigurations
import com.app.broadwayfashion.utils.loadImageFromUrl
import com.app.broadwayfashion.view.activities.ProductImageActivity

class ProductImagePagerAdapter(
    private val imageList: MutableList<Image>,
    private val isClickable: Boolean = false
) :
    RecyclerView.Adapter<ProductImagePagerAdapter.ViewHolder>() {


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ProductImageItemBinding =
            ProductImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview

        holder.binding.imgProduct.loadImageFromUrl(imageList[position].src) {
            holder.binding.progress.isGone = true
        }

        if(isClickable) {
            holder.binding.imgProduct.setOnClickListener {
                AppConfigurations.selectedImageList = imageList
                holder.binding.imgProduct.context.startActivity(
                    Intent(
                        ProductImageActivity.newInstance(
                            holder.binding.imgProduct.context,
                            position
                        )
                    )
                )
            }
        }
        else{

        }

    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return imageList.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: ProductImageItemBinding) : RecyclerView.ViewHolder(binding.root) {


    }
}