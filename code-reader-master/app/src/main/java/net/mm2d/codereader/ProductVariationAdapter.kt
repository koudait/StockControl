package net.mm2d.codereader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import net.mm2d.codereader.model.ProductVariation

class ProductVariationAdapter(context: Context, private var mProductVariationList: List<ProductVariation>, private var incrementButtonClickListener: IncrementButtonClickListener) : ArrayAdapter<ProductVariation>(context, 0, mProductVariationList) {

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        // Productの取得
        val productVariation:ProductVariation = mProductVariationList[position]

        // レイアウトの設定
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_item, parent, false)
        }

        // 各Viewの設定
        view?.findViewById<TextView>(R.id.name)?.text = productVariation.product.productName
        view?.findViewById<TextView>(R.id.productCode)?.text = productVariation.product.productCode
        view?.findViewById<TextView>(R.id.uniqueCode)?.text = productVariation.uniqueCode
        view?.findViewById<TextView>(R.id.color)?.text = productVariation.colorName
        view?.findViewById<TextView>(R.id.size)?.text = productVariation.sizeName

        val num = view?.findViewById<TextView>(R.id.countView)
        num?.text = productVariation.scanNum.toString()

        // マイナスボタンのクリックイベントを設定
        (view?.findViewById<Button>(R.id.btnSub))?.setOnClickListener {
            productVariation.scanNum--
            this@ProductVariationAdapter.notifyDataSetChanged()
            incrementButtonClickListener.onIncrementButtonClick(productVariation)
        }
        // プラスボタンのクリックイベントを設定
        (view?.findViewById<Button>(R.id.btnAdd))?.setOnClickListener {
            productVariation.scanNum++
            this@ProductVariationAdapter.notifyDataSetChanged()
            incrementButtonClickListener.onIncrementButtonClick(productVariation)
        }
        return view!!
    }
}

interface IncrementButtonClickListener {
    fun onIncrementButtonClick(item: ProductVariation)
}