package net.mm2d.codereader

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class LoginActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)






        buttonLogin.setOnClickListener {

            val idEdit = findViewById<EditText>(R.id.idEdit)
            val passwordEdit = findViewById<EditText>(R.id.passwordEdit)
            val textid: String = idEdit.text.toString()
            val textpassword: String = passwordEdit.text.toString()
            if (textid != "" && textpassword != "" )  {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            } else {
                buttonLogin.setOnClickListener {
                    AlertDialog.Builder(this)
                        .setTitle("ERROR！")
                        .setMessage("idとパスワードをそれぞれ入力してください")
                        .setPositiveButton("OK") { dialog, which -> }
                        .show()
                }
            }
        }

        //val buttonCreate = findViewById<Button>(R.id.buttonCreate)
        //buttonCreate.setOnClickListener {
        //    val intent = Intent(this, NewAccountActivity::class.java)
         //   startActivity(intent)
        //}
    }
}