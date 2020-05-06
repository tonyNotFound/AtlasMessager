package com.example.atlasmessager

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_act.*

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_act)
        login_submit.setOnClickListener {
            val username = login_username.text.toString()
            val password = login_password.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Snackbar.make(it, "Please fill the username and password field..!", Snackbar.LENGTH_SHORT).show()
//                Toast.makeText(this, "fill the fucking username and the password field", Toast.LENGTH_LONG).show()
            } else {
//                FirebaseAuth.getInstance().signInWithEmailAndPassword()
            }
        }
    }


}