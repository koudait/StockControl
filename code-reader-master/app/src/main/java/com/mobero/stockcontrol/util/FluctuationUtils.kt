package com.mobero.stockcontrol.util

import com.mobero.stockcontrol.model.Location
import com.mobero.stockcontrol.model.ProductVariation
import com.mobero.stockcontrol.model.StockChangeHistory

object FluctuationUtils {

    /**
     * 出納検索処理
     */

    fun searchStockChangeHistory(prvId: Int? = null, locId: Int? = null): ArrayList<Any> {
        val param: HashMap<String, Int> = HashMap()
        if (prvId != null) param["prv_id"] = prvId
        if (locId != null) param["loc_id"] = locId
        //TODO 出納検索処理を記載する
        val StockChangeHistoryList = ArrayList<Any>()
        for (i in 0..5) {
            val prv: ProductVariation? = getDummyProductVariation()
            val loc: Location? = getDummyLocation()
            if (prv == null) continue
            if (loc == null) continue
            val StockChangeHistory =StockChangeHistory(prv.clientCode, prv.productCode, prv.productName, prv.prvId, prv.uniqueCode, prv.sizeName, prv.colorName, loc, (1..10).random(), (1..10).random(), (1..10).random(), "西暦年月日")
            StockChangeHistoryList.add(StockChangeHistory)
        }
        return StockChangeHistoryList
    }

    private fun getDummyStockChangeHistoryVariation(): StockChangeHistory? {
        val range = (1..3)
        val random = range.random()
        var dummyStockChangeHistoryVariation: StockChangeHistory? = null
        when (random) {
            1 -> {
                dummyStockChangeHistoryVariation = StockChangeHistory("1", "BLOOD", "ブラッドハウンドTシャツ", 1, "BLOOD001", "Sサイズ", "赤",null,1,0,0,"2022年1月1日")
            }
            2 -> {
                dummyStockChangeHistoryVariation = StockChangeHistory("1", "RACE", "レイスTシャツ", 2, "RACE001", "Sサイズ", "赤",null,100,50,0,"2022年1月2日")
            }
            3 -> {
                dummyStockChangeHistoryVariation = StockChangeHistory("1", "GIBRALTAL", "ジブラルタルTシャツ", 3, "GIBRALTAL001", "Sサイズ", "赤",null,2,0,2,"2022年1月3日")
            }
        }
        return dummyStockChangeHistoryVariation
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