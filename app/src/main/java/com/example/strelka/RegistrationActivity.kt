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
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class RegistrationActivity : AppCompatActivity()
{
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        db = Firebase.firestore

        val loginEditText : EditText = findViewById(R.id.registration_login)
        val emailEditText : EditText = findViewById(R.id.registration_email)
        val passwordEditText : EditText = findViewById(R.id.registration_password)
        val enterButton : ImageView = findViewById(R.id.registration_enter_button)
        val authButton : Button = findViewById(R.id.registration_auth_button)

        enterButton.setOnClickListener { registrationNewUser(loginEditText, emailEditText, passwordEditText) }

        authButton.setOnClickListener { goToActivity(AuthActivity::class.java) }
    }

    private fun goToActivity(activity : Class<*>) {
        val intent = Intent(this, activity)

        startActivity(intent)
    }

    private fun registrationNewUser(loginEditText : EditText, emailEditText : EditText, passwordEditText : EditText) {
        val login = loginEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (login == "" || email == "" || password == "") return

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val profile = userProfileChangeRequest {
                    displayName = login
                }

                auth.currentUser!!.updateProfile(profile)
                    .addOnSuccessListener {
                        val data = hashMapOf(
                            "nickname" to login,
                            "email" to email,
                            "avatars" to listOf("default"),
                            "points" to 0
                        )

                        db.collection("users").document(auth.currentUser?.uid.toString()).set(data)

                        loginEditText.text.clear()
                        emailEditText.text.clear()
                        passwordEditText.text.clear()

                        Toast.makeText(
                            this,
                            resources.getString(R.string.AccountCreated),
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("AuthSystem", "createUserWithEmail:success")

                        goToActivity(MenuActivity::class.java)
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, resources.getString(R.string.ErrorStandart), Toast.LENGTH_LONG).show()
                        Log.w("AuthSystem", "Failure with update user profile", exception)
                    }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "${resources.getString(R.string.CreatedError)}: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
                Log.w("AuthSystem", "createUserWithEmail:failure", exception)
            }
    }
}