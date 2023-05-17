package com.app.broadwayfashion.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.broadwayfashion.databinding.FilterBrandItemBinding
import com.app.broadwayfashion.model.BrandItem
import com.app.broadwayfashion.utils.AppConfigurations


class FilterBrandAdapter(private val func: (String) -> Unit) :
    RecyclerView.Adapter<FilterBrandAdapter.ViewHolder>() {
    // Array of images
    private var brandList = arrayListOf<BrandItem>()

    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FilterBrandItemBinding =
            FilterBrandItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        setBrandList()
        setSelectedItem(AppConfigurations.brand)
        return ViewHolder(binding)
    }

    private fun setSelectedItem(att: String) {
        var obj = brandList.find { it.id == att }
        if (obj != null) {
            resetSelectionAll()
            obj.isSelected = true
//            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // This will set the images in imageview
        var obj = brandList[position]
        holder.binding.tvTitle.text = obj.name
        holder.binding.chkBox.isChecked = obj.isSelected
        holder.binding.chkBox.setOnClickListener {
            if (!obj.isSelected) {
                resetSelectionAll()
                obj.isSelected = true
                func.invoke(obj.id)
                notifyDataSetChanged()

            }
        }
        /* holder.binding.chkBox.setOnCheckedChangeListener { _, b ->
             if (b) {
                 resetSelectionAll()
                 obj.isSelected = true
                 Handler(Looper.myLooper()!!).post {
                     notifyDataSetChanged()
                 }
             }
             //
         }
 */
    }

    private fun resetSelectionAll() {
        brandList.forEach {
            it.isSelected = false
        }
    }

    private fun setBrandList() {
        for (i in 0 until list.size) {
            brandList.add(BrandItem(list[i], listIds[i]))
        }
    }

    private var list = arrayListOf<String>(
        "Alpha Style",
        "Black Keys",
        "Casio",
        "Champion",
        "Christian Paul",
        "Eleven Paris",
        "FOX THE LABEL",
        "Ice Cream",
        "NA-KD",
        "Neon Denim",
        "Noize",
        "Paper Planes",
        "Point Zero",
        "Reason",
        "Roberto Cavalli",
        "Woodpecker",
        "ZANEROBE",
    )
    private var listIds = arrayListOf<String>(
        "984",
        "799",
        "403",
        "778",
        "391",
        "795",
        "788",
        "791",
        "983",
        "980",
        "858",
        "810",
        "852",
        "965",
        "794",
        "989",
        "796"
    )

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return list.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(val binding: FilterBrandItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}