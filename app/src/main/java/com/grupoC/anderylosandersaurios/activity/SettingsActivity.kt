package com.grupoC.anderylosandersaurios

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager.widget.ViewPager.DecorView
import com.grupoC.anderylosandersaurios.databinding.ActivitySettingsBinding
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        hideSystemUI()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // TODO: VER CÓMO PASAR EL VOLUMEN SETEADO POR LA BARRA AL RESTO DE LA APLICACIÓN
        mediaPlayer= MediaPlayer.create(this, R.raw.nokia1994)
        //mediaPlayer.start()
        audioManager=getSystemService(AUDIO_SERVICE) as AudioManager

        val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        binding.barVolume.max = max
        binding.barVolume.progress = currentVol

        binding.barVolume.setOnSeekBarChangeListener( object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }

        })


        // TODO: ARREGLAR CAMBIO DE IDIOMA, NO SÉ QUÉ LE PASA TnT

        //FUNCA EL ESPAÑOL, PERO NO VUELVE A INGLES O PASA AL REVES

        val locale = this.resources.configuration.locales

        binding.english.setOnClickListener {
            setLocale("en")
        }
        binding.spanish.setOnClickListener {
            setLocale("es")
        }
    }

    private fun setLocale(languageCode:String){
        var locale= Locale(languageCode)
        Locale.setDefault(locale)
        this.resources.configuration.setLocale(locale)
        this.recreate()
    }

    private fun initControls(){


    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window,
            window.decorView.findViewById(android.R.id.content)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())

            // When the screen is swiped up at the bottom
            // of the application, the navigationBar shall
            // appear for some time
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}