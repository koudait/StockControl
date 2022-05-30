package com.mobero.stockcontrol.util

import com.mobero.stockcontrol.model.*

object FluctuationUtils {

    /**
     * 出納検索処理
     */
    fun searchStockChangeHistory(prvId: Int? = null, locId: Int? = null): ArrayList<Any> {
        val param: HashMap<String, Int> = HashMap()
        if (prvId != null) param["prv_id"] = prvId
        if (locId != null) param["loc_id"] = locId
        //TODO 出納検索処理を記載する
        val stockChangeHistoryList = ArrayList<Any>()
        for (i in 0..5) {
            val prv: ProductVariation? = ProductUtils.getDummyProductVariation()
            val loc: Location? = getDummyLocation()
            if (prv == null) continue
            if (loc == null) continue
            val stockStatus:StockStatus = StockStatus(0, "良品")
            val stockType:StockType = StockType(0, "セル")
            val stockChangeHistory =StockChangeHistory(prv, Order(), stockStatus, stockType, loc, (1..10).random(), (1..10).random(), (1..10).random(), (1..10).random(), "西暦年月日")
            stockChangeHistoryList.add(stockChangeHistory)
        }
        return stockChangeHistoryList
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