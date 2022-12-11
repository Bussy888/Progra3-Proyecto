package com.grupoC.anderylosandersaurios.activity

import android.R
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.grupoC.anderylosandersaurios.SettingsActivity
import com.grupoC.anderylosandersaurios.databinding.ActivityMainBinding
import com.grupoC.anderylosandersaurios.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.buttonOptions.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java).apply {}
            startActivity(intent)
        }

        binding.buttonTutorial.setOnClickListener {
            val intent = Intent(this, RulesActivity::class.java).apply {}
            startActivity(intent)
        }

        binding.buttonTarjetaDificil.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java).apply {}
            intent.putExtra("HARD", true)
            startActivity(intent)
        }

        binding.buttonTarjetaFacil.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java).apply {}
            intent.putExtra("HARD", false)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window,
            window.decorView.findViewById(R.id.content)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())

            // When the screen is swiped up at the bottom
            // of the application, the navigationBar shall
            // appear for some time
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}