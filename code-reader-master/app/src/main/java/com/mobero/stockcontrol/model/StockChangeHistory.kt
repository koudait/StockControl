package com.mobero.stockcontrol.model

open class StockChangeHistory(
    prv:ProductVariation,
    var order: Order,
    var stockStatus: StockStatus,
    var stockType: StockType,
    var loc: Location,
    var receivingCount: Int,
    var shippingCount: Int,
    var totalNum: Int,
    var locNum: Int,
    var changeDate: String)