package net.mm2d.codereader

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : BasicActivity(R.layout.activity_login) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonCreate = findViewById<Button>(R.id.buttonCreate)

        //ログインボタンを押下すると、メニュー画面に遷移
        buttonLogin.setOnClickListener {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            soundPool.play(soundSuccess, 1.0f, 1.0f, 0, 0, 1.0f)
        }

        Toast.makeText(applicationContext, "idとパスワードを入力してください", Toast.LENGTH_SHORT).show()



        //アカウント新規作成ボタンを押下すると、アカウント新規作成画面に遷移
        buttonCreate.setOnClickListener {
            val intent = Intent(this, NewAccountActivity::class.java)
            startActivity(intent)
        }
    }
}