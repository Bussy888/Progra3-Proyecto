package com.grupoC.anderylosandersaurios.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.grupoC.anderylosandersaurios.R
import com.grupoC.anderylosandersaurios.classes.Cabinet
import com.grupoC.anderylosandersaurios.classes.MediatorGame
import com.grupoC.anderylosandersaurios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var thunderSound : MediaPlayer
    private lateinit var binding: ActivityMainBinding
    private lateinit var game: MediatorGame
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //Inicialización
        game = MediatorGame(
            Cabinet("blue", 0),
            Cabinet("red", 0),
            Cabinet("yellow", 0),
            Cabinet("green", 0)
        )
        binding.redScore.text = "0"
        binding.yellowScore.text = "0"
        binding.blueScore.text = "0"
        binding.greenScore.text = "0"

        // El timer
        binding.progressBar.max = 100
        progressBarCycle()
        timer()
        thunderSound = MediaPlayer.create(this, R.raw.thunder)



        // Los botones
        binding.buttonOne.setOnClickListener {
            binding.redScore.text = game.checking(1).toString()
        }

        binding.buttonTwo.setOnClickListener {
            binding.yellowScore.text = game.checking(2).toString()
        }

        binding.buttonThree.setOnClickListener {
            binding.blueScore.text = game.checking(3).toString()
        }

        binding.buttonFour.setOnClickListener {
            binding.greenScore.text = game.checking(4).toString()
        }

    }
    fun timer(){
        object : CountDownTimer(3000,1000){
            override fun onTick(millisUntilFinished: Long) {
                val minute = (millisUntilFinished / 1000) / 60
                val seconds = seconds(millisUntilFinished)
                binding.textViewTimer.text = "$minute:$seconds"
            }

            override fun onFinish() {
                    val intent = Intent(applicationContext, GameOverActivity::class.java).apply {}
                    startActivity(intent)
            }
        }.start()

    }
    fun progressBarCycle(){
        object  : CountDownTimer(20000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                Log.v("Log_tag", "Tickprogress $i $millisUntilFinished  ")
                i++
                binding.progressBar.progress = i*100/(20000/1000)
            }

            override fun onFinish() {
                i++
                thunder(3000, 1000, binding.backgroundWhite)
                thunderSound.start()
                binding.progressBar.progress = 0
                i = 0


            }
        }.start()
    }
    fun thunder(millisInFuture : Long, countDownInterval : Long, view: View){
        object : CountDownTimer(millisInFuture,countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                view.visibility = View.VISIBLE
                binding.groupButtons.visibility = View.GONE
            }

            override fun onFinish() {
                view.visibility = View.GONE
                binding.groupButtons.visibility = View.VISIBLE
                progressBarCycle()
            }
        }.start()


    }
    fun seconds(millisUntilFinished: Long): String{
        return if((millisUntilFinished/ 1000) % 60<10){
            "0${(millisUntilFinished/ 1000) % 60}"
        }else{
            "${(millisUntilFinished/ 1000) % 60}"
        }
    }
}