package com.example.strelka

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class AuthActivity : AppCompatActivity()
{
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth

        if (auth.currentUser != null) {
            User.loadPoints()
            goToActivity(MenuActivity::class.java)
        }

        val emailEditText : EditText = findViewById(R.id.auth_email)
        val passwordEditText : EditText = findViewById(R.id.auth_password)
        val enterButton : ImageView = findViewById(R.id.auth_enter_button)
        val createAccountButton : Button = findViewById(R.id.auth_create_account_button)


        enterButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email == "" || password == "") return@setOnClickListener

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, resources.getString(R.string.SuccessfulAuth), Toast.LENGTH_LONG).show()

                        emailEditText.text.clear()
                        passwordEditText.text.clear()

                        Log.d("AuthSystem", "signInWithEmailAndPassword::success")
                        User.loadPoints()
                        goToActivity(MenuActivity::class.java)
                    }
                    else {
                        Log.w("AuthSystem", "signInWithEmailAndPassword::failure")
                        Toast.makeText(this, "${resources.getString(R.string.LoginError)} ${task.exception?.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        createAccountButton.setOnClickListener {
            goToActivity(RegistrationActivity::class.java)
        }
    }

    private fun goToActivity(activity : Class<*>) {
        val intent = Intent(this, activity)

        startActivity(intent)
    }
}