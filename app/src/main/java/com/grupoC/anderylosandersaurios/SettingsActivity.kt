package com.grupoC.anderylosandersaurios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.core.content.getSystemService
import com.grupoC.anderylosandersaurios.databinding.ActivityMainBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val audioManager = binding.root.context.getSystemService(AUDIO_SERVICE)
    }

}