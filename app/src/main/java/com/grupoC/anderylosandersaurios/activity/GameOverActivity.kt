package com.grupoC.anderylosandersaurios.activity

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
import com.grupoC.anderylosandersaurios.databinding.ActivityGameOverBinding
import com.grupoC.anderylosandersaurios.databinding.ActivityLoginBinding
import com.grupoC.anderylosandersaurios.R

class GameOverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameOverBinding

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager

    companion object {
        val SCORE: String = "new_Message"
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)

        val score: Int = intent.getIntExtra(SCORE, 0)

        setContentView(binding.root)
        hideSystemUI()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        initVolumen()

        if(intent.getBooleanExtra("HARD",true)){
            binding.scoreTitle.text ="SCORE++"
        }

        binding.scoreNumber.text = score.toString()

        binding.buttonMenu.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            intent.apply {
                putExtra(SCORE, score)
            }
            startActivity(intent)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(android.R.id.content)
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
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        LoginActivity.VOLUME = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainMenuActivity::class.java).apply {}
        startActivity(intent)
        finish()
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