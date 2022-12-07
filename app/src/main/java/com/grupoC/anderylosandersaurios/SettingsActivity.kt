package com.grupoC.anderylosandersaurios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.core.content.getSystemService
import com.grupoC.anderylosandersaurios.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mediaPlayer= MediaPlayer.create(this, R.birds)

        setVolumeControlStream(AudioManager.STREAM_MUSIC)
        val audioManager = applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager

    }

    private fun initControls(){


    }

}