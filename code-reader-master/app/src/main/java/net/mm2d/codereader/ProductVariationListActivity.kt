package net.mm2d.codereader

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import net.mm2d.codereader.model.ProductVariation
import net.mm2d.codereader.util.ProductUtils

open class ProductVariationListActivity(layoutId: Int) : BasicActivity(layoutId) {
    /**
     * リストビューのアダプター定義
     */
    lateinit var mProductVariationAdapter: ProductVariationAdapter

    /**
     * リストビューのリスト定義
     */
    lateinit var mPrvList: ArrayList<ProductVariation>

    interface IScanListener {
        fun onProductVariationSearchSuccess(prv: ProductVariation)
        fun onProductVariationListItemDeleteCancel(prv: ProductVariation)
        fun onProductVariationAddSuccess(prv: ProductVariation)
        fun onExistProductVariationAdded(prv: ProductVariation)
    }

    private lateinit var scanListener: IScanListener
    fun setScanListener(scanListener: IScanListener) {
        this.scanListener = scanListener
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
                    val prv = ProductUtils.productVariationSearch(code)
                    if (prv == null) {
                        // 商品情報取得失敗時
                        Toast.makeText(
                            this,
                            R.string.alert_message_product_not_found,
                            Toast.LENGTH_LONG
                        ).show()
                        soundPool.play(soundError, 1.0f, 1.0f, 0, 0, 1.0f)
                    } else {
                        addProduct(prv)
                        scanListener.onProductVariationSearchSuccess(prv)
                        soundPool.play(soundScan, 1.0f, 1.0f, 0, 0, 1.0f)
                        // アダプターに反映
                        mProductVariationAdapter.notifyDataSetChanged()
                    }
                    // アダプターに反映
                    mProductVariationAdapter.notifyDataSetChanged()
                }
            })

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
            override fun onIncrementButtonClick(prv: ProductVariation) {
                if (prv.scanNum == 0) {
                    // 削除確認ダイアログを表示
                    AlertDialog.Builder(this@ProductVariationListActivity)
                        .setTitle(R.string.alert_title_confirm)
                        .setMessage(R.string.alert_message_working_back)
                        .setPositiveButton("OK") { _, _ ->
                            mPrvList.remove(prv)
                            mProductVariationAdapter.notifyDataSetChanged()
                        }
                        .setNegativeButton("Cancel") { _, _ ->
                            scanListener.onProductVariationListItemDeleteCancel(prv)
                            mProductVariationAdapter.notifyDataSetChanged()
                        }
                        .show()
                }
            }
        })

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
                //Edittextからの入力値を設定
                val code: String = searchTextBox.text.toString()
                //商品追加
                val prv = ProductUtils.productVariationSearch(code)
                if (prv == null) {
                    // 商品情報取得失敗時
                    Toast.makeText(this, R.string.alert_message_product_not_found, Toast.LENGTH_LONG).show()
                    soundPool.play(soundError, 1.0f, 1.0f, 0, 0, 1.0f)
                } else {
                    addProduct(prv)
                    soundPool.play(soundScan, 1.0f, 1.0f, 0, 0, 1.0f)
                    // アダプターに反映
                    mProductVariationAdapter.notifyDataSetChanged()
                }
                view.text = ""
            }
            return@setOnEditorActionListener true
        }

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
                scanListener.onExistProductVariationAdded(it)
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