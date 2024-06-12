package com.example.strelka

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import androidx.viewpager2.adapter.FragmentStateAdapter


class MenuActivity : AppCompatActivity()
{
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        db = Firebase.firestore

        val profileButton: ImageView = findViewById(R.id.profile_btn)
        val exchangePointsButton : ImageView = findViewById(R.id.coins_btn)

        exchangePointsButton.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)

            startActivity(intent)
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)

            startActivity(intent)
        }

        db.collection("quests").whereNotEqualTo("start", "").get()
            .addOnSuccessListener { result ->
                val questsByDifficulty : HashMap<String, ArrayList<Quest>> = hashMapOf(
                    "Простая" to arrayListOf(),
                    "Средняя" to arrayListOf(),
                    "Сложная" to arrayListOf()
                )

                val tasks : ArrayList<Task<*>> = arrayListOf()

                for (doc in result.documents) {
                    tasks.add(db.document(doc["start"].toString()).get()
                        .addOnSuccessListener { startDoc ->
                            questsByDifficulty[doc["difficulty"].toString()]!!.add(Quest(
                                doc["name"].toString(),
                                doc["category"].toString(),
                                doc["difficulty"].toString(),
                                doc["legend"].toString(),
                                startDoc.data as HashMap<String, Any>,
                                doc["points"].toString()
                            ))
                        })
                }

                Tasks.whenAllComplete(tasks)
                    .addOnSuccessListener {
                        val quests = arrayListOf<QuestDifficulty>()
                        val itemsList: RecyclerView = findViewById(R.id.quests_main)





                        quests.add(QuestDifficulty("Панда Тодд","Простая","pande", questsByDifficulty["Простая"] ?: arrayListOf()))
                        quests.add(QuestDifficulty("Кот Симба","Средняя","catt", questsByDifficulty["Средняя"] ?: arrayListOf()))
                        quests.add(QuestDifficulty("Заяц Яшка","Сложная","rabbit", questsByDifficulty["Сложная"] ?: arrayListOf()))

                        itemsList.adapter = QuestDifficultyAdapter(quests,this)
                        itemsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

                        //itemsList.orientation = ViewPager2.ORIENTATION_HORIZONTAL

                    }
            }

    }

    override fun onStart()
    {
        super.onStart()

        val userPoints : TextView = findViewById(R.id.menu_user_points)

        db.collection("users").document(auth.currentUser!!.uid.toString()).get()
            .addOnSuccessListener { result ->
                userPoints.text = result.get("points").toString()
            }
    }
}