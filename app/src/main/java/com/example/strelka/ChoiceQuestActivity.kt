package com.example.strelka

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChoiceQuestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_choice_quest)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val quests = intent.getSerializableExtra("Quests") as ArrayList<Quest>
        var image = "change_"+intent.getStringExtra("Image")

        val buttonBack : ImageView = findViewById(R.id.choice_quest_back)
        val difficulty : TextView = findViewById(R.id.choice_quest_difficulty)
        val avatar : ImageView = findViewById(R.id.choice_quest_avatar)

        if (image== "change_pande") { image = "change_panda"}
        if (image == "change_catt") {image = "change_cat"}


        val imageId = resources.getIdentifier(
            image,
            "drawable",
            packageName
        )
        avatar.setImageResource(imageId)

        difficulty.text = intent.getStringExtra("Difficulty").toString()

        val questsChoice = arrayListOf<Quest>()
        val itemsList: RecyclerView = findViewById(R.id.quests_list)

        buttonBack.setOnClickListener {
            onBackPressed()
        }

        for (quest in quests)
            questsChoice.add(quest)

        itemsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        itemsList.adapter = ChoiceQuestAdapter(questsChoice,this)
    }
}