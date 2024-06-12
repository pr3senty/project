package com.example.strelka

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class EventActivity : AppCompatActivity()
{
    private lateinit var db : FirebaseFirestore
    private lateinit var wayData : HashMap<String, Any>
    private lateinit var badWayData : HashMap<String, Any>
    private lateinit var trueWayData : HashMap<String, Any>
    private lateinit var context : Context
    private lateinit var messagesList : RecyclerView

    private lateinit var gpt : GPTManager

    private var attemptsRemain = 2 // + 1
    private val messages : ArrayList<Message> = arrayListOf()
    private var questPoints = 0
    private var message = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = Firebase.firestore
        gpt = GPTManager(this)
        context = this

        val data = intent.getSerializableExtra("EventData") as HashMap<String, Any>
        questPoints = Integer.parseInt(intent.getStringExtra("QuestPoints").toString())
        messagesList = findViewById(R.id.messagesList)

        changeAvatar()

        db.document(data["true_way"].toString()).get()
            .addOnSuccessListener { result ->
                trueWayData = result.data as HashMap<String, Any>

        }

        db.document(data["bad_way"].toString()).get()
            .addOnSuccessListener { result ->
                badWayData = result.data as HashMap<String, Any>

                wayData = badWayData
            }


        newAnswer(data["text"].toString())
        gpt.start(data["text"].toString(), data["answer"].toString())

        gpt.getAnswer += ::newAnswer
        gpt.getCheckAnswer += ::checkAnswer

        val inputText : EditText = findViewById(R.id.editText)
        val sendButton: FrameLayout = findViewById(R.id.layoutSend)


        sendButton.setOnClickListener{
            message = inputText.text.toString()
            sendButton.isClickable = false
            inputText.text.clear()

            if (message == "")
                return@setOnClickListener

            messages.add(Message(message, "send"))
            messagesList.adapter = ChatAdapter(messages, context)
            messagesList.scrollToPosition(messages.size - 1)

            gpt.checkUserResponse(message, data["answer"].toString())
        }
    }

    private fun checkAnswer(out : Boolean) {
        val inputText : EditText = findViewById(R.id.editText)

        if (out)
        {
            wayData = trueWayData

            goToNextEvent(questPoints.toString(), inputText)
        } else if (attemptsRemain-- > 0)
        {
            val attemptsText : TextView = findViewById(R.id.event_attempts)
            val infoText : TextView = findViewById(R.id.event_attempts_info)

            attemptsText.text = attemptsRemain.toString()

            if (attemptsRemain == 0)
                infoText.text = "Введите ответ"

            gpt.getGPTResponse(message)
        }
        else
            goToNextEvent(questPoints.toString(), inputText)
    }

    private fun newAnswer(answer: String) {
        val sendButton: FrameLayout = findViewById(R.id.layoutSend)

        messages.add(Message(answer, "receive"))
        messagesList.layoutManager = LinearLayoutManager(context)
        messagesList.adapter = ChatAdapter(messages, context)

        messagesList.scrollToPosition(messages.count() - 1)

        sendButton.isClickable = true
    }


    private fun goToNextEvent(questPoints : String, inputText: EditText) {

        val intent = if (wayData["end"] == true)
            Intent(this, QuestFinishActivity::class.java)
        else
            Intent(this, EventActivity::class.java)


        intent.putExtra("EventData", wayData)
        intent.putExtra("QuestPoints", questPoints)

        wayData = badWayData

        inputText.isEnabled = false
        startActivity(intent)
    }

    private fun changeAvatar() {
        val difficulty = intent.getStringExtra("Difficulty")
        val avatar : ImageView = findViewById(R.id.event_avatar)
        var image = "deff_panda"

        if (difficulty == "Средняя")
            image = "deff_cat"
        if (difficulty == "Сложная")
            image = "deff_rabbit"

        val imageId = resources.getIdentifier(
            image,
            "drawable",
            packageName
        )

        avatar.setImageResource(imageId)
    }
}