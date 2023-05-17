package com.app.broadwayfashion.view.adapters



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.TrackOrderItemBinding


class TrackAdapter(private var size: Int = 4) : RecyclerView.Adapter<TrackAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: TrackOrderItemBinding =
            TrackOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    class ViewHolder(val binding: TrackOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}