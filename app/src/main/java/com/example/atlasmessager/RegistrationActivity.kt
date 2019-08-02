package com.example.atlasmessager

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
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
            createUserWithEmailAndPassword()
        }
        main_reg_already_have_an_account.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        main_reg_profile_picture.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && data != null) {
            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            main_reg_profile_pic.setImageBitmap(bitmap)
//            val bitmapDrawable = BitmapDrawable(bitmap)
//            main_reg_profile_picture.setBackgroundDrawable(bitmapDrawable)
            main_reg_profile_picture.alpha = 0f
        }
    }
    private fun createUserWithEmailAndPassword() {
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
}
