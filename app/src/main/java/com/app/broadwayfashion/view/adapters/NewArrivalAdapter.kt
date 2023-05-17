package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.NewArrivalItemHomeBinding
import com.app.broadwayfashion.model.NewArrivalItem


internal class NewArrivalAdapter(

    private val list: MutableList<NewArrivalItem>,

    private val isShipping: Boolean = false,
    private val func: (NewArrivalItem) -> Unit
) :
    RecyclerView.Adapter<NewArrivalAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: NewArrivalItemHomeBinding =
            NewArrivalItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, isShipping)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        var obj = list[position]

        holder.binding.tvDesc.text = ""
        holder.binding.tvPriceOld.text =""
       // holder.binding.imgPic.setImageResource(obj.image)
        holder.itemView.setOnClickListener {
            func.invoke(obj)
        }


    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return list.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: NewArrivalItemHomeBinding, private val isShipping: Boolean) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            if (isShipping) {
                binding.imgShipping.isVisible = true
            } else
                binding.imgShipping.isGone = true
        }


    }
}