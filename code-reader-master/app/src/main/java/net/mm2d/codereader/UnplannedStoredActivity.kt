package net.mm2d.codereader

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import net.mm2d.codereader.model.ProductVariation
import net.mm2d.codereader.util.ProductUtils


class UnplannedStoredActivity : ProductVariationListActivity(R.layout.activity_unplannedstored) {

    /**
     * カメラアクティビティのイベント定義
     */
    private var cameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
        if (result?.resultCode == Activity.RESULT_OK) {
            val code = result.data?.getStringExtra("barcodeKEY").toString()
            val prv = ProductUtils.productVariationSearch(code)
            if (prv == null) {
                // 商品情報取得失敗時
                Toast.makeText(this, R.string.alert_message_product_not_found, Toast.LENGTH_LONG).show()
                soundPool.play(soundError, 1.0f, 1.0f, 0, 0, 1.0f)
            } else {
                addProduct(prv)
                soundPool.play(soundScan, 1.0f, 1.0f, 0, 0, 1.0f)
                // アダプターに反映
                mProductVariationAdapter.notifyDataSetChanged()
            }
            // アダプターに反映
            mProductVariationAdapter.notifyDataSetChanged()
        }
    }

    /**
     * OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // カメラボタンを押下した際にカメラアクティビティを起動する
        val btnStart :Button = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            val intent = Intent(this,CameraScan::class.java)
            cameraResult.launch(intent)
        }

        //region 各種イベントの設定
        //EditText入力イベント処理
        val editSearch = findViewById<EditText>(R.id.editSearch)
        editSearch.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                //Edittextからの入力値を設定
                val code: String = editSearch.text.toString()
                //商品追加
                val prv = ProductUtils.productVariationSearch(code)
                if (prv == null) {
                    // 商品情報取得失敗時
                    Toast.makeText(this, R.string.alert_message_product_not_found, Toast.LENGTH_LONG).show()
                    soundPool.play(soundError, 1.0f, 1.0f, 0, 0, 1.0f)
                } else {
                    addProduct(prv)
                    soundPool.play(soundScan, 1.0f, 1.0f, 0, 0, 1.0f)
                    // アダプターに反映
                    mProductVariationAdapter.notifyDataSetChanged()
                }
                view.text = ""
            }
            return@setOnEditorActionListener true
        }

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
