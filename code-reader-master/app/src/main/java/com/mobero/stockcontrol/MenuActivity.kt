package com.mobero.stockcontrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

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

        val darkModeValues = resources.getStringArray(R.array.dark_mode_values)
        when (PreferenceManager.getDefaultSharedPreferences(this)
            .getString(getString(R.string.dark_mode), getString(R.string.dark_mode_def_value))) {
             darkModeValues[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
             darkModeValues[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

         }
        setContentView(R.layout.activity_menu)
    }

    //ダークモードメニュー表示
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    //ダークモードメニュー選択
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == R.id.settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        } else super.onOptionsItemSelected(item)
}