package com.example.gardening

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    private lateinit var userName: TextView
    private lateinit var logout: Button
    private lateinit var gClient: GoogleSignInClient
    private lateinit var gOptions: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout = findViewById(R.id.logout)
        userName = findViewById(R.id.userName)

        gOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gClient = GoogleSignIn.getClient(this, gOptions)

        val gAccount: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (gAccount != null) {
            val gName = gAccount.displayName
            userName.text = gName
        }

        logout.setOnClickListener {
            gClient.signOut().addOnCompleteListener(this, OnCompleteListener {
                finish()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            })
        }
    }
}
