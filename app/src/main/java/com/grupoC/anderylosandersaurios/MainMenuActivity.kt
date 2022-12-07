package com.grupoC.anderylosandersaurios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grupoC.anderylosandersaurios.databinding.ActivityMainBinding
import com.grupoC.anderylosandersaurios.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}