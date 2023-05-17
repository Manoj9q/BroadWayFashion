package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.HotWinterItemBinding
import com.app.broadwayfashion.model.NewArrivalItem
import com.app.broadwayfashion.utils.loadImageFromUrl


internal class HotWinterAdapter(
    private val list: MutableList<NewArrivalItem>,
    private val func: (Int) -> Unit
) :
    RecyclerView.Adapter<HotWinterAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: HotWinterItemBinding =
            HotWinterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        var obj = list[position]
        holder.binding.tvDesc.text = obj.desc
        holder.binding.imgPic.setImageResource(obj.image)
        //  holder.binding.imgPic.loadImageFromUrl(obj.sourceImage)

        holder.itemView.setOnClickListener {
            func.invoke(obj.id)
        }

    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return list.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: HotWinterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}