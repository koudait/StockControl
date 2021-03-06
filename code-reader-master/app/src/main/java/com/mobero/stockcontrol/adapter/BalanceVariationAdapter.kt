package com.mobero.stockcontrol.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.mobero.stockcontrol.R
import com.mobero.stockcontrol.model.ProductVariation
import com.mobero.stockcontrol.model.StockChangeHistory

class StockChangeHistoryVariationAdapter(context: Context, private var mList: List<Any>,  private var isScan: Boolean = false) : ArrayAdapter<Any>(context, 0, mList) {

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        // Productの取得
        val prv: ProductVariation = mList[position] as ProductVariation

        // StockChangeHistoryの取得
        val StockChangeHistory: StockChangeHistory = mList[position] as StockChangeHistory

        // レイアウトの設定
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_stock_change_history, parent, false)
        }

        // 各Viewの設定
        view?.findViewById<TextView>(R.id.fluctuatingDate)?.text = StockChangeHistory.changeDate
        view?.findViewById<TextView>(R.id.receivingCount)?.text = StockChangeHistory.receivingCount.toString()
        view?.findViewById<TextView>(R.id.shippingCount)?.text = StockChangeHistory.shippingCount.toString()
        view?.findViewById<TextView>(R.id.name)?.text = prv.prd.productName
        view?.findViewById<TextView>(R.id.productCode)?.text = prv.prd.productCode
        view?.findViewById<TextView>(R.id.uniqueCode)?.text = prv.uniqueCode
        view?.findViewById<TextView>(R.id.color)?.text = prv.colorName
        view?.findViewById<TextView>(R.id.size)?.text = prv.sizeName

        val num = view?.findViewById<TextView>(R.id.countView)
        num?.text = prv.scanNum.toString()

        return view!!
    }

}
