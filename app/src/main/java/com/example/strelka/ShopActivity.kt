package com.example.strelka

import android.os.Bundle
import android.widget.AbsListView.RecyclerListener
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class ShopActivity : AppCompatActivity()
{
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        auth = Firebase.auth
        db = Firebase.firestore

        val avatars : RecyclerView = findViewById(R.id.shop_avatars)

        updatePoints()

        val animals : List<Animal> = listOf(
            Animal(333, "change_cat"),
            Animal(444, "change_panda"),
            Animal(555, "change_rabbit")
        )

        avatars.adapter = AnimalAdapter(animals, this)
        avatars.layoutManager = GridLayoutManager(this,2)

        val back_btn: ImageView = findViewById(R.id.imageView6)
        back_btn.setOnClickListener{
            onBackPressed()
        }

        User.onPointsChanged += ::updatePoints
    }

    fun updatePoints() {
        val userPointsText : TextView = findViewById(R.id.shop_user_points)

        userPointsText.text = User.points.toString()
    }
}