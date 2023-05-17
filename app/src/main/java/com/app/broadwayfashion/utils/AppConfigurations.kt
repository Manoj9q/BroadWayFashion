package com.app.broadwayfashion.utils

import com.app.broadwayfashion.R
import com.app.broadwayfashion.model.CategoryItem
import com.app.broadwayfashion.model.HorizontalItem
import com.app.broadwayfashion.model.NavigationItem
import com.app.broadwayfashion.model.NewArrivalItem
import com.app.broadwayfashion.model.product.Image
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import java.util.TreeMap

object AppConfigurations {

    const val PRODUCT_ITEM_COUNT = 20
    var selectedImageList: List<Image> = ArrayList()

    var size = ""
    var color = ""
    var brand = ""
    var orderBy: String = "date"
    var order: String = "desc"
    var sortText: String = "Latest"

    fun resetFilter() {
        size = ""
        color = ""
        brand = ""
        orderBy = "date"
        order = "desc"
        sortText = "Latest"
    }

    fun getHashMapObj(): LinkedHashMap<String, String> {
        var map = LinkedHashMap<String, String>()
        if (!size.isNullOrEmpty()) {

            map["attribute"] = "pa_size"
            map["attribute_term"] = size
        }
        if (!color.isNullOrEmpty()) {
            map["attribute"] = "pa_colour"
            map["attribute_term"] = color
        }
        if (!brand.isNullOrEmpty()) {
            map["attribute"] = "pa_brand"
            map["attribute_term"] = brand
        }
        if (!orderBy.isNullOrEmpty()) {
            map["order"] = order
            map["orderby"] = orderBy
        }
        map["per_page"] = PRODUCT_ITEM_COUNT.toString()

        return map

    }


    var newHotItemList = arrayListOf<NewArrivalItem>(
        NewArrivalItem("Shop Mens Puffer Jackets", "$70", R.drawable.hot_pic_wimter1, id = 111),
        NewArrivalItem("Shop Womens Bombers", "$70", R.drawable.hot_pic_wimter2, id = 112),
        NewArrivalItem("Shop Mens Cargo Pants", "$70", R.drawable.hot_pic_wimter3, id = 69)
    )
    var newArrivalItemList = arrayListOf<NewArrivalItem>(
        NewArrivalItem("Project-X Paris-T..", "$70", R.drawable.ic_dress1, "Buy black Sweater"),
        NewArrivalItem(
            "HD-Puffy Shoulde..",
            "$50",
            R.drawable.ic_dress2,
            "Shop mens Puffer Jackets",
            "https://assets.ajio.com/medias/sys_master/root/20201121/6Nzm/5fb80fc4f997dd8c83a406ce/-473Wx593H-441105686-black-MODEL.jpg"
        ),
        NewArrivalItem(
            "NA-KD-Hoodi..",
            "$90",
            R.drawable.ic_dress3,
            "Shop mens Puffer Jackets",
            "https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T1/images/I/614jtLUPy3L._UX466_.jpg"
        ),
        NewArrivalItem(
            "Project-X Paris-T..",
            "$70",
            R.drawable.ic_dress1,
            "Shop mens Puffer Jackets",
            "https://n3.sdlcdn.com/imgs/j/n/d/98-Degree-North-Black-High-SDL669909358-1-cbfa7.jpeg"
        ),
        NewArrivalItem(
            "HD-Puffy Shoulde..",
            "$80",
            R.drawable.ic_dress2,
            "Shop mens Puffer Jackets",
            "https://n3.sdlcdn.com/imgs/j/n/d/98-Degree-North-Black-High-SDL669909358-1-cbfa7.jpeg"
        ),
        NewArrivalItem(
            "Project-X Paris-T..",
            "$75",
            R.drawable.ic_dress3,
            "Shop mens Puffer Jackets",
            "https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T1/images/I/614jtLUPy3L._UX466_.jpg"
        ),
        NewArrivalItem(
            "NA-KD-Hoodi..",
            "$90",
            R.drawable.ic_dress1,
            "Shop mens Puffer Jackets",
            "https://rukminim1.flixcart.com/image/832/832/jvqzo280/sweater/p/y/g/s-5415592-roadster-original-imafghfz78bet4pb.jpeg?q=70"
        ),
        NewArrivalItem(
            "Project-X Paris-T..",
            "$95",
            R.drawable.ic_dress2,
            "Shop mens Puffer Jackets",
            "https://n3.sdlcdn.com/imgs/j/n/d/98-Degree-North-Black-High-SDL669909358-1-cbfa7.jpeg"
        ),
        NewArrivalItem(
            "HD-Puffy Shoulde..",
            "$97",
            R.drawable.ic_dress3,
            "Women best Sweater",
            "https://assets.myntassets.com/dpr_1.5,q_60,w_400,c_limit,fl_progressive/assets/images/19798116/2022/9/9/bf5314fa-1208-4c4c-9945-9af4452ddc431662728283802-Computerised-Cable-Knit-Pattern-Sweater-291662728283275-1.jpg"
        ),
    )

    var horizontalList = arrayListOf<HorizontalItem>(
        HorizontalItem("Men", true),
        HorizontalItem("Women", false),
        HorizontalItem("Outwear", false),
        HorizontalItem("Sale", false),
        HorizontalItem("", false)
    )

    var categoriesList = arrayListOf<CategoryItem>(
        CategoryItem(id = 61, name = "Men"),
        CategoryItem(id = 69, name = "Women"),
        CategoryItem(id = 111, name = "Outwear", otherId = 112),
        CategoryItem(id = 2, name = "Sale"),
        CategoryItem(id = 0, name = "")
    )

    var outCategoriesList = arrayListOf<CategoryItem>(
        CategoryItem(id = 111, name = "Men"),
        CategoryItem(id = 112, name = "Women")/*,
        CategoryItem(id = 12345, name = "Kids"),*/
    )

    var brandImageList = arrayListOf<String>(
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/alpha-slider.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/boylondon-slider.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/champion-slider.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/elevenparis-slider.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2022/11/essentials.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/hotanddelicious-slider.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2022/03/kangol-slider_2.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/mooseknuckles-slider.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2022/03/nakd-slider.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/nobis-slider-150x150.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/pjs-slider-150x150.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/projectparis-slider.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/soiakyo-slider.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/sprayground-slider.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2020/10/superdry-slider.jpg",
        "https://www.broadwayfashion.ca/wp-content/uploads/2021/10/wood-pecker_thumbnail.jpg"
    )
}