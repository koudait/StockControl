package net.mm2d.codereader

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*


class UnplannedStoredActivity : ScanProductVariationActivity(R.layout.activity_unplannedstored) {
    /**
     * OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //region 各種イベントの設定

        // 登録ボタン押下イベント処理
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            // 削除確認ダイアログを表示
            AlertDialog.Builder(this@UnplannedStoredActivity)
                .setTitle(R.string.alert_title_confirm)
                .setMessage(R.string.alert_message_register)
                .setPositiveButton("OK") { _, _ ->
                    mPrvList.clear()
                    mProductVariationAdapter.notifyDataSetChanged()
                    soundPool.play(soundSuccess, 1.0f, 1.0f, 0, 0, 1.0f)
                    //TODO 登録処理を記載する。
                }
                .setNegativeButton("Cancel") { _, _ ->
                }
                .show()
        }
        //endregion

    }
}
