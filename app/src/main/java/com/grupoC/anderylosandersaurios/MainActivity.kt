package com.grupoC.anderylosandersaurios

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.grupoC.anderylosandersaurios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.progressBar.max = 1000
        val currentProgress = 1000
        ObjectAnimator.ofInt(binding.progressBar,"progress",currentProgress).setDuration(10000).start()

        object : CountDownTimer(300000,1000){
            override fun onTick(millisUntilFinished: Long) {
                var minute = (millisUntilFinished/1000)/60
                var second = (millisUntilFinished/1000)%60
                binding.textViewTimer.setText("$minute:$second")
            }

            override fun onFinish() {
                binding.textViewTimer.setText("You Lose!")
            }
        }
    }
}