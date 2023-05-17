package com.app.broadwayfashion.model

data class NewArrivalItem(
    var title: String,
    var price: String,
    var image: Int,
    var desc: String = "Shop mens Puffer Jackets",
    var sourceImage: String = "",
    var id:Int=0
)