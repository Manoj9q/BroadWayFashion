package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.R
import com.app.broadwayfashion.databinding.OnBoardedItemBinding


internal class OnBoardingViewPagerAdapter :
    RecyclerView.Adapter<OnBoardingViewPagerAdapter.ViewHolder>() {
    // Array of images
    // Adding images from drawable folder
    private val titleList = arrayListOf("Latest Street \nStyle Fits" , "Trendy Outer Wears", "Get amazing discounts")
    private val descList = arrayListOf(
        "Lorem ipsum dolor sit amet consectetur. Quis purus purus massa in sit.",
        "Lorem ipsum dolor sit amet consectetur. Quis purus purus massa in sit.",
        "Lorem ipsum dolor sit amet consectetur. Quis purus purus massa in sit."

    )

    private var imageList = arrayListOf(
        R.drawable.on_board1,
        R.drawable.on_board2,
        R.drawable.on_board3
    )

    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: OnBoardedItemBinding =
            OnBoardedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview

        holder.binding.tvTitle.text = titleList[position]
        holder.binding.tvDesc.text = descList[position]
        holder.binding.imgBackGround.setImageResource(imageList[position])

    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return titleList.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: OnBoardedItemBinding) : RecyclerView.ViewHolder(binding.root) {


    }
}