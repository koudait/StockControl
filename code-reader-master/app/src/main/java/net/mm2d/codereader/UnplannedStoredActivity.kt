package net.mm2d.codereader

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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

        val editSearch = findViewById<EditText>(R.id.editSearch)

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
