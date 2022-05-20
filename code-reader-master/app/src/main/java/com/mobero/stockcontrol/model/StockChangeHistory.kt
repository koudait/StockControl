package com.mobero.stockcontrol.model

open class StockChangeHistory(
    clientCode: String, productCode: String, productName: String, prvId: Int, uniqueCode: String, sizeName: String, colorName: String, loc: Location?, stockNum: Int,
    var receivingCount: Int,
    var shippingCount: Int,
    var fluctuatingDate: String): Stock(clientCode, productCode, productName, prvId, uniqueCode, sizeName, colorName,loc,stockNum) {
}