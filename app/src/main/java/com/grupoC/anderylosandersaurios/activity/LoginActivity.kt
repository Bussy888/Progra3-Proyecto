package com.grupoC.anderylosandersaurios.activity

import android.R
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.grupoC.anderylosandersaurios.databinding.ActivityLoginBinding
import com.grupoC.anderylosandersaurios.databinding.ItemPopupSettingsBinding
import com.grupoC.anderylosandersaurios.databinding.ItemPopupTutorialBinding

class LoginActivity : AppCompatActivity() {

    companion object{
        var VOLUME: Int = 0
        var VIBRATION = true
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager
    var currentUser: FirebaseUser?=null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initVolumen()

        initUi()
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

    private fun initUi(){
        auth=FirebaseAuth.getInstance()
        currentUser=auth.currentUser

        binding.run{
            buttonLogIn.setOnClickListener{
                val email=editEmail.text.toString()
                val password = editPassword.text.toString()
                if(validateData(email, password)){
                    loginUser(email, password)
                }
            }
            buttonNewUser.setOnClickListener{
                val email=editEmail.text.toString()
                val password=editPassword.text.toString()
                if(validateData(email, password)){
                    createNewUser(email,password)
                }
            }
            play.setOnClickListener {
                if(currentUser != null){
                    redirectActivity()
                } else{
                    sendToast(getString(com.grupoC.anderylosandersaurios.R.string.login_to_an_account))
                }
            }
        }
    }

    private fun validateData(email: String, password: String): Boolean{
        var valid = true
        if(email.isEmpty()){
            valid = false
            sendToast(getString(com.grupoC.anderylosandersaurios.R.string.enter_an_email_address))
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            valid = false
            sendToast(getString(com.grupoC.anderylosandersaurios.R.string.invalid_email))
        } else if (!validPassword(password)){
            valid = false
            sendToast(getString(com.grupoC.anderylosandersaurios.R.string.invalid_password))
        }
        return valid
    }

    private fun createNewUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this)
        { task ->
            if(task.isSuccessful){
                sendToast(getString(com.grupoC.anderylosandersaurios.R.string.new_user_created))
            } else{
                sendToast(getString(com.grupoC.anderylosandersaurios.R.string.error_login))
            }
        }

    }

    private fun loginUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).
        addOnCompleteListener(this) { task ->
            if(task.isSuccessful){
                redirectActivity()
            } else{
                sendToast(getString(com.grupoC.anderylosandersaurios.R.string.error_login))
            }
        }
    }

    private fun redirectActivity(){
        val intentRedirect = Intent(this, MainMenuActivity::class.java)
        intentRedirect.putExtra("POPUP", true)
        startActivity(intentRedirect)
        finish()
    }


    private fun sendToast(text:String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    private fun validPassword(password:String):Boolean {
        return Regex(pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$").matches(password)
    }

    private fun initVolumen() {
        mediaPlayer = MediaPlayer.create(this, com.grupoC.anderylosandersaurios.R.raw.the_consequence_of_style)
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        VOLUME = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
    }

    override fun onPause() {
        mediaPlayer.stop()
        super.onPause()
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        super.onDestroy()
    }

    override fun onStop() {
        mediaPlayer.stop()
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        mediaPlayer.start()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }
}
