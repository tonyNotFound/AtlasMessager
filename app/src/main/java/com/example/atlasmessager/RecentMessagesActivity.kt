package com.example.atlasmessager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.solver.widgets.Snapshot
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RecentMessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_messages)

        val currentUser = FirebaseAuth.getInstance().uid
        if (null == currentUser) {
            val intent = Intent(this, RegistrationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegistrationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            R.id.menu_action_new_message -> {
                // Do some fucking thing here
                Snackbar.make(this.findViewById(R.id.menu_action_new_message), "Implementation pending", Snackbar.LENGTH_LONG).show()
                val intent = Intent(this, NewMessageActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
