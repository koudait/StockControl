package net.mm2d.codereader.util

import net.mm2d.codereader.model.Product
import net.mm2d.codereader.model.ProductVariation

object Product {

    /**
     * 商品検索処理
     *
     * @param code 商品コード
     * @return ProductVariation
     */
    fun productSearch(code: String): ProductVariation {
        val range = (1..3)
        val random = range.random()
        val dummyProduct:Product
        val dummyProductVariation:ProductVariation
        when (random) {
            1 -> {
                dummyProduct = Product("1", "BLOOD", "ブラッドハウンドTシャツ")
                dummyProductVariation = ProductVariation("BLOOD001", dummyProduct, "Sサイズ", "赤")
            }
            2 -> {
                dummyProduct = Product("1", "RACE", "レイスTシャツ")
                dummyProductVariation = ProductVariation("RACE001", dummyProduct, "Sサイズ", "赤")
            }
            else -> {
                dummyProduct = Product("1", "GIBRALTAL", "ジブラルタルTシャツ")
                dummyProductVariation = ProductVariation("GIBRALTAL001", dummyProduct, "Sサイズ", "赤")
            }
        }
        return dummyProductVariation
        //TODO 商品検索処理を記載する。
    }
}