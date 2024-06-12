package com.example.strelka

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class exchange_points : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_points)

        val trafiks = arrayListOf<trafik_info_data>()
        val itemsList: RecyclerView = findViewById(R.id.shop_avatars)

        trafiks.add(trafik_info_data("TikTok","tiktok",60))
        trafiks.add(trafik_info_data("ВКонтакте","vk",60))
        trafiks.add(trafik_info_data("YouTube","youtube",60))

        trafiks.add(trafik_info_data("TikTok","tiktok",120))
        trafiks.add(trafik_info_data("ВКонтакте","vk",120))
        trafiks.add(trafik_info_data("YouTube","youtube",120))


        itemsList.layoutManager = GridLayoutManager(this,2)

        itemsList.adapter = trafik_adapter(trafiks,this)

        val back_btn: ImageView = findViewById(R.id.imageView6)
        back_btn.setOnClickListener{
            onBackPressed()
        }

    }
}