package com.grupoC.anderylosandersaurios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grupoC.anderylosandersaurios.databinding.ActivityRulesBinding
import com.grupoC.anderylosandersaurios.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}