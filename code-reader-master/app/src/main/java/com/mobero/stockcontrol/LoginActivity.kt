package com.mobero.stockcontrol

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class LoginActivity : BasicActivity(R.layout.activity_login) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonCreate = findViewById<Button>(R.id.buttonCreate)

        //ログインボタン押下した際の処理
        buttonLogin.setOnClickListener {

            //id,passwordの入力値を取得
            val editId = findViewById<EditText>(R.id.idEdit)
            val editPass = findViewById<EditText>(R.id.passwordEdit)

            //id,passwordがともに入力された場合、メニュー画面に遷移する
            if ((editId.text.toString()!="") && (editPass.text.toString()!="")) {

                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                soundPool.play(soundSuccess, 1.0f, 1.0f, 0, 0, 1.0f)
            } else {
                //どちらか一方でも入力されていなかった場合、入力を促すメッセージを表示する
                Toast.makeText(applicationContext, "idとパスワードを入力してください", Toast.LENGTH_SHORT).show()
            }
        }


        //アカウント新規作成ボタンを押下すると、アカウント新規作成画面に遷移
        buttonCreate.setOnClickListener {
            val intent = Intent(this, NewAccountActivity::class.java)
            startActivity(intent)
        }
    }
}