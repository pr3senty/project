package com.example.strelka

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestDifficultyAdapter(val items : List<QuestDifficulty>, val context: Context): RecyclerView.Adapter<QuestDifficultyAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View ): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.main_img)
        val name: TextView = view.findViewById(R.id.name_character)
        val difficulty: TextView = view.findViewById(R.id.diff_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_quests_chose,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.difficulty.text = items[position].difficulty

        val imageId = context.resources.getIdentifier(
            items[position].image,
            "drawable",
            context.packageName
        )
        holder.image.setImageResource(imageId)

        holder.image.setOnClickListener {
            val intent = Intent(context, ChoiceQuestActivity::class.java)

            intent.putExtra("Quests", items[position].quests)
            intent.putExtra("Difficulty", items[position].difficulty)
            intent.putExtra("Image", items[position].image)

            context.startActivity(intent)
        }
    }
}