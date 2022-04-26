package com.mobero.stockcontrol.util

import com.mobero.stockcontrol.model.Location
import com.mobero.stockcontrol.model.Product
import com.mobero.stockcontrol.model.ProductVariation
import com.mobero.stockcontrol.model.Stock

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

    fun searchStock(prvId: Int? = null, locId: Int? = null): ArrayList<Any> {
        val param: HashMap<String, Int> = HashMap()
        if (prvId != null) param["prv_id"] = prvId
        if (locId != null) param["loc_id"] = locId
        //TODO 在庫検索処理を記載する
        val stockList = ArrayList<Any>()
        for (i in 0..5) {
            val prv: ProductVariation? = getDummyProductVariation()
            val loc: Location? = getDummyLocation()
            if (prv == null) continue
            if (loc == null) continue
            val stock = Stock(prv.clientCode, prv.productCode, prv.productName, prv.prvId, prv.uniqueCode, prv.sizeName, prv.colorName, loc, (1..10).random())
            stockList.add(stock)
        }
        return stockList
    }

    private fun getDummyProductVariation(): ProductVariation? {
        val range = (1..3)
        val random = range.random()
        var dummyProductVariation: ProductVariation? = null
        when (random) {
            1 -> {
                dummyProductVariation = ProductVariation("1", "BLOOD", "ブラッドハウンドTシャツ", 1, "BLOOD001", "Sサイズ", "赤")
            }
            2 -> {
                dummyProductVariation = ProductVariation("1", "RACE", "レイスTシャツ", 2, "RACE001", "Sサイズ", "赤")
            }
            3 -> {
                dummyProductVariation = ProductVariation("1", "GIBRALTAL", "ジブラルタルTシャツ", 3, "GIBRALTAL001", "Sサイズ", "赤")
            }
        }
        return dummyProductVariation
    }

    private fun getDummyLocation(): Location? {
        val range = (1..3)
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