package com.mobero.stockcontrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // ImageButtonの取得
        val btnUnplannedStore: Button = findViewById(R.id.btn_unplanned_store)
        val btnStockSearch: Button = findViewById(R.id.btn_stock_search)

        // ボタンを押したら次の画面へ
        btnUnplannedStore.setOnClickListener {
            val intent = Intent(this, UnplannedStoredActivity::class.java)
            startActivity(intent)
        }
        btnStockSearch.setOnClickListener {
            val intent = Intent(this, StockSearchActivity::class.java)
            startActivity(intent)
        }
    }
}