package com.example.strelka


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class animals_adp(val items : List<QuestDifficulty>, val context: Context): RecyclerView.Adapter<animals_adp.MyViewHolder>() {

    class MyViewHolder(view: View ): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.choice_quest_avatar)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.animals_adaptr,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        val imageId = context.resources.getIdentifier(
            items[position].image,
            "drawable",
            context.packageName
        )
        holder.image.setImageResource(imageId)
//        holder.image.setOnClickListener {
//            val intent = Intent(context,PostDescript::class.java)
//            intent.putExtra("PostName",posts[position].nm_post)
//            intent.putExtra("PostImage",posts[position].image)
//            intent.putExtra("PostText",posts[position].desc)
//            intent.putExtra("UserName",posts[position].user_name)
//
//
//            context.startActivity(intent)
//
//        }
    }
}