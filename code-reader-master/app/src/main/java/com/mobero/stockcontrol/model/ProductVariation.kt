package com.mobero.stockcontrol.model

open class ProductVariation(
    var prd: Product,
    var prvId: Int,
    var uniqueCode: String,
    var sizeCode:String,
    var sizeName: String,
    var colorCode:String,
    var colorName: String,
    var scanIndex:Int,
    var scanNum:Int)
