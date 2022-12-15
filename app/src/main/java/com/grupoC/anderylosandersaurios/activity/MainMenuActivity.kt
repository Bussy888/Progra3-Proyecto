package com.grupoC.anderylosandersaurios.activity

import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
        val ID: String = "email"
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

        managePreferences()

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
            finish()
        }

        binding.buttonTarjetaFacil.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {}
            intent.putExtra("HARD", false)
            startActivity(intent)
            finish()
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
        mediaPlayer = MediaPlayer.create(
            this,
            com.grupoC.anderylosandersaurios.R.raw.the_consequence_of_style
        )
        mediaPlayer.start()
        mediaPlayer.isLooping = true
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, VOLUME, 0)
    }

    private fun managePreferences() {
        val sharedPrefFile = "Scores_saved_${intent.getStringExtra(ID)}"
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        val sharedBestScore = sharedPreferences.getInt("best_score", 0)
        val sharedLastScore = sharedPreferences.getInt("last_score", 0)

        binding.textBestScoreNumber.text = sharedBestScore.toString()
        binding.textLastScoreNumber.text = sharedLastScore.toString()

        val lastScore: Int = intent.getIntExtra(SCORE, -1)

        if (lastScore >= 0) {
            val editor = sharedPreferences.edit()
            if (sharedLastScore < lastScore) {
                binding.textBestScoreNumber.text = lastScore.toString()
                editor.putInt("best_score", lastScore)
            }
            binding.textLastScoreNumber.text = lastScore.toString()
            editor.putInt("last_score", lastScore)

            editor.apply()
            editor.commit()
        }
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
        mediaPlayer.start()
        super.onRestart()

    }

    override fun onResume() {
        mediaPlayer.start()
        super.onResume()
    }

    override fun onStart() {
        mediaPlayer.start()
        super.onStart()
    }
}