package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.R
import com.app.broadwayfashion.databinding.ItemColorBinding
import com.app.broadwayfashion.model.ColorItem
import com.app.broadwayfashion.utils.setCustomBackgroundTintList


class ColorAdapter(private var list: MutableList<ColorItem>, private val func: (String) -> Unit) :
    RecyclerView.Adapter<ColorAdapter.ViewHolder>() {
    // Array of images

    fun setSelectedItem(string: String) {
        if(!string.isNullOrEmpty()) {
            var obj = list.find { it.attId == string.toInt() }
            if (obj != null) {
                reSetAll()
                obj.isSelected = true
            }
        }
    }


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemColorBinding =
            ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var obj = list[position]
        holder.binding.apply {
            viewColor.setCardBackgroundColor(obj.colorCode)
            //viewColor.setBackgroundColor(obj.colorCode)
            if (obj.isSelected) {
                imgSelected.isVisible = true
            } else {
                imgSelected.isGone = true
            }
            holder.binding.root.setOnClickListener {
                if (!obj.isSelected) {
                    reSetAll()
                    obj.isSelected = true
                    func.invoke(obj.attId.toString())
                    notifyDataSetChanged()
                }
            }
        }

    }

    private fun reSetAll() {
        list.forEach {
            it.isSelected = false
        }
    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return list.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}