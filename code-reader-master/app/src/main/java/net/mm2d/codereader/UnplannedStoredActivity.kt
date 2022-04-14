package net.mm2d.codereader

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.system.Os.remove
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import net.mm2d.codereader.model.ProductVariation
import net.mm2d.codereader.util.Product


class UnplannedStoredActivity : AppCompatActivity() {

    lateinit var mProductVariationAdapter: ProductVariationAdapter
    lateinit var mPrvList: ArrayList<ProductVariation>

    companion object {
        const val RESULT_ACTIVITY = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_unplannedstored)


        //１）Viewの取得
        val btnStart :Button =findViewById(R.id.btnStart)

        //２）ボタンを押したらスキャン画面へ
        btnStart.setOnClickListener {
            val intent = Intent(this,CameraScan::class.java)
            startActivityForResult(intent, RESULT_ACTIVITY)
        }

        mPrvList = arrayListOf()
        // CustomAdapterの生成と設定
        mProductVariationAdapter = ProductVariationAdapter(this, mPrvList)
        val listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = mProductVariationAdapter

        //EditText入力イベント処理
        val editSearch = findViewById<EditText>(R.id.editSearch)
        //Enterkey押下イベント
        editSearch.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Edittextからの入力値を設定
                val textEnter: String = editSearch.getText().toString()
                //変数codeに代入
                addProduct(code = textEnter)
                fun addProduct(code: String) {
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

                    //ボタン増減処理
                    val btnSub: Button =findViewById(R.id.btnSub)
                    val btnAdd: Button =findViewById(R.id.btnAdd)
                    val countView: TextView =findViewById(R.id.countView)

                    //-1ボタンを押下した時、リストをクリアにする
                    btnSub.setOnClickListener {
                        mPrvList.clear()
                    }

                    //+1ボタンを押下した時、カウントアップしテキストに表示
                    btnSub.setOnClickListener {
                        prv.scanNum++
                        countView.text = prv.scanNum.toString()
                    }

                    // アダプターに反映
                    mProductVariationAdapter.notifyDataSetChanged()
                }
            }
            return@setOnEditorActionListener true
        }
    }
    //CameraScan画面からバーコード値を取得する処理
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (resultCode == Activity.RESULT_OK &&
            requestCode == RESULT_ACTIVITY && intent != null) {

            val code = intent.getStringExtra("barcodeKEY").toString()
            addProduct(code)
        }
    }

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
        // アダプターに反映
        mProductVariationAdapter.notifyDataSetChanged()
    }

}
