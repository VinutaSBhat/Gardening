package com.example.gardening

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gardening.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

//    private lateinit var userName: TextView

//    private lateinit var gClient: GoogleSignInClient
//    private lateinit var gOptions: GoogleSignInOptions
private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        replaceFragment(HomeFragment())
        binding.bottomNavigationView.background = null

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.market -> replaceFragment(MarketFragment())
                R.id.community -> replaceFragment(communityFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

            }
            true
        }
        binding.post.setOnClickListener{
            val intent = Intent(this@MainActivity, Post::class.java)
            startActivity(intent)

        }


//        userName = findViewById(R.id.userName)

//        gOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
//        gClient = GoogleSignIn.getClient(this, gOptions)

//        val gAccount: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
//        if (gAccount != null) {
//            val gName = gAccount.displayName
//            userName.text = gName
//        }

        }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

}


