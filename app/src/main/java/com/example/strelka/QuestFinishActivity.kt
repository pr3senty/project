package com.example.strelka

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class QuestFinishActivity : AppCompatActivity()
{
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quest_finish)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        db = Firebase.firestore

        var questPoints = 0
        val data = intent.getSerializableExtra("EventData") as HashMap<String, Any>

        val goodEndingText : TextView = findViewById(R.id.good_ending)
        val badEndingText : TextView = findViewById(R.id.bad_ending)
        val finishExtra : TextView = findViewById(R.id.quest_finish_extra)
        val award : TextView = findViewById(R.id.quest_finish_award)
        val backToMenuButton : Button = findViewById(R.id.quest_finish_back_to_menu)

        finishExtra.movementMethod = ScrollingMovementMethod()

        if (data["win"] == true) {
            badEndingText.visibility = View.GONE
            goodEndingText.visibility = View.VISIBLE

            backToMenuButton.isClickable = false

            questPoints = Integer.parseInt(intent.getStringExtra("QuestPoints").toString())

            db.collection("users").document(auth.currentUser!!.uid.toString()).get()
                .addOnSuccessListener { result ->
                    db.collection("users").document(auth.currentUser!!.uid.toString())
                        .update("points", User.points + questPoints)
                        .addOnSuccessListener {
                            backToMenuButton.isClickable = true
                            User.points += questPoints
                        }
                }
        }

        finishExtra.text = data["text"].toString()
        award.text = questPoints.toString()

        backToMenuButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)

            startActivity(intent)
        }
    }
}