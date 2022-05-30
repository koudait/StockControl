package com.mobero.stockcontrol.model

open class Stock(
    var productVariation:ProductVariation,
    var stockStatus: StockStatus,
    var stockType: StockType,
    var loc: Location?,
    var stockNum: Int
    )

