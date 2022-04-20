package net.mm2d.codereader.util

import net.mm2d.codereader.model.Location
import net.mm2d.codereader.model.Product
import net.mm2d.codereader.model.ProductVariation
import net.mm2d.codereader.model.Stock

object ProductUtils {

    /**
     * 商品検索処理
     *
     * @param code 商品コード
     * @return ProductVariation
     */
    fun searchProductVariation(code: String): ProductVariation? {
        return getDummyProductVariation()
        //TODO 商品検索処理を記載する。
    }

    fun searchLocation(locCode: String): Location? {
        return getDummyLocation()
        //TODO ロケ検索処理を記載する
    }

    fun searchStock(prvId: Int, locId: Int): Stock? {
        val param: HashMap<String, Int> = HashMap()
        param["prv_id"] = prvId
        param["loc_id"] = locId
        //TODO 在庫検索処理を記載する
        val prv: ProductVariation = getDummyProductVariation() ?: return null
        val loc: Location = getDummyLocation() ?: return null
        return Stock(prv, loc, (1..10).random())
    }

    private fun getDummyProductVariation(): ProductVariation? {
        val range = (1..4)
        val random = range.random()
        val dummyProduct:Product
        var dummyProductVariation: ProductVariation? = null
        when (random) {
            1 -> {
                dummyProduct = Product("1", "BLOOD", "ブラッドハウンドTシャツ")
                dummyProductVariation = ProductVariation(1, "BLOOD001", dummyProduct, "Sサイズ", "赤")
            }
            2 -> {
                dummyProduct = Product("1", "RACE", "レイスTシャツ")
                dummyProductVariation = ProductVariation(2, "RACE001", dummyProduct, "Sサイズ", "赤")
            }
            3 -> {
                dummyProduct = Product("1", "GIBRALTAL", "ジブラルタルTシャツ")
                dummyProductVariation = ProductVariation(3, "GIBRALTAL001", dummyProduct, "Sサイズ", "赤")
            }
        }
        return dummyProductVariation
    }

    private fun getDummyLocation(): Location? {
        val range = (1..4)
        val random = range.random()

        var dummyLocation: Location? = null
        when (random) {
            1 -> {
                dummyLocation = Location(1, "01-01-01")
            }
            2 -> {
                dummyLocation = Location(2, "01-01-02")
            }
            3 -> {
                dummyLocation = Location(0, null)
            }
        }
        return dummyLocation
    }
}