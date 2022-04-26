package net.mm2d.codereader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import net.mm2d.codereader.model.ProductVariation

class ProductVariationAdapter(context: Context, private var mList: List<Any>, private var incrementButtonClickListener: IncrementButtonClickListener, private var isScan: Boolean = false) : ArrayAdapter<Any>(context, 0, mList) {

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        // Productの取得
        val productVariation:ProductVariation = mList[position] as ProductVariation

        // レイアウトの設定
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_item, parent, false)
        }

        // 各Viewの設定
        view?.findViewById<TextView>(R.id.name)?.text = productVariation.productName
        view?.findViewById<TextView>(R.id.productCode)?.text = productVariation.productCode
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

        if (isScan) {
            view?.findViewById<Button>(R.id.btnAdd)?.visibility = View.VISIBLE
            view?.findViewById<Button>(R.id.btnSub)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<Button>(R.id.btnAdd)?.visibility = View.GONE
            view?.findViewById<Button>(R.id.btnSub)?.visibility = View.GONE
        }
        return view!!
    }
}

interface IncrementButtonClickListener {
    fun onIncrementButtonClick(prv: ProductVariation)
}