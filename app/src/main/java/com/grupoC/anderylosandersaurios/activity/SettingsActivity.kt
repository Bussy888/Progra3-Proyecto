package com.grupoC.anderylosandersaurios.activity

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager.widget.ViewPager.DecorView
import com.grupoC.anderylosandersaurios.R
import com.grupoC.anderylosandersaurios.databinding.ActivitySettingsBinding
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager

    // TODO: COPIAR CODIGO PARA OCULTAR BARRA DE NAVEGACION A TODAS LAS ACTIVIDADES
    private lateinit var decorWindow: View //barraNaveg
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        decorWindow = window.decorView //barranaveg
        hideSystemUI() //barranaveg

        // TODO: VER CÓMO PASAR EL VOLUMEN SETEADO POR LA BARRA AL RESTO DE LA APLICACIÓN
        mediaPlayer = MediaPlayer.create(this, R.raw.nokia1994)
        //mediaPlayer.start()
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        binding.barVolume.max = max
        binding.barVolume.progress = currentVol

        binding.barVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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

    //BARRANAVEG
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, decorWindow).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
        }
    }

    private fun setLocale(languageCode: String) {
        var locale = Locale(languageCode)
        Locale.setDefault(locale)
        this.resources.configuration.setLocale(locale)
        this.recreate()
    }

    private fun initControls() {


    }

}