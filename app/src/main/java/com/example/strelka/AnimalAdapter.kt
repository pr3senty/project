package com.example.strelka

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AnimalAdapter(val animals : List<Animal>, val context: Context): RecyclerView.Adapter<AnimalAdapter.MyViewHolder1>() {
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    class MyViewHolder1(view: View ): RecyclerView.ViewHolder(view){
        val avatar : ImageView = view.findViewById(R.id.buy_avatar)
        val buyButton : Button = view.findViewById(R.id.animal_buy_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.animal_in_list, parent,false)
        return MyViewHolder1(view)
    }

    override fun getItemCount(): Int {
        return animals.size
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {
        auth = Firebase.auth
        db = Firebase.firestore

        val imageId = context.resources.getIdentifier(
            animals[position].image,
            "drawable",
            context.packageName
        )

        db.collection("users").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { result ->
                val avatars = result.get("avatars") as ArrayList<String>


                if (animals[position].image in avatars)
                    holder.buyButton.isClickable = false
            }

        holder.avatar.setImageResource(imageId)
        holder.buyButton.text = animals[position].cost.toString()

        holder.buyButton.setOnClickListener {
            if (User.points < animals[position].cost)
            {
                Toast.makeText(context, "Не хватает баллов!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            holder.buyButton.isClickable = false

            db.collection("users").document(auth.currentUser!!.uid).get()
                .addOnSuccessListener { result ->
                    val avatars = result.get("avatars") as ArrayList<String>

                    avatars.add(animals[position].image)

                    db.collection("users").document(auth.currentUser!!.uid)
                        .update("avatars", avatars)
                        .addOnSuccessListener {
                            db.collection("users").document(auth.currentUser!!.uid)
                                .update("points", User.points - animals[position].cost)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Успешная покупка!", Toast.LENGTH_LONG).show()
                                    User.points -= animals[position].cost
                                }
                        }

                }
        }
    }


}
