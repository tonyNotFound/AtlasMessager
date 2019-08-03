package com.example.atlasmessager

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.registration_act.*
import java.util.*

class RegistrationActivity : Activity() {
    var registrationProPic:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_act)
        Log.d("Registration", "Registration activity is being handled here")
        main_reg_registation_submit.setOnClickListener {
            createUserWithEmailAndPassword()
            uploadUserProfilePicture()
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
            registrationProPic = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, registrationProPic)
            main_reg_profile_pic.setImageBitmap(bitmap)
            main_reg_profile_picture.alpha = 0f
            Log.d("Registration" ,"User selected his/her profile picture")
//            val bitmapDrawable = BitmapDrawable(bitmap)
//            main_reg_profile_picture.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun uploadUserProfilePicture() {
        if (registrationProPic == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/usersProfilePicture/$filename")
        ref.putFile(registrationProPic!!).addOnSuccessListener {
            Log.d("Registration", "Successfully saved user profile pic in database ${it.metadata?.path}")
            ref.downloadUrl.addOnSuccessListener {
                val profilePicDownload = it.toString()
                saveEveryDetailsAboutUserInDB(profilePicDownload)
                Log.d("Registration" ,"And the download url is ${it} ")
            }
            Log.d("Registration" ,"And the download url is ${ref.downloadUrl} ")
            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createUserWithEmailAndPassword() {
        val userName = main_reg_username.text.toString()
        val email = main_reg_email.text.toString()
        val password = main_reg_password.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty() && userName.isNotEmpty())
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show()
                    Log.d("Registration", "User UID -> ${it.user.uid}")
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
    }
    private fun saveEveryDetailsAboutUserInDB(profilePicDownload:String) {
        if (null == FirebaseAuth.getInstance().uid) return
        val ref = FirebaseDatabase.getInstance().getReference("Users/${FirebaseAuth.getInstance().uid}")
        val userName = main_reg_username.text.toString()
        val email= main_reg_email.text.toString()
        val userdata = UserDetails(FirebaseAuth.getInstance().uid!!, userName, email, profilePicDownload)
        ref.setValue(userdata)
            .addOnSuccessListener {
            Toast.makeText(this, "saved successfully in db", Toast.LENGTH_SHORT).show()
            Log.d("Registration", "saved successfully in db")
            }
            .addOnFailureListener {
                Log.d("Registration", "something unexpected happened")
            }

    }

}

class UserDetails (val uid:String, val userName:String, val emial:String, val profilePicDownload:String)


