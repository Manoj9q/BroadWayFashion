package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.R
import com.app.broadwayfashion.databinding.HomePagerItemBinding


internal class HomePagerAdapter(private val func: (String) -> Unit) :
    RecyclerView.Adapter<HomePagerAdapter.ViewHolder>() {
    // Array of images
    // Adding images from drawable folder
    private val titleList = arrayListOf(
        "Women's Fall/ \nWinter Collection", "Men's Legend/ \n" +
                "Same Collection", "Kid's Royal/ \n" +
                "New Collection"
    )


    private var imageList = arrayListOf(
        R.drawable.winter_collection,
        R.drawable.mens_legend_collection,
        R.drawable.kids_royal_new_collection
    )

    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: HomePagerItemBinding =
            HomePagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview

        holder.binding.tvTitle.text = titleList[position]

        holder.binding.imgBackGround.setImageResource(imageList[position])

        holder.binding.tvDesc.setOnClickListener {
            if (position == 0)
                func.invoke("Women")
            else
                func.invoke("Men")

        }

    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return titleList.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: HomePagerItemBinding) : RecyclerView.ViewHolder(binding.root) {


    }
}