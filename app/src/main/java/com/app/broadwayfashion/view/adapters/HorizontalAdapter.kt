package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.R
import com.app.broadwayfashion.databinding.HorizontalItemBinding
import com.app.broadwayfashion.model.HorizontalItem
import com.app.broadwayfashion.utils.setCustomColor
import com.app.broadwayfashion.view.activities.FilterActivity


internal class HorizontalAdapter(private val list: MutableList<HorizontalItem>) :
    RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: HorizontalItemBinding =
            HorizontalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        var obj = list[position]

        if (obj.name.isNullOrEmpty()) {
            holder.binding.imgFilter.isVisible = true
            holder.binding.tvTitle.isGone = true
            holder.binding.viewSelected.isGone = true
            holder.binding.imgFilter.setOnClickListener {
                holder.binding.imgFilter.context.startActivity(FilterActivity.newInstance(holder.binding.imgFilter.context))
            }

        } else {
            holder.binding.tvTitle.setOnClickListener {
                if (!obj.isSelected) {
                    resetAll()
                    obj.isSelected = true
                    notifyDataSetChanged()
                }
            }
            holder.binding.tvTitle.text = obj.name
            if (obj.isSelected) {
                holder.binding.viewSelected.isVisible = true
                holder.binding.tvTitle.setCustomColor(R.color.app_theme_color)
            } else {
                holder.binding.viewSelected.isGone = true
                holder.binding.tvTitle.setCustomColor(R.color.light_black)
            }
        }

    }

    private fun resetAll() {
        list.forEach {
            it.isSelected = false
        }
    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return list.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: HorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}