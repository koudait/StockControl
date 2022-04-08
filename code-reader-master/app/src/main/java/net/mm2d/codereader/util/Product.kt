package net.mm2d.codereader.util

import net.mm2d.codereader.model.Product
import net.mm2d.codereader.model.ProductVariation

object Product {
    fun productSearch(code: String): ProductVariation {
        val dummyProduct = Product("1", "BLOOD", "ブラッドハウンドTシャツ")
        return ProductVariation("BLOOD001", dummyProduct, "Sサイズ", "赤")
    }
}