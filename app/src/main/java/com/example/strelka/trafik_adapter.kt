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

class trafik_adapter(val items : List<trafik_info_data>,val context: Context): RecyclerView.Adapter<trafik_adapter.MyViewHolder>() {

    class MyViewHolder(view: View ): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.main_img_trafic)
        val name: TextView = view.findViewById(R.id.text_info_trafik)
        val cost: Int = 0


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trafik_adapter,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = items[position].name+" на "+items[position].cost+" минут"
        val cost = items[position].cost


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