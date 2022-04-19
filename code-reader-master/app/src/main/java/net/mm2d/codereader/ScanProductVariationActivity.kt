package net.mm2d.codereader

import android.os.Bundle
import net.mm2d.codereader.model.ProductVariation

open class ScanProductVariationActivity(layoutId: Int): ProductVariationListActivity(layoutId, isScan = true), ProductVariationListActivity.IScanListener {
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
        prv.scanNum++
    }

    override fun onProductVariationListAddSuccess(prv: ProductVariation) {
        prv.scanNum++
    }

    override fun onProductVariationExisted(existedPrv: ProductVariation) {
        existedPrv.scanNum++
    }
}