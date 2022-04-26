package com.mobero.stockcontrol

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class SplashActivity : AppCompatActivity() {

    val handler = Handler()
    val spHandler = SplashHandler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_splash)
    }
    override fun onResume() {
        super.onResume()
        //2000ミリ秒遅れて、「spHandler」を実行させる
        handler.postDelayed(spHandler, 2000)
    }
    override fun onStop() {
        super.onStop()
        intent = null
        handler.removeCallbacks(spHandler)
    }
    //スプラッシュ画面からスタート画面に遷移するためのクラス
    inner class SplashHandler : Runnable {
        override fun run() {
            //画面遷移
            intent = Intent(this@SplashActivity, MenuActivity::class.java)
            startActivity(intent)
            //アクティビティを破棄する
            this@SplashActivity.finish()
        }
    }
}