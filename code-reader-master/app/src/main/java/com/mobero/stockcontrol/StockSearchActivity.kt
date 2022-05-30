package com.mobero.stockcontrol

import android.os.Bundle
import android.widget.Toast
import com.mobero.stockcontrol.adapter.ProductVariationAdapter
import com.mobero.stockcontrol.model.Location
import com.mobero.stockcontrol.model.ProductVariation
import com.mobero.stockcontrol.model.Stock
import com.mobero.stockcontrol.util.ProductUtils

class StockSearchActivity// 削除確認ダイアログを表示
    : ProductVariationListActivity(layoutId = R.layout.activity_stock_search), ProductVariationListActivity.IListListener, ProductVariationListActivity.IStockListener {

    /**
     * OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        mAdapter = ProductVariationAdapter(this, mList!!, isScan = false)

        super.onCreate(savedInstanceState)

        setListListener(this)
        setStockListener(this)
    }

    /**
     * リストアイテム削除キャンセル時のイベント
     */
    override fun onListItemDeleteCancel(item: Any) {
    }

    /**
     * リスト追加時のイベント
     */
    override fun onListAdded(item: Any) {
    }

    override fun onSearch(code: String) {

        val prv: ProductVariation? = ProductUtils.searchProductVariation(code)
        val stockList: ArrayList<Any>
        if (prv != null) {
            // 商品がマッチした場合商品コードによる検索とみなす
            stockList = ProductUtils.searchStock(prvId = prv.prvId)
            setList(stockList)
            mAdapter!!.notifyDataSetChanged()
        } else {
            val loc: Location? = ProductUtils.searchLocation(code)
            if (loc != null) {
                // ロケコードがマッチした場合ロケコードによる検索とみなす
                stockList = ProductUtils.searchStock(locId = loc.locId)
                setList(stockList)
                mAdapter!!.notifyDataSetChanged()
            } else {
                Toast.makeText(
                    this,
                    R.string.alert_message_product_and_location_not_found,
                    Toast.LENGTH_LONG
                ).show()
                soundPool.play(soundAlert, 1.0f, 1.0f, 0, 0, 1.0f)
            }
        }
    }

    override fun onStockSearchSuccess(stock: Stock) {
    }
}