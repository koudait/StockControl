package net.mm2d.codereader

import android.os.Bundle
import androidx.recyclerview.widget.AsyncListDiffer
import net.mm2d.codereader.model.ProductVariation

open class ScanProductVariationActivity(layoutId: Int): ProductVariationListActivity(layoutId, isScan = true), ProductVariationListActivity.IProductVariationListener, ProductVariationListActivity.IListListener {
    /**
     * OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListListener(this)
        setProductVariationListener(this)
    }

    override fun onProductVariationSearchSuccess(prv: ProductVariation) {
    }

    override fun onProductVariationExisted(existedPrv: ProductVariation) {
        existedPrv.scanNum++
    }

    override fun onListItemDeleteCancel(item: Any) {
        (item as ProductVariation).scanNum++
    }

    override fun onListAdded(item: Any) {
        (item as ProductVariation).scanNum++
    }
}