package com.mobero.stockcontrol.model

open class Stock(clientCode: String, productCode: String, productName: String, prvId: Int, uniqueCode: String, sizeName: String, colorName: String, var loc: Location?, var stockNum: Int): ProductVariation(clientCode, productCode, productName, prvId, uniqueCode, sizeName, colorName)