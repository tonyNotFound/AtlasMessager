package com.example.atlasmessager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.registration_act.*

class RegistrationActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_act)
        Log.d("Registration", "Registration activity is being handled here")
        val userName = main_reg_username.text.toString()
        val email = main_reg_email.text.toString()
        val password = main_reg_password.text.toString()
        

        main_reg_already_have_an_account.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply{
                putExtra("Message", "User already have an account")
            }
            startActivity(intent)
        }

        }
}
