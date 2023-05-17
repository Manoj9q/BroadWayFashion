package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.MyCartItemBinding
import com.app.broadwayfashion.model.cart.Item
import com.app.broadwayfashion.model.cart.Variation
import com.app.broadwayfashion.utils.loadImageFromUrl


class CustomerCardAdapter(private var list: List<Item>, private val func: (Pair<Int, Item>) -> Unit) :
    RecyclerView.Adapter<CustomerCardAdapter.ViewHolder>() {
    // Array of images


    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MyCartItemBinding =
            MyCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var obj = list[position]
        holder.binding.apply {
            tvProductTitle.text = obj.title
            tvQuantity.text = obj.quantity?.value?.toString()
            tvQuantity.text = "Quantity: ${obj.quantity?.value?.toString()}"

            if(obj.meta?.variation is ArrayList<*>){
                if(!(obj.meta?.variation as ArrayList<*>).isNullOrEmpty()) {
                    var variation = (obj.meta?.variation as ArrayList<*>)[0] as Variation
                    tvSize.text = "Size: ${variation.size}"
                }
                else
                    tvSize.text = "Size: NA"

            }
            else{
                if(obj.meta?.variation is Variation) {
                    var variation = obj.meta?.variation as Variation
                    tvSize.text = "Size: ${variation?.size}"
                }
                else
                    tvSize.text = "Size: NA"

            }
            tvPrice.text = "$ ${obj.price!!.toDouble()/100}"
            if (obj.featuredImage != null)
                imgLogo.loadImageFromUrl(obj.featuredImage)

            tvEdit.setOnClickListener {
                func.invoke(Pair(1,obj))
            }
            tvRemoveFromCart.setOnClickListener {
                func.invoke(Pair(2,obj))
            }
        }


    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return list.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: MyCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}