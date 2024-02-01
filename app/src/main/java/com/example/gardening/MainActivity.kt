package com.example.gardening

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gardening.databinding.ActivityMainBinding


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
                R.id.cart -> replaceFragment(CartFragment())
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


