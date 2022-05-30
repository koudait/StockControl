package com.mobero.stockcontrol.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.mobero.stockcontrol.R
import com.mobero.stockcontrol.model.ProductVariation
import com.mobero.stockcontrol.model.Stock

class ProductVariationAdapter(context: Context, private var mList: List<Any>, private var incrementButtonClickListener: IncrementButtonClickListener? = null, private var isScan: Boolean = false) : ArrayAdapter<Any>(context, 0, mList) {

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView


        // レイアウトの設定
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_item, parent, false)
        }

        val prv:ProductVariation
        // Productの取得
        if (mList[position] is Stock) {
            prv = (mList[position] as Stock).productVariation
        } else if(mList[position] is ProductVariation) {
            prv = mList[position] as ProductVariation
        } else {
            return view!!
        }

        // 各Viewの設定
        view?.findViewById<TextView>(R.id.name)?.text = prv.prd.productName
        view?.findViewById<TextView>(R.id.productCode)?.text = prv.prd.productCode
        view?.findViewById<TextView>(R.id.uniqueCode)?.text = prv.uniqueCode
        view?.findViewById<TextView>(R.id.color)?.text = prv.colorName
        view?.findViewById<TextView>(R.id.size)?.text = prv.sizeName

        val num = view?.findViewById<TextView>(R.id.countView)
        num?.text = prv.scanNum.toString()

        // マイナスボタンのクリックイベントを設定
        (view?.findViewById<Button>(R.id.btnSub))?.setOnClickListener {
            prv.scanNum--
            this@ProductVariationAdapter.notifyDataSetChanged()
            incrementButtonClickListener?.onIncrementButtonClick(prv)
        }
        // プラスボタンのクリックイベントを設定
        (view?.findViewById<Button>(R.id.btnAdd))?.setOnClickListener {
            prv.scanNum++
            this@ProductVariationAdapter.notifyDataSetChanged()
            incrementButtonClickListener?.onIncrementButtonClick(prv)
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