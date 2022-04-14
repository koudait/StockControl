package net.mm2d.codereader

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import net.mm2d.codereader.model.ProductVariation
import net.mm2d.codereader.util.Product


class UnplannedStoredActivity : AppCompatActivity() {

    /**
     * リストビューのアダプター定義
     */
    lateinit var mProductVariationAdapter: ProductVariationAdapter

    /**
     * リストビューのリスト定義
     */
    lateinit var mPrvList: ArrayList<ProductVariation>

    /**
     * カメラアクティビティのイベント定義
     */
    private var cameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
        if (result?.resultCode == Activity.RESULT_OK) {
            val code = result.data?.getStringExtra("barcodeKEY").toString()
            addProduct(code)
            // アダプターに反映
            mProductVariationAdapter.notifyDataSetChanged()
        }
    }

    /**
     * OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unplannedstored)

        // カメラボタンを押下した際にカメラアクティビティを起動する
        val btnStart :Button = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            val intent = Intent(this,CameraScan::class.java)
            cameraResult.launch(intent)
        }

        // アダプターに設定する商品リストの生成
        mPrvList = arrayListOf()
        // CustomAdapterの生成と設定
        mProductVariationAdapter = ProductVariationAdapter(this, mPrvList, object : IncrementButtonClickListener{
            override fun onIncrementButtonClick(item: ProductVariation) {
                if (item.scanNum == 0) {
                    // 削除確認ダイアログを表示
                    AlertDialog.Builder(this@UnplannedStoredActivity)
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
        val listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = mProductVariationAdapter

        //EditText入力イベント処理
        val editSearch = findViewById<EditText>(R.id.editSearch)
        editSearch.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Edittextからの入力値を設定
                val textEnter: String = editSearch.text.toString()
                //変数codeに代入
                addProduct(textEnter)
                // アダプターに反映
                mProductVariationAdapter.notifyDataSetChanged()
                view.text = ""
            }
            return@setOnEditorActionListener true
        }
    }

    /**
     * 商品追加
     *
     * @args code 追加する商品の商品コード
     */
    private fun addProduct(code: String) {
        val prv = Product.productSearch(code)
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
