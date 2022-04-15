package net.mm2d.codereader

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ListView
import net.mm2d.codereader.model.ProductVariation

open class ProductVariationListActivity(layoutId: Int) : BasicActivity(layoutId) {
    /**
     * リストビューのアダプター定義
     */
    lateinit var mProductVariationAdapter: ProductVariationAdapter

    /**
     * リストビューのリスト定義
     */
    lateinit var mPrvList: ArrayList<ProductVariation>

    /**
     * OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //region リスト及びアダプターの設定

        // Listの初期化
        mPrvList = arrayListOf()
        // CustomAdapterの生成と設定
        mProductVariationAdapter = ProductVariationAdapter(this, mPrvList, object : IncrementButtonClickListener{
            override fun onIncrementButtonClick(item: ProductVariation) {
                if (item.scanNum == 0) {
                    // 削除確認ダイアログを表示
                    AlertDialog.Builder(this@ProductVariationListActivity)
                        .setTitle(R.string.alert_title_confirm)
                        .setMessage(R.string.alert_message_working_back)
                        .setPositiveButton("OK") { _, _ ->
                            mPrvList.remove(item)
                            mProductVariationAdapter.notifyDataSetChanged()
                        }
                        .setNegativeButton("Cancel") { _, _ ->
                            item.scanNum++
                            mProductVariationAdapter.notifyDataSetChanged()
                        }
                        .show()
                }
            }
        })

        // リストビューの設定
        findViewById<ListView>(R.id.list_view).adapter = mProductVariationAdapter

        //endregion
    }

    /**
     * 商品追加
     *
     * @param prv 商品バリエーション
     */
    fun addProduct(prv: ProductVariation) {
        var isExist = false
        mPrvList.forEach {
            if (it.uniqueCode == prv.uniqueCode) {
                // 存在した場合はインクリメント
                it.scanNum++
                isExist = true
                return@forEach
            }
        }
        // 存在しない場合はリストに追加
        if (!isExist) {
            mPrvList.add(prv)
            prv.scanNum = 1
        }
    }
}