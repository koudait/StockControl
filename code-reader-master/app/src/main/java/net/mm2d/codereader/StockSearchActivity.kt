package net.mm2d.codereader

import android.os.Bundle
import android.widget.Toast
import net.mm2d.codereader.adapter.ProductVariationAdapter
import net.mm2d.codereader.model.Location
import net.mm2d.codereader.model.ProductVariation
import net.mm2d.codereader.model.Stock
import net.mm2d.codereader.util.ProductUtils

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

    override fun onListItemDeleteCancel(item: Any) {
    }

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