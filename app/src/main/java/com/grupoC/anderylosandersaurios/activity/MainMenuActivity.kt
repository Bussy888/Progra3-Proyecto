package com.grupoC.anderylosandersaurios.activity

import android.R
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.grupoC.anderylosandersaurios.databinding.ItemPopupTutorialBinding

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager
    private lateinit var popupTutorialBinding: ItemPopupTutorialBinding
    private val sharedPrefFile = "Scores_saved"

    companion object {
        var SCORE: Int = -1
        var email: String = ""

        fun asignEmail(newEmail: String) {
            email = newEmail
        }
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

        var popUpTutorial = intent.getBooleanExtra("POPUP", false)

        if(popUpTutorial){
            managePopupTutorial()

        }

        binding.buttonOptions.setOnClickListener {
            val intentRedirect = Intent(this, SettingsActivity::class.java)
            startActivity(intentRedirect)
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
    fun managePopupTutorial() {
        popupTutorialBinding = ItemPopupTutorialBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(popupTutorialBinding.root)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
        popupTutorialBinding.buttonAccept.setOnClickListener {
            val intent = Intent(this, RulesActivity::class.java).apply {}
            startActivity(intent)
            dialog.dismiss()

        }
        popupTutorialBinding.buttonCancel.setOnClickListener {
            dialog.dismiss()
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
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val sharedBestScore =
            sharedPreferences.getInt("best_score_$email", 0)
        val sharedLastScore =
            sharedPreferences.getInt("last_score_$email", 0)

        binding.textBestScoreNumber.text = sharedBestScore.toString()
        binding.textLastScoreNumber.text = sharedLastScore.toString()

        val lastScore: Int = SCORE
        val editor = sharedPreferences.edit()
        if (lastScore >= 0) {

            if (sharedBestScore < lastScore) {
                binding.textBestScoreNumber.text = lastScore.toString()
                editor.putInt("best_score_$email", lastScore)
            }
            binding.textLastScoreNumber.text = lastScore.toString()
            editor.putInt("last_score_$email", lastScore)

            editor.apply()
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
        super.onRestart()
        this.recreate()
        mediaPlayer.start()
    }

    override fun onResume() {
        mediaPlayer.start()
        managePreferences()
        super.onResume()
    }

    override fun onStart() {
        mediaPlayer.start()
        super.onStart()
    }
}