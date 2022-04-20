package net.mm2d.codereader.model

open class ProductVariation(var prvId: Int, var uniqueCode: String, var product: Product, var sizeName: String, var colorName: String, var scanIndex: Int = 0) {
    public var scanNum = 0;
}