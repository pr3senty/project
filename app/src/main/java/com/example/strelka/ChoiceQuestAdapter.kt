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

class ChoiceQuestAdapter(val questList: List<Quest>, val context: Context): RecyclerView.Adapter<ChoiceQuestAdapter.MyViewHolder1>() {

    class MyViewHolder1(view: View ): RecyclerView.ViewHolder(view){
        val nameQuestView: TextView = view.findViewById(R.id.nameQuest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.name_quest,parent,false)
        return MyViewHolder1(view)
    }

    override fun getItemCount(): Int {
        return questList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {
        holder.nameQuestView.text = questList[position].name

        holder.nameQuestView.setOnClickListener {
            val intent = Intent(context, QuestActivity::class.java)

            intent.putExtra("Quest", questList[position])

            context.startActivity(intent)
        }
    }


}