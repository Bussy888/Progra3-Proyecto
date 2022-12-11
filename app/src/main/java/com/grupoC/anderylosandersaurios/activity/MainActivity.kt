package com.grupoC.anderylosandersaurios.activity

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.grupoC.anderylosandersaurios.R
import com.grupoC.anderylosandersaurios.SettingsActivity
import com.grupoC.anderylosandersaurios.classes.Cabinet
import com.grupoC.anderylosandersaurios.classes.MediatorGame
import com.grupoC.anderylosandersaurios.databinding.ActivityMainBinding
import com.grupoC.anderylosandersaurios.databinding.ItemPopupMenuBinding
import com.grupoC.anderylosandersaurios.databinding.ItemPopupSettingsBinding

class MainActivity : AppCompatActivity() {

    private lateinit var thunderSound : MediaPlayer
    private lateinit var binding: ActivityMainBinding
    private lateinit var game: MediatorGame
    private lateinit var bindingPopupMenu : ItemPopupMenuBinding
    private lateinit var bindingPopupSettings : ItemPopupSettingsBinding
    var i = 0

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        hideSystemUI()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        var hard = intent.getBooleanExtra("HARD", true)

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

        //NIVEL DIFICIL
        if(!hard){
            binding.progressBarBackGround.visibility=View.GONE
            binding.progressBar.visibility=View.GONE
        } else{
            binding.progressBarBackGround.visibility=View.VISIBLE
            binding.progressBar.visibility=View.VISIBLE
            binding.progressBar.max = 100
            progressBarCycle()
        }
        // El timer
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
            binding.buttonMenu.setOnClickListener {
                managePopupMenu()
            }
            binding.imageCoffeeCupS.setOnClickListener {
                managePopupSettings()
            }

    }

    override fun onStop() {
        super.onStop()
        finishAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        finishAll()
    }
    fun hardMode(){
        binding.progressBarBackGround.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE
        binding.lightBulb.visibility = View.VISIBLE
        binding.progressBar.max = 100
        progressBarCycle()
    }

    fun timer(){
        object : CountDownTimer(300000,1000){
            override fun onTick(millisUntilFinished: Long) {
                val minute = (millisUntilFinished / 1000) / 60
                val seconds = seconds(millisUntilFinished)
                binding.textViewTimer.setText("$minute:$seconds")
            }

            override fun onFinish() {
                    val intent = Intent(applicationContext, GameOverActivity::class.java).apply {}
                    startActivity(intent)
                    finishAll()
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
    fun managePopupSettings() {
        bindingPopupSettings = ItemPopupSettingsBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(bindingPopupSettings.root)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        bindingPopupSettings.buttonAccept.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java).apply {}
            startActivity(intent)
            finishAll()
        }
        bindingPopupSettings.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
    fun managePopupMenu(){
        bindingPopupMenu = ItemPopupMenuBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(bindingPopupMenu.root)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        bindingPopupMenu.buttonAccept.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java).apply {}
            startActivity(intent)
            finishAll()
        }
        bindingPopupMenu.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
    fun finishAll(){
        finish()
        thunderSound.stop()
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