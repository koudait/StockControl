package com.mobero.stockcontrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.ImageButton

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        //１）ImageButtonの取得
        val menu01: ImageButton = findViewById(R.id.menu01)
        val menu02: ImageButton = findViewById(R.id.menu02)


        //２）ボタンを押したら次の画面へ
        menu01.setOnClickListener {
            val intent = Intent(this, UnplannedStoredActivity::class.java)
            startActivity(intent)
        }
        menu02.setOnClickListener {
            val intent = Intent(this, StockSearchActivity::class.java)
            startActivity(intent)
        }
    }
}