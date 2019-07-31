package com.example.atlasmessager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.registration_act.*
import java.util.*

class RegistrationActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_act)
        Log.d("Registration", "Registration activity is being handled here")
        main_reg_registation_submit.setOnClickListener {
            val userName = main_reg_username.text.toString()
            val email = main_reg_email.text.toString()
            val password = main_reg_password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty())
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show()
                        Log.d("LogInSuccess", "User UID -> ${it.user.uid}")
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show()
                    }
        }
        main_reg_already_have_an_account.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply{
                putExtra("Message", "User already have an account")
            }
            startActivity(intent)
        }

        }
}
