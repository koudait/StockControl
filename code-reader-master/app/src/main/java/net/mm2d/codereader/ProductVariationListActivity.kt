package net.mm2d.codereader

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import net.mm2d.codereader.model.ProductVariation
import net.mm2d.codereader.model.Stock

open class ProductVariationListActivity(layoutId: Int, val isScan: Boolean =false) : BasicActivity(layoutId) {
    /**
     * リストビューのアダプター定義
     */
    lateinit var mProductVariationAdapter: ProductVariationAdapter

    /**
     * リストビューのリスト定義
     */
    lateinit var mList: ArrayList<Any>

    interface IListListener {
        /**
         * リストアイテムの削除がキャンセルされた場合に呼び出されるイベント
         */
        fun onListItemDeleteCancel(item: Any)

        /**
         * リストへの追加が成功した場合に呼ばれるイベント
         */
        fun onListAdded(item: Any)
    }

    interface IProductVariationListener {
        /**
         * 検索処理
         */
        fun onSearch(prvCode: String)
        /**
         * 検索処理に成功した場合に呼ばれるイベント
         */
        fun onProductVariationSearchSuccess(prv: ProductVariation)

        /**
         * 商品追加時に商品が既に存在した場合に呼ばれるイベント
         */
        fun onProductVariationExisted(existedPrv: ProductVariation)
    }

    interface IStockListener {
        /**
         * 検索処理
         */
        fun onSearch(code: String)
        /**
         * 検索処理に成功した場合に呼ばれるイベント
         */
        fun onStockSearchSuccess(stock: Stock)
    }

    private lateinit var productVariationListener: IProductVariationListener
    private lateinit var listListener: IListListener
    private lateinit var stockListener: IStockListener

    fun setProductVariationListener(productVariationListener: IProductVariationListener) {
        this.productVariationListener = productVariationListener
    }

    fun setListListener(listener: IListListener) {
        this.listListener = listener
    }

    fun setStockListener(listener: IStockListener) {
        this.stockListener = listener
    }
    /**
     * カメラアクティビティのイベント定義
     */
    private var cameraResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            fun(result: ActivityResult?) {
                if (result?.resultCode == RESULT_OK) {
                    val code = result.data?.getStringExtra("barcodeKEY").toString()
                    if (::productVariationListener.isInitialized) productVariationListener.onSearch(code)
                    if (::stockListener.isInitialized) stockListener.onSearch(code)
                }
            })

    /**
     * OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //region リスト及びアダプターの設定

        // Listの初期化
        mList = arrayListOf()
        // CustomAdapterの生成と設定
        mProductVariationAdapter = ProductVariationAdapter(this, mList, object : IncrementButtonClickListener{
            override fun onIncrementButtonClick(prv: ProductVariation) {
                if (prv.scanNum == 0) {
                    // 削除確認ダイアログを表示
                    AlertDialog.Builder(this@ProductVariationListActivity)
                        .setTitle(R.string.alert_title_confirm)
                        .setMessage(R.string.alert_message_working_back)
                        .setPositiveButton("OK") { _, _ ->
                            mList.remove(prv)
                            mProductVariationAdapter.notifyDataSetChanged()
                        }
                        .setNegativeButton("Cancel") { _, _ ->
                            listListener.onListItemDeleteCancel(prv)
                            mProductVariationAdapter.notifyDataSetChanged()
                        }
                        .show()
                }
            }
        }, isScan)

        // リストビューの設定
        findViewById<ListView>(R.id.list_view).adapter = mProductVariationAdapter

        //endregion

        //region 各種イベントの設定

        // カメラボタンを押下した際にカメラアクティビティを起動する
        val btnStart : Button = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            val intent = Intent(this,CameraScan::class.java)
            cameraResult.launch(intent)
        }

        //EditText入力イベント処理
        val searchTextBox = findViewById<EditText>(R.id.editSearch)
        searchTextBox.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                val code = searchTextBox.text.toString()
                if (::productVariationListener.isInitialized) productVariationListener.onSearch(code)
                if (::stockListener.isInitialized) stockListener.onSearch(code)
                view.text = ""
            }
            return@setOnEditorActionListener true
        }

        //endregion
    }

    fun setList(prvList: ArrayList<Any>) {
        //mList = prvList
        mList.clear()
        for (prv in prvList) {
            mList.add(prv)
        }
    }

    /**
     * 商品追加
     *
     * @param prv 商品バリエーション
     * @return
     */
    fun addProductVariation(prv: ProductVariation): ProductVariation? {
        mList.forEach {
            if (it is ProductVariation) {
                if (it.uniqueCode == prv.uniqueCode) {
                    // 存在した場合はリストに追加せず処理を終了する
                    return it
                }
            }
        }
        // 存在しない場合はリストに追加
        mList.add(prv)
        return null
    }
}