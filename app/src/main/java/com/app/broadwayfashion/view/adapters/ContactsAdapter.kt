package com.app.broadwayfashion.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.ContactUsItemBinding
import com.app.broadwayfashion.model.NavigationItem


class ContactsAdapter(
    private val navigationList: MutableList<NavigationItem>,
    private val func: (NavigationItem) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ContactUsItemBinding.inflate(LayoutInflater.from(parent.context)))
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

        holder.itemView.setOnClickListener {
            func.invoke(obj)
        }

    }


    /** @returns List Size */
    override fun getItemCount() = navigationList.size

    inner class ViewHolder(val binding: ContactUsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}