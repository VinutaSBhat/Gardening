package com.example.gardening

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var signupRedirectText: TextView
    private lateinit var forgotPassword: TextView

    private lateinit var auth: FirebaseAuth
//    private lateinit var gOptions: GoogleSignInOptions
//    private lateinit var gClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEmail = findViewById(R.id.login_email)
        loginPassword = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        signupRedirectText = findViewById(R.id.signUpRedirectText)
        forgotPassword = findViewById(R.id.forgot_password)


        auth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val email = loginEmail.text.toString()
            val pass = loginPassword.text.toString()

            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!pass.isEmpty()) {
                    auth.signInWithEmailAndPassword(email, pass)
                        .addOnSuccessListener(OnSuccessListener<AuthResult> {
                            Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }).addOnFailureListener(OnFailureListener {
                            Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                        })
                } else {
                    loginPassword.setError("Empty fields are not allowed")
                }
            } else if (email.isEmpty()) {
                loginEmail.setError("Empty fields are not allowed")
            } else {
                loginEmail.setError("Please enter correct email")
            }
        }

        signupRedirectText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

        forgotPassword.setOnClickListener {
            val builder = AlertDialog.Builder(this@LoginActivity)
            val dialogView = layoutInflater.inflate(R.layout.dialog_forgot, null)
            val emailBox = dialogView.findViewById<EditText>(R.id.emailBox)

            builder.setView(dialogView)
            val dialog = builder.create()

            dialogView.findViewById<View>(R.id.btnReset).setOnClickListener {
                val userEmail = emailBox.text.toString()

                if (TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    Toast.makeText(this@LoginActivity, "Enter your registered email id", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Check your email", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(this@LoginActivity, "Unable to send, failed", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            dialogView.findViewById<View>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }
            if (dialog.window != null) {
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()
        }

//        gOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
//        gClient = GoogleSignIn.getClient(this, gOptions)

//        val gAccount: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
//        if (gAccount != null) {
//            finish()
//            val intent = Intent(this@LoginActivity, MainActivity::class.java)
//            startActivity(intent)
//        }

//        val activityResultLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
//                ActivityResultCallback { result: ActivityResult ->
//                    if (result.resultCode == Activity.RESULT_OK) {
//                        val data = result.data
//                        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//                        try {
//                            task.getResult(ApiException::class.java)
//                            finish()
//                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                            startActivity(intent)
//                        } catch (e: ApiException) {
//                            Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                })


    }
}
