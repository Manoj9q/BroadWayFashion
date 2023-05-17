package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.FilterSortbyItemBinding


class FilterSortByAdapter(private val func: (String) -> Unit) :
    RecyclerView.Adapter<FilterSortByAdapter.ViewHolder>() {
    // Array of images

    var selected = "Latest"
    fun setSelectedItem(str: String) {
        selected = str
        notifyDataSetChanged()
    }

    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FilterSortbyItemBinding =
            FilterSortbyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        holder.itemView.setOnClickListener {
            selected = list[position]
            func.invoke(selected)
            notifyDataSetChanged()
        }
        holder.binding.tvTitle.text = list[position]
        holder.binding.tvTitleSelected.text = list[position]

        if (selected == list[position]) {
            holder.binding.tvTitleSelected.isVisible = true
            holder.binding.tvTitle.isGone = true


        } else {
            holder.binding.tvTitleSelected.isGone = true
            holder.binding.tvTitle.isVisible = true

        }


    }

    private var list = arrayListOf<String>(
        "Popularity",
        "Average Rating",
        "Latest",
        "Price: High to Low",
        "Price: Low to High"
    )


    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return list.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: FilterSortbyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}