package com.mobero.stockcontrol

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.regions.Regions
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult

class NewAccountActivity : AppCompatActivity() {

    private val userPoolId = "ap-northeast-1_XoLC4B9oW"
    private val clientId = "1odi5bn282u0k6ck6k134r4ulf"
    private val clientSecret = "7hunkom68sbi5h8ilufm5s8accugl9294jvj8seqqmjm7pvm67t"
    private val userPool = CognitoUserPool(this, userPoolId, clientId, clientSecret, Regions.AP_NORTHEAST_1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)

        val buttonCreate = findViewById<Button>(R.id.buttonCreate)
        val textEmail = findViewById<EditText>(R.id.mailaddress01)
        val textPassword = findViewById<EditText>(R.id.password01)

        buttonCreate.setOnClickListener {
            signUp(textEmail.text.toString(), textPassword.text.toString(), {
                Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show()
                // 確認コード入力ダイアログを表示する

                val editText = AppCompatEditText(this)
                AlertDialog.Builder(this)
                    .setTitle("確認コード")
                    .setMessage("確認コードを入力してください。")
                    .setView(editText)
                    .setPositiveButton("OK") { dialog, _ ->
                        // OKボタンを押したときの処理
                        val code = editText.text.toString()
                        //確認コード判定API呼び出し
                        confirmSignUp(textEmail.text.toString(),code,{
                            // 確認コードが正しいとき、ダイアログを閉じて、サインインしてメインメニューに遷移
                            dialog.dismiss()
                            val intent = Intent(this, MenuActivity::class.java)
                            startActivity(intent)
                        },{
                            // 確認コードが正しくないとき、エラーメッセージを表示
                            Toast.makeText(this, "failed!" + it?.message, Toast.LENGTH_LONG).show()
                        })
                    }
                    .setNegativeButton("キャンセル") { dialog, _ ->
                        // キャンセルボタンを押したとき、ダイアログを閉じる
                        dialog.dismiss()
                    }
                    .create()
                    .show()

            }, {
                Toast.makeText(this, "failed!" + it?.message, Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun signUp(email: String,
                       password: String,
                       successCallback: () -> Unit,
                       failureCallback: (exception: Exception?) -> Unit
    ) {
        val userAttributes = CognitoUserAttributes()
        userAttributes.addAttribute("email", email)
        userPool.signUpInBackground(email, password, userAttributes, null,
            object : SignUpHandler {
                override fun onSuccess(user: CognitoUser?, signUpResult: SignUpResult?) {
                    successCallback()
                }

                override fun onFailure(exception: java.lang.Exception?) {
                    failureCallback(exception)
                }

            }
        )
    }

    //本人確認
    private fun confirmSignUp(email: String,
                      verifyCode: String,
                      successCallback: () -> Unit,
                      failureCallback: (exception: Exception?) -> Unit
    ) {
        userPool.getUser(email).confirmSignUpInBackground(verifyCode, false,
            object : GenericHandler {
                override fun onSuccess() {
                    successCallback()
                }

                override fun onFailure(exception: Exception?) {
                    failureCallback(exception)
                }
            })
    }



}