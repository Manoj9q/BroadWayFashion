package com.app.broadwayfashion.view.adapters


import android.graphics.Paint
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.NewArrivalItemHomeBinding
import com.app.broadwayfashion.model.product.ProductItem
import com.app.broadwayfashion.utils.loadImageFromUrl


class ProductItemAdapter(
    private val list: MutableList<ProductItem>,

    private val isShipping: Boolean = false,
    private val func: (ProductItem) -> Unit
) :
    RecyclerView.Adapter<ProductItemAdapter.ViewHolder>() {
    // Array of images

    fun setNewList(newList: MutableList<ProductItem>) {
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun getListCount() = list.size
    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: NewArrivalItemHomeBinding =
            NewArrivalItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, isShipping)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        var obj = list[position]
        holder.binding.tvDesc.text = obj.name
        if (obj.priceHtml.isNullOrEmpty())
            obj.priceHtml = "Out of Stock"

        var text = HtmlCompat.fromHtml(
            obj.priceHtml ?: "$${if (obj.onSale == true) obj.salePrice else obj.price}",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        //var text = holder.binding.tvPriceOld.text.toString()
        if (text.contains(" ")) {
            holder.binding.tvPriceOld.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            var breakText = text.split(" ")
            holder.binding.tvPriceOld.text = breakText[0]
            holder.binding.tvPriceNew.text = breakText[1]
        } else {
            holder.binding.tvPriceOld.text = ""
            holder.binding.tvPriceNew.text = text
        }

        if (!obj.images.isNullOrEmpty()) {
            holder.binding.imgPic.loadImageFromUrl(obj.images!![0]!!.src)
        }
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