package net.mm2d.codereader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import net.mm2d.codereader.model.ProductVariation

class CustomAdapter(context: Context, var mProductVariationList: List<ProductVariation>) : ArrayAdapter<ProductVariation>(context, 0, mProductVariationList) {

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
        val name = view?.findViewById<TextView>(R.id.name)
        name?.text = productVariation.product.productName

        return view!!
    }
}