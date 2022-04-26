package net.mm2d.codereader.model

open class ProductVariation(clientCode: String, productCode: String, productName: String, var prvId: Int, var uniqueCode: String, var sizeName: String, var colorName: String): Product(clientCode, productCode, productName) {
    var scanIndex: Int = 0
    var scanNum = 0
}