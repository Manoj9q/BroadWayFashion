package com.app.broadwayfashion.model

data class StoreItem(
    var title: String,
    var address: String,
    var mapUrl: String,
    var contact: String,
    var daysTime: String = "Mon-Fri: 10 a.m. - 9 p.m.\nSat: 10 a.m. - 6 p.m.\nSun: 11 a.m. - 6 p.m.",
    var image: Int
)