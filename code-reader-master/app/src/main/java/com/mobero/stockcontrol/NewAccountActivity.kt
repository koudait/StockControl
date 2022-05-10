package com.mobero.stockcontrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
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
                val confirmFlg = true
                if (confirmFlg) {
                    // 認証が確認取れたら認証APIを叩く
                    // 認証APIが成功したら自動でサインインしてメインメニューに遷移
                }
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

}