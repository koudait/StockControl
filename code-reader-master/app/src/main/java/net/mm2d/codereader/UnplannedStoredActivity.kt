package net.mm2d.codereader

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.mm2d.codereader.model.ProductVariation
import net.mm2d.codereader.model.ScanProductVariation
import net.mm2d.codereader.util.Product


class UnplannedStoredActivity : AppCompatActivity() {

    lateinit var mCustomAdapter: CustomAdapter
    lateinit var mProductVariationList: ArrayList<ProductVariation>

    companion object {
        const val RESULT_ACTIVITY = 1000
    }

    val scanProductList:List<ScanProductVariation> = listOf()
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

        mProductVariationList = arrayListOf<ProductVariation>()
        // CustomAdapterの生成と設定
        mCustomAdapter = CustomAdapter(this, mProductVariationList)
        val listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = mCustomAdapter

        // ３）渡された値を取り出す⇒テキスト欄に表示
        //val textView : TextView =  findViewById(R.id.editSearch)
        //val textCode = intent.getStringExtra("barcodeKEY")
        //textView.text = textCode

        //lateinit var mCustomAdapter: CustomAdapter
        //lateinit var mProductList: ArrayList<Product>
        // データ一覧の実装
        //val one = Product("単一電池", R.drawable.one)
        //val two = Product("単二電池", R.drawable.two)
        //val three = Product("単三電池", R.drawable.three)
        //val four = Product("単四電池",  R.drawable.four)
        //val five = Product("単五電池", R.drawable.five)
        //mProductList = arrayListOf(one, two, three, four, five)

        //val listView = findViewById<ListView>(R.id.list_view)

        // CustomAdapterの生成と設定
        //mCustomAdapter = CustomAdapter(this, mProductList)
        //listView.adapter = mCustomAdapter


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
        val productVariation = Product.productSearch(code)
        mProductVariationList.add(productVariation)
        mCustomAdapter.notifyDataSetChanged()
        Toast.makeText(this, code, Toast.LENGTH_LONG).show()
    }

}
