package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.StoreItemBinding
import com.app.broadwayfashion.model.StoreItem


class StoreAdapter(private val list: MutableList<StoreItem>, private val func: (String) -> Unit) :
    RecyclerView.Adapter<StoreAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: StoreItemBinding =
            StoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        var obj = list[position]
        holder.binding.apply {
            imgMap.setImageResource(obj.image)
            tvStoreName.text = obj.title
            tvAddress.text = obj.address
            tvContact.text = obj.contact
            tvTiming.text = obj.daysTime

        }
        holder.itemView.setOnClickListener {
            func.invoke(obj.mapUrl)
        }

    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return list.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: StoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}