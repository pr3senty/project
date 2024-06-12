package com.example.strelka

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MainActivity : AppCompatActivity()
{
    private lateinit var isAnimation: AnimationDrawable
    private lateinit var btn: Button
    private lateinit var img: ImageView

    private var isStart = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        img = findViewById(R.id.img)
        btn = findViewById(R.id.btn)
        img.setImageResource(R.drawable.animation_item)

        val buttonGo : Button = findViewById(R.id.auth_create_account_button)
        val buttonQuests : Button = findViewById(R.id.go_quests)

        isAnimation = img.drawable as AnimationDrawable
        btn.setBackgroundColor(Color.GREEN)

        btn.setOnClickListener {
            if (!isStart) {
                isAnimation.start()
                btn.text = "stop"
                isStart = true
                btn.setBackgroundColor(Color.RED)

            } else {
                isAnimation.stop()
                btn.text = "Start"
                isStart = false
                btn.setBackgroundColor(Color.GREEN)
            }
        }

        buttonGo.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)

            startActivity(intent)
        }

        buttonQuests.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)

            startActivity(intent)
        }

        if (!Python.isStarted())
            Python.start(AndroidPlatform(this))

    }
}