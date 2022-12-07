package com.grupoC.anderylosandersaurios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grupoC.anderylosandersaurios.databinding.ActivityGameOverBinding
import com.grupoC.anderylosandersaurios.databinding.ActivityLoginBinding

class GameOverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameOverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}