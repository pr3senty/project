package com.example.strelka

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

object User
{
    var points : Long = 0
        set(value) {
            field = value
            onPointsChanged.invoke()
        }

    val onPointsChanged : Action = Action()

    fun loadPoints() {
        val db = Firebase.firestore
        val auth = Firebase.auth

        db.collection("users").document(auth.uid.toString()).get()
            .addOnSuccessListener { result ->
                points = result!!["points"] as Long
            }
    }
}