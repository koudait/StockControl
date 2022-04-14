package net.mm2d.codereader

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import net.mm2d.codereader.model.ProductVariation
import net.mm2d.codereader.util.Product


class UnplannedStoredActivity : AppCompatActivity() {

    /**
     * リストビューのアダプター定義
     */
    lateinit var mProductVariationAdapter: ProductVariationAdapter

    /**
     * リストビューのリスト定義
     */
    lateinit var mPrvList: ArrayList<ProductVariation>

    /**
     * サウンドプール
     */
    lateinit var soundPool: SoundPool

    /**
     * 成功音
     */
    private var soundSuccess = 0

    /**
     * スキャン音
     */
    private var soundScan = 0

    /**
     * エラー音
     */
    private var soundError = 0


    /**
     * カメラアクティビティのイベント定義
     */
    private var cameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
        if (result?.resultCode == Activity.RESULT_OK) {
            val code = result.data?.getStringExtra("barcodeKEY").toString()
            addProduct(code)
            // アダプターに反映
            mProductVariationAdapter.notifyDataSetChanged()
        }
    }

    /**
     * OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unplannedstored)

        //region サウンドの設定
        val audioAttributes = AudioAttributes.Builder()
            // USAGE_MEDIA
            // USAGE_GAME
            .setUsage(AudioAttributes.USAGE_GAME)
            // CONTENT_TYPE_MUSIC
            // CONTENT_TYPE_SPEECH, etc.
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(2)
            .build()
        soundSuccess = soundPool.load(this, R.raw.sound_success, 1)
        soundScan = soundPool.load(this, R.raw.sound_scan, 1)

        // カメラボタンを押下した際にカメラアクティビティを起動する
        val btnStart :Button = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            val intent = Intent(this,CameraScan::class.java)
            cameraResult.launch(intent)
        }
        //endregion

        //region リスト及びアダプターの設定
        mPrvList = arrayListOf()
        // CustomAdapterの生成と設定
        mProductVariationAdapter = ProductVariationAdapter(this, mPrvList, object : IncrementButtonClickListener{
            override fun onIncrementButtonClick(item: ProductVariation) {
                if (item.scanNum == 0) {
                    // 削除確認ダイアログを表示
                    AlertDialog.Builder(this@UnplannedStoredActivity)
                        .setTitle(R.string.alert_title_confirm)
                        .setMessage(R.string.alert_message_working_back)
                        .setPositiveButton("OK") { _, _ ->
                            mPrvList.remove(item)
                            mProductVariationAdapter.notifyDataSetChanged()
                        }
                        .setNegativeButton("Cancel") { _, _ ->
                            item.scanNum++
                            mProductVariationAdapter.notifyDataSetChanged()
                        }
                        .show()
                }
            }
        })

        // リストビューの設定
        val listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = mProductVariationAdapter

        //endregion

        //region 各種イベントの設定
        //EditText入力イベント処理
        val editSearch = findViewById<EditText>(R.id.editSearch)
        editSearch.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                //Edittextからの入力値を設定
                val textEnter: String = editSearch.text.toString()
                //商品追加
                if (addProduct(textEnter)) {
                    soundPool.play(soundScan, 1.0f, 1.0f, 0, 0, 1.0f)
                    // アダプターに反映
                    mProductVariationAdapter.notifyDataSetChanged()
                } else {
                    // 商品情報取得失敗時
                    soundPool.play(soundError, 1.0f, 1.0f, 0, 0, 1.0f)
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

    /**
     * 商品追加
     *
     * @param code 追加する商品の商品コード
     */
    private fun addProduct(code: String): Boolean {
        val prv = Product.productSearch(code) ?: return false
        var isExist = false
        mPrvList.forEach {
            if (it.uniqueCode == prv.uniqueCode) {
                // 存在した場合はインクリメント
                it.scanNum++
                isExist = true
                return@forEach
            }
        }
        // 存在しない場合はリストに追加
        if (!isExist) {
            mPrvList.add(prv)
            prv.scanNum = 1
        }
        return true
    }

}
