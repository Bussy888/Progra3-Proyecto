package com.grupoC.anderylosandersaurios.activity

import android.R
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.grupoC.anderylosandersaurios.activity.LoginActivity.Companion.VOLUME
import com.grupoC.anderylosandersaurios.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager

    companion object {
        val SCORE: String = "new_Message"
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        initVolumen()
        binding.buttonOptions.setOnClickListener {
            val intentRedirect = Intent(this, SettingsActivity::class.java)
            startActivity(intentRedirect)
            finish()
        }

        binding.buttonTutorial.setOnClickListener {
            val intent = Intent(this, RulesActivity::class.java).apply {}
            startActivity(intent)
        }

        binding.buttonTarjetaDificil.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {}
            intent.putExtra("HARD", true)
            startActivity(intent)
        }

        binding.buttonTarjetaFacil.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {}
            intent.putExtra("HARD", false)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(R.id.content)
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())

            // When the screen is swiped up at the bottom
            // of the application, the navigationBar shall
            // appear for some time
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun initVolumen() {
        mediaPlayer = MediaPlayer.create(this, com.grupoC.anderylosandersaurios.R.raw.thunder)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, VOLUME, 0)
    }

    override fun onPause() {
        mediaPlayer.stop()
        super.onPause()
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        super.onDestroy()
    }

    override fun onStop() {
        mediaPlayer.stop()
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