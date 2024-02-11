package com.example.gardening

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class SignUpActivity : AppCompatActivity() {

    private  var auth = FirebaseAuth.getInstance()
    private lateinit var signupEmail: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signupButton: Button
    private lateinit var loginRedirectText: TextView
    private lateinit var username: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val sharedPref = getSharedPreferences("addName", Context.MODE_PRIVATE)
        auth = FirebaseAuth.getInstance()
        signupEmail = findViewById(R.id.signup_email)
        signupPassword = findViewById(R.id.signup_password)
        signupButton = findViewById(R.id.signup_button)
        loginRedirectText = findViewById(R.id.loginRedirectText)
        username=findViewById(R.id.usernamee)
        var edit = sharedPref.edit()
        signupButton.setOnClickListener {



            val user = signupEmail.text.toString().trim()
            val pass = signupPassword.text.toString().trim()

            if (user.isEmpty()) {
                signupEmail.error = "Email cannot be empty"
            }
            if (pass.isEmpty()) {
                signupPassword.error = "Password cannot be empty"
            } else {
                auth.createUserWithEmailAndPassword(user, pass)
                    .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            edit.putString("username",username.text.toString())
                            edit.commit()

                            Toast.makeText(
                                this@SignUpActivity,
                                "SignUp Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                        } else {
                            Toast.makeText(
                                this@SignUpActivity,
                                "SignUp Failed" + task.exception?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }

        loginRedirectText.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        }
    }
}