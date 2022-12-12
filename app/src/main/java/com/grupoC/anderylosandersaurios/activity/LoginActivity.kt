package com.grupoC.anderylosandersaurios.activity

import android.R
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.grupoC.anderylosandersaurios.activity.SettingsActivity
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.grupoC.anderylosandersaurios.databinding.ActivityLoginBinding
import com.grupoC.anderylosandersaurios.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser?=null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

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
                    sendToast()
                }
            }
        }
    }

    private fun validateData(email: String, password: String): Boolean{
        var valid = true
        if(email.isEmpty()){
            valid = false

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            valid = false
        }
        return valid
    }

    private fun createNewUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this)
        { task ->
            if(task.isSuccessful){
                sendToast()
            } else{
                sendToast()
            }
        }

    }

    private fun loginUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).
        addOnCompleteListener(this) { task ->
            if(task.isSuccessful){
                redirectActivity()
            } else{
                sendToast()
            }
        }
    }

    // TODO: IR A MAIN MENU ACTIVITY
    private fun redirectActivity(){
        val intentRedirect = Intent(this, MainMenuActivity::class.java)
        startActivity(intentRedirect)
        finish()
    }

    // TODO: MANDAR TOAST
    private fun sendToast(){

    }
}