package net.mm2d.codereader

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import net.mm2d.codereader.util.ProductUtils


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
                    mList.clear()
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

    override fun onSearch(prvCode: String) {
        searchProductVariation(prvCode)
    }

    private fun searchProductVariation(code: String) {
        val prv = ProductUtils.searchProductVariation(code)
        if (prv == null) {
            // 商品情報取得失敗時
            Toast.makeText(
                this,
                R.string.alert_message_product_not_found,
                Toast.LENGTH_LONG
            ).show()
            soundPool.play(soundAlert, 1.0f, 1.0f, 0, 0, 1.0f)
        } else {
            val existedPrv = addProductVariation(prv)
            if (existedPrv == null) {
                // 商品バリエーションリストへの追加に成功した場合に呼び出す
                onListAdded(prv)
            } else {
                // 商品バリエーションリストへの追加時に商品が既に存在した場合に呼び出す
                onProductVariationExisted(existedPrv)
            }
            onProductVariationSearchSuccess(prv)
            soundPool.play(soundScan, 1.0f, 1.0f, 0, 0, 1.0f)
        }
        // アダプターに反映
        mProductVariationAdapter.notifyDataSetChanged()
    }
}
