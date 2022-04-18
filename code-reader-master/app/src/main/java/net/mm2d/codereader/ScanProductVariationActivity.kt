package net.mm2d.codereader

import android.os.Bundle
import net.mm2d.codereader.model.ProductVariation

open class ScanProductVariationActivity(layoutId: Int): ProductVariationListActivity(layoutId), ProductVariationListActivity.IScanListener {
    /**
     * OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScanListener(this)


    }

    override fun onProductVariationSearchSuccess(prv: ProductVariation) {
        addProduct(prv)
    }

    override fun onProductVariationListItemDeleteCancel(prv: ProductVariation) {
        prv.scanNum++
    }

    override fun onProductVariationAddSuccess(prv: ProductVariation) {
        addProduct(prv)
    }

    override fun onExistProductVariationAdded(prv: ProductVariation) {
        prv.scanNum++
    }
}