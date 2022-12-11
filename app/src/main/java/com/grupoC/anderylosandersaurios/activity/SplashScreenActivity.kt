package com.grupoC.anderylosandersaurios.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.grupoC.anderylosandersaurios.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        changeActivity()
    }

    fun changeActivity() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                val intent = Intent(applicationContext, LoginActivity::class.java).apply {}
                startActivity(intent)
            }
        }.start()
    }
}