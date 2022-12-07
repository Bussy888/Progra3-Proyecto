package com.grupoC.anderylosandersaurios.activity

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.grupoC.anderylosandersaurios.classes.Cabinet
import com.grupoC.anderylosandersaurios.classes.MediatorGame
import com.grupoC.anderylosandersaurios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var game: MediatorGame
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
        binding.progressBar.max = 1000
        val currentProgress = 1000
        ObjectAnimator.ofInt(binding.progressBar, "progress", currentProgress).setDuration(10000)
            .start()

        object : CountDownTimer(300000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var minute = (millisUntilFinished / 1000) / 60
                var second = (millisUntilFinished / 1000) % 60
                binding.textViewTimer.setText("$minute:$second")
            }

            override fun onFinish() {
                binding.textViewTimer.setText("You Lose!")
            }
        }


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
}