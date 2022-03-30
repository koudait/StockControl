package net.mm2d.codereader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView


class TOP_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_menu)

        

        //１）Viewの取得
        val btnStart :Button =findViewById(R.id.btnStart)

        //２）ボタンを押したらスキャン画面へ
        btnStart.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }
        lateinit var mCustomAdapter: CustomAdapter
        lateinit var mProductList: ArrayList<Product>
        // データ一覧の実装
        val one = Product("単一電池", R.drawable.one)
        val two = Product("単二電池", R.drawable.two)
        val three = Product("単三電池", R.drawable.three)
        val four = Product("単四電池",  R.drawable.four)
        val five = Product("単五電池", R.drawable.five)
        mProductList = arrayListOf(one, two, three, four, five)

        val listView = findViewById<ListView>(R.id.list_view)

        // CustomAdapterの生成と設定
        mCustomAdapter = CustomAdapter(this, mProductList)
        listView.adapter = mCustomAdapter



    }
}
