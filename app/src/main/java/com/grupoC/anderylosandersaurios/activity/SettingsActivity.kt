package com.grupoC.anderylosandersaurios.activity

import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.grupoC.anderylosandersaurios.R
import com.grupoC.anderylosandersaurios.activity.LoginActivity.Companion.LANG
import com.grupoC.anderylosandersaurios.activity.LoginActivity.Companion.VIBRATION
import com.grupoC.anderylosandersaurios.activity.LoginActivity.Companion.VOLUME
import com.grupoC.anderylosandersaurios.databinding.ActivitySettingsBinding
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        hideSystemUI()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        hideSystemUI()
        initVolumen()

        binding.barVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                VOLUME = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

        })

        if(LANG == "en"){
            binding.english.isChecked = true
            binding.spanish.isChecked = false
        } else{
            binding.english.isChecked = false
            binding.spanish.isChecked = true
        }

        if(VIBRATION){
            binding.vibrationOn.isChecked=true
            binding.vibrationOff.isChecked=false
        } else{
            binding.vibrationOn.isChecked=false
            binding.vibrationOff.isChecked=true
        }

        binding.english.setOnClickListener {
            setLocale("en")
        }
        binding.spanish.setOnClickListener {
            setLocale("es")
        }

        binding.buttonMenu.setOnClickListener {
            onBackPressed()
        }

        binding.vibrationOn.setOnClickListener{
            VIBRATION = true
        }

        binding.vibrationOff.setOnClickListener {
            VIBRATION = false
        }
    }

    private fun setLocale(languageCode: String) {
        LANG = languageCode
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = this.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        this.recreate()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(android.R.id.content)
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())

            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainMenuActivity::class.java).apply {}
        startActivity(intent)
        finish()
    }

    private fun initVolumen(){
        mediaPlayer = MediaPlayer.create(this, R.raw.the_consequence_of_style)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        binding.barVolume.max = max
        binding.barVolume.progress = currentVol
    }

    override fun onPause() {
        mediaPlayer.pause()
        super.onPause()
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        super.onDestroy()
    }

    override fun onStop() {
        mediaPlayer.pause()
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        mediaPlayer.start()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

}