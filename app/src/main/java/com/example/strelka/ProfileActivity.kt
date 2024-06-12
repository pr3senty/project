package com.example.strelka

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class ProfileActivity : AppCompatActivity()
{
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        db = Firebase.firestore

        val animals : ArrayList<AnimalImage> = arrayListOf()
        val userAvatars : RecyclerView = findViewById(R.id.user_avatars)
        val points : TextView = findViewById(R.id.profile_user_points)

        db.collection("users").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { result ->
                for (animalImage in (result.get("avatars") as List<String>)) {
                    if (animalImage == "default")
                        continue

                    animals.add(AnimalImage(animalImage))
                }

                points.text = result.get("points").toString()
                userAvatars.adapter = AnimalImageAdapter(animals, this)
                userAvatars.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
            }

        val back_btn: ImageView = findViewById(R.id.choice_quest_back)
        back_btn.setOnClickListener{
            onBackPressed()
        }
    }
}