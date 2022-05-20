package com.mobero.stockcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class StockChangeHistoryActivity : ProductVariationListActivity(R.layout.activity_stock_change_history) {
    init {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_change_history)

        val editStockChangeHistory = findViewById<EditText>(R.id.StockChangeHistorySearch)
        val btnStockChangeHistorySearch = findViewById<Button>(R.id.btnStockChangeHistorySearch)

        btnStockChangeHistorySearch.setOnClickListener {

            if (editStockChangeHistory.text.toString() != "") {
                //アダプター(リスト)を表示する処理
                //if (::mProductVariationListener.isInitialized) mProductVariationListener.onSearch(code)
                //if (::mStockListener.isInitialized) mStockListener.onSearch(code)
            } else {
                Toast.makeText(applicationContext, "コードを入力してください。", Toast.LENGTH_SHORT).show()
            }
        }
    }
}