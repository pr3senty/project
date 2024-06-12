package com.example.strelka

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnimalImageAdapter(val animalImages : List<AnimalImage>, val context: Context): RecyclerView.Adapter<AnimalImageAdapter.MyViewHolder1>() {

    class MyViewHolder1(view: View ): RecyclerView.ViewHolder(view){
        val avatar : ImageView = view.findViewById(R.id.choice_quest_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.animals_adaptr,parent,false)
        return MyViewHolder1(view)
    }

    override fun getItemCount(): Int {
        return animalImages.size
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {
        val imageId = context.resources.getIdentifier(
            animalImages[position].image,
            "drawable",
            context.packageName
        )

        holder.avatar.setImageResource(imageId)


    }


}