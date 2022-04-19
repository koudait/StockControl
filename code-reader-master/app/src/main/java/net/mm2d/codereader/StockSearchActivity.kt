package net.mm2d.codereader

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import net.mm2d.codereader.model.ProductVariation
import net.mm2d.codereader.util.ProductUtils
import kotlin.math.log

class StockSearchActivity : ProductVariationListActivity(R.layout.activity_stock_search), ProductVariationListActivity.IScanListener {

    /**
     * OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScanListener(this)
    }

    override fun onProductVariationSearchSuccess(prv: ProductVariation) {
    }

    override fun onProductVariationListItemDeleteCancel(prv: ProductVariation) {
    }

    override fun onProductVariationListAddSuccess(prv: ProductVariation) {
    }

    override fun onProductVariationExisted(existedPrv: ProductVariation) {
    }
}