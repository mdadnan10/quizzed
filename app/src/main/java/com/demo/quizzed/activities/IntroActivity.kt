package com.demo.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.demo.quizzed.R
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val auth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            if (auth.currentUser != null) {
                redirected("MAIN")
                Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show()
            } else {
                redirected("LOGIN")
            }

        }, 1000)

    }

    private fun redirected(name: String) {
        val intent = when (name) {
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("No such path exists")
        }
        startActivity(intent)
        finish()
    }

}