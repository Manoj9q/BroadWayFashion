package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.R
import com.app.broadwayfashion.databinding.NavigationItemBinding
import com.app.broadwayfashion.model.NavigationItem


class NavigationAdapter(
    private val navigationList: ArrayList<NavigationItem>,
    private val func: (NavigationItem) -> Unit
) : RecyclerView.Adapter<NavigationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NavigationItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * On bind view holder
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var obj = navigationList[position]
        holder.binding.tvTitle.text = obj.title
        holder.binding.imgIcon.setImageResource(obj.icon)

        if (position >= navigationList.size - 1) {
            holder.binding.viewSep.isVisible = true
        } else
            holder.binding.viewSep.isVisible = true

        holder.itemView.setOnClickListener {
            func.invoke(obj)
        }

    }


    /** @returns List Size */
    override fun getItemCount() = navigationList.size

    inner class ViewHolder(val binding: NavigationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}