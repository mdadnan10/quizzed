package com.demo.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.demo.quizzed.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        progressBarLogin.visibility = View.GONE

        btnLogin.setOnClickListener {
            logIn()
        }

        btnForgotPass.setOnClickListener {
            Toast.makeText(this, "Will add later.", Toast.LENGTH_SHORT).show()
        }

        btnGoToSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun logIn() {
        val email = etEmailAddress.text.trim().toString()
        val password = etPassword.text.trim().toString()

        if (etEmailAddress.text.toString().isEmpty()) {
            etEmailAddress.error = "Please Enter Email Address"
        }
        if (etPassword.text.toString().isEmpty()) {
            etPassword.error = "please Enter Confirm Password"
            return
        }

        progressBarLogin.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { Task ->
            if (Task.isSuccessful) {
                val intentLogin = Intent(this, MainActivity::class.java)
                Toast.makeText(this, "Welcome back", Toast.LENGTH_LONG).show()
                startActivity(intentLogin)
                finish()
            } else {
                Toast.makeText(this, "Unable to Login ${Task.exception}", Toast.LENGTH_LONG).show()
                progressBarLogin.visibility = View.GONE
            }
        }
    }

}