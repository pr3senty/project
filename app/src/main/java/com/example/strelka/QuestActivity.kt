package com.example.strelka

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.DocumentSnapshot
import org.w3c.dom.Document

class QuestActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quest)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val quest = intent.getSerializableExtra("Quest") as Quest

        val textQuestName : TextView = findViewById(R.id.quest_name)
        val textQuestDifficulty : TextView = findViewById(R.id.quest_difficulty)
        val textQuestCategory : TextView = findViewById(R.id.quest_category)
        val textQuestLegend : TextView = findViewById(R.id.quest_legend)
        val textQuestPoints : TextView = findViewById(R.id.quest_points)
        val startQuestButton : TextView = findViewById(R.id.start_quest_button)

        textQuestName.text = quest.name
        textQuestDifficulty.text = quest.difficulty
        textQuestCategory.text = quest.category
        textQuestLegend.text = quest.legend
        textQuestPoints.text = quest.points

        textQuestLegend.movementMethod = ScrollingMovementMethod()

        startQuestButton.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)

            intent.putExtra("EventData", quest.startEvent)
            intent.putExtra("QuestPoints", quest.points)
            intent.putExtra("Difficulty", quest.difficulty)

            startActivity(intent)
        }
    }


}