package com.grupoC.anderylosandersaurios.activity

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.grupoC.anderylosandersaurios.R
import com.grupoC.anderylosandersaurios.classes.ButtonContract
import com.grupoC.anderylosandersaurios.classes.Cabinet
import com.grupoC.anderylosandersaurios.classes.MediatorGame
import com.grupoC.anderylosandersaurios.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var thunderSound: MediaPlayer
    private lateinit var binding: ActivityMainBinding
    private lateinit var game: MediatorGame

    private var i = 0

    private var colors: List<String> = listOf("red", "yellow", "blue", "green")
    private val colorDraw: Map<String, Int> = mapOf(
        "red" to R.drawable.folder_red_plus,
        "yellow" to R.drawable.folder_yellow_plus,
        "blue" to R.drawable.folder_blue_plus,
        "green" to R.drawable.folder_green_plus
    )

    private var easyLevel: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //Inicialización
        game = MediatorGame(
            Cabinet("blue", 0),
            Cabinet("red", 0),
            Cabinet("yellow", 0),
            Cabinet("green", 0),
            generatorButtonContracts(),
            this
        )

        initGame()

        // Progress bar
        binding.progressBar.max = 100

        if (!easyLevel) {
            progressBarCycle()
        }

        // El timer
        timer()
        thunderSound = MediaPlayer.create(this, R.raw.thunder)


        // Los botones
        binding.buttonOne.setOnClickListener {
            clickButton(1)
        }

        binding.buttonTwo.setOnClickListener {
            clickButton(2)
        }

        binding.buttonThree.setOnClickListener {
            clickButton(3)
        }

        binding.buttonFour.setOnClickListener {
            clickButton(4)
        }
    }

    fun clickButton(buttonPosition: Int) {
        when (colors[buttonPosition - 1]) {
            "red" -> {
                binding.redScore.text = game.checking("red").toString()
            }
            "yellow" -> {
                binding.yellowScore.text = game.checking("yellow").toString()
            }
            "blue" -> {
                binding.blueScore.text = game.checking("blue").toString()
            }
            "green" -> {
                binding.greenScore.text = game.checking("green").toString()
            }
        }
    }

    fun generatorButtonContracts(): List<ButtonContract> {
        val buttonContracts: MutableList<ButtonContract> = mutableListOf()
        for (i in 0..3) {
            buttonContracts.add(ButtonContract(colors[i], i + 1))
        }
        return buttonContracts.toList()
    }

    fun shuffleButtons() {
        colors = colors.shuffled()
        for (i in 0 until game.buttonsContracts.size) {
            game.buttonsContracts[i].color = colors[i]
        }
        setButtonColors()
    }

    fun setButtonColors() {
        binding.buttonOne.setImageResource(colorDraw[game.buttonsContracts[0].color]!!)
        binding.buttonTwo.setImageResource(colorDraw[game.buttonsContracts[1].color]!!)
        binding.buttonThree.setImageResource(colorDraw[game.buttonsContracts[2].color]!!)
        binding.buttonFour.setImageResource(colorDraw[game.buttonsContracts[3].color]!!)
    }


    fun initGame() {
        binding.redScore.text = "0"
        binding.yellowScore.text = "0"
        binding.blueScore.text = "0"
        binding.greenScore.text = "0"
        game.generateContract()
        setButtonColors()
    }

    fun timer() {
        object : CountDownTimer(300000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minute = (millisUntilFinished / 1000) / 60
                val seconds = seconds(millisUntilFinished)
                binding.textViewTimer.setText("$minute:$seconds")
            }

            override fun onFinish() {
                val intent = Intent(applicationContext, GameOverActivity::class.java).apply {}
                startActivity(intent)
            }
        }.start()

    }

    fun progressBarCycle() {
        object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.v("Log_tag", "Tickprogress $i $millisUntilFinished  ")
                i++
                binding.progressBar.progress = i * 100 / (20000 / 1000)
            }

            override fun onFinish() {
                i++;
                thunder(3000, 1000, binding.backgroundWhite)
                thunderSound.start()
                binding.progressBar.progress = 0
                i = 0
            }
        }.start()
        shuffleButtons()
    }

    fun thunder(millisInFuture: Long, countDownInterval: Long, view: View) {
        object : CountDownTimer(millisInFuture, countDownInterval) {
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

    fun seconds(millisUntilFinished: Long): String {
        return if ((millisUntilFinished / 1000) % 60 < 10) {
            "0${(millisUntilFinished / 1000) % 60}"
        } else {
            "${(millisUntilFinished / 1000) % 60}"
        }
    }

    fun idSContracts(name: String) {
        val id: Int = resources.getIdentifier("folder_yellow", "drawable", packageName)
        binding.imageFileCenter.setImageResource(id)
    }
}