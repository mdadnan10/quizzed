package com.demo.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.demo.quizzed.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        progressBarSignUp.visibility = View.GONE

        btnSignUp.setOnClickListener {
            signIn()
        }

        btnGoToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun signIn() {

        val name = etName.text.trim().toString()
        val email = etEmailSignup.text.trim().toString()
        val password = etPasswordSignup.text.trim().toString()
        val confirmPassword = etConfirmPasswordSignup.text.trim().toString()

//        if (etName.text.toString().isEmpty()) {
//            etName.error = "Please Enter Your Name"
//        }
        if (etEmailSignup.text.toString().isEmpty()) {
            etEmailSignup.error = "Please Enter Email Address"
        }
        if (etPasswordSignup.text.toString().isEmpty()) {
            etPasswordSignup.error = "please Enter Confirm Password"
        }
        if (etConfirmPasswordSignup.text.toString().isEmpty()) {
            etConfirmPasswordSignup.error = "Please Enter Confirm Password"
            return
        }
        if (etPasswordSignup.text.toString().length < 6) {
            etPasswordSignup.error = "Password must be atleast 6 Character"
        }
        if (etConfirmPasswordSignup.text.toString().length < 6) {
            etConfirmPasswordSignup.error = "Password must be atleast 6 Character"
            return
        }
        if (password != confirmPassword) {
            Toast.makeText(
                this,
                "Password and Confirm Password Should be same.",
                Toast.LENGTH_LONG
            )
                .show()
            return
        }

        progressBarSignUp.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { Task ->
            if (Task.isSuccessful) {
                val intentLogin = Intent(this, MainActivity::class.java)
                startActivity(intentLogin)
                Toast.makeText(this, "Welcome to Quizzed", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Log.d("email", "Error is ${Task.exception}")
                Toast.makeText(this, "Error ${Task.exception}", Toast.LENGTH_LONG)
                    .show()
                progressBarSignUp.visibility = View.GONE
            }
        }

    }

}