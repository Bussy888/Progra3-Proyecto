package com.grupoC.anderylosandersaurios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import com.grupoC.anderylosandersaurios.databinding.ActivitySettingsBinding
import java.util.Locale

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // TODO: VER CÓMO PASAR EL VOLUMEN SETEADO POR LA BARRA AL RESTO DE LA APLICACIÓN
        mediaPlayer= MediaPlayer.create(this, R.raw.nokia1994)
        mediaPlayer.start()
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

        // TODO: COMPROBAR QUE FUNCA EL CAMBIO DE IDIOMA

        //FUNCA EL ESPAÑOL, PERO NO VUELVE A INGLES O PASA AL REVES

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

}