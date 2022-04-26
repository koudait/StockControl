package com.mobero.stockcontrol

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobero.stockcontrol.R

open class BasicActivity(private val layoutId: Int) : AppCompatActivity() {
    /**
     * サウンドプール
     */
    lateinit var soundPool: SoundPool

    /**
     * 成功音
     */
    var soundSuccess = 0

    /**
     * スキャン音
     */
    var soundScan = 0

    /**
     * エラー音
     */
    var soundError = 0

    /**
     * アラート音
     */
    var soundAlert = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

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
        soundError = soundPool.load(this, R.raw.sound_error, 1)
        soundAlert = soundPool.load(this, R.raw.sound_alert, 1)

        //endregion

    }
}