package net.mm2d.codereader

import android.os.Bundle
import net.mm2d.codereader.model.ProductVariation
import net.mm2d.codereader.model.Stock

class StockSearchActivity : ProductVariationListActivity(R.layout.activity_stock_search), ProductVariationListActivity.IProductVariationListener, ProductVariationListActivity.IListListener, ProductVariationListActivity.IStockListener {

    /**
     * OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListListener(this)
        setProductVariationListener(this)
        setStockListener(this)
    }

    override fun onProductVariationSearchSuccess(prv: ProductVariation) {
    }

    override fun onProductVariationExisted(existedPrv: ProductVariation) {
    }

    override fun onListItemDeleteCancel(item: Any) {
    }

    override fun onListAdded(item: Any) {
    }

    override fun onStockSearchSuccess(stock: Stock) {
    }
}