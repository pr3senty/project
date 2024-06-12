package com.example.strelka

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val messages : List<Message>, val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    class SentViewHolder(view: View): RecyclerView.ViewHolder(view){
        val sentMessage: TextView = view.findViewById(R.id.sentMessage)
    }
    class ReceiveViewHolder(view: View): RecyclerView.ViewHolder(view){
        val receiveMessage: TextView = view.findViewById(R.id.receiveMessage)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.receive_message,parent,false)
            return ReceiveViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sent_message,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messages[position]

        if(messages[position].senderId == "send"){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messages[position]
        if(holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        }else{
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
            }

    }
}