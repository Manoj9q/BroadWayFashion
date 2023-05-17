package com.app.broadwayfashion.view.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.FilterSortbyItemBinding
import com.app.broadwayfashion.utils.AppConfigurations


class FilterSizeAdapter(private val func: (String) -> Unit) :
    RecyclerView.Adapter<FilterSizeAdapter.ViewHolder>() {
    // Array of images

    var selected = ""
    fun setList(slist: List<String>) {
        list = slist as ArrayList<String>
        notifyDataSetChanged()
    }

    fun setSelectedItem(att: String) {
        var pos = listIds.indexOf(att)
        if (pos >= 0) {
            selected = list[pos]
        }
        notifyDataSetChanged()
    }

    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     //   selected = AppConfigurations.size
        val binding: FilterSortbyItemBinding =
            FilterSortbyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        holder.itemView.setOnClickListener {
            selected = list[position]
            Log.e("Manoj22:",selected)
            func(listIds[position])
            notifyDataSetChanged()
        }
        holder.binding.tvTitle.text = list[position]
        holder.binding.tvTitleSelected.text = list[position]
        Log.e("Manoj22:","$selected == ${list[position]}")
        if (selected == list[position]) {
            Log.e("Manoj22:","true")
            holder.binding.tvTitleSelected.text = list[position]
            holder.binding.tvTitleSelected.isVisible = true
            holder.binding.tvTitle.isGone = true


        } else {
            holder.binding.tvTitleSelected.isGone = true
            holder.binding.tvTitle.isVisible = true

        }


    }


    private var list = arrayListOf<String>(
        "XS",
        "S",
        "M",
        "L",
        "XL",
        "XXL",
        "XXXL"
    )

    private var listIds = arrayListOf<String>(
        "193",
        "47",
        "46",
        "45",
        "44",
        "43",
        "192"
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