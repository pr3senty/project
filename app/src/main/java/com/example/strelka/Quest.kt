package com.example.strelka

import java.io.Serializable

class Quest(val name : String, val category : String, val difficulty : String, val legend : String, val startEvent : HashMap<String, Any>, val points : String) : Serializable
{
    val serialVersionUID = 42131231
}