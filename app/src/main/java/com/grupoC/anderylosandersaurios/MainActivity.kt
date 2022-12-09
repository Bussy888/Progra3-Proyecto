package com.grupoC.anderylosandersaurios

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.view.isGone
import com.grupoC.anderylosandersaurios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var hand : Handler
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.progressBar.max = 100
        progressBarCycle()
        timer()

    }
    fun timer(){
        object : CountDownTimer(300000,1000){
            override fun onTick(millisUntilFinished: Long) {
                val minute = (millisUntilFinished / 1000) / 60
                val seconds = seconds(millisUntilFinished)
                binding.textViewTimer.setText("$minute:$seconds")
            }

            override fun onFinish() {
                binding.textViewTimer.setText("You Lose!")
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
                i++;
                thunder(2000, 1000, binding.backgroundWhite)
                binding.progressBar.progress = 0
                i = 0


            }
        }.start()
    }
    fun thunder(millisInFuture : Long, countDownInterval : Long, view: View){
        object : CountDownTimer(millisInFuture,countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                view.visibility = View.VISIBLE
            }

            override fun onFinish() {
                view.visibility = View.GONE
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