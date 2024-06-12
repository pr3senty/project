package com.example.strelka

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class GPTManager(context: Context) : ViewModel()
{
    private lateinit var gptModule : PyObject

    val getAnswer : Event<String> = Event()
    val getCheckAnswer : Event<Boolean> = Event()

    private var started : Boolean = false
    private var messages : ArrayList<Map<String, String>> = arrayListOf()

    init
    {
        if (!Python.isStarted())
            Python.start(AndroidPlatform(context))
    }

    fun start(target : String, answer : String) {
        messages.add(
            mapOf(
                "role" to "system",
                "text" to "Ты персональный помощник команды \"пол бэнки\". Тебе будет необходимо помочь ответить пользователю на вопрос: \"$target\"." +
                        "Ты должна общаться лишь на тему этого вопроса и уходить от других тем. Если тебе был задан не вопрос то проигнорируй."
        ))

        started = true
        gptModule = Python.getInstance().getModule("gpt_connect")
    }

    fun getGPTResponse(response : String) {

        viewModelScope.launch {

            val answer : String = withContext(Dispatchers.IO) {
                messages.add(
                    mapOf(
                        "role" to "user",
                        "text" to response
                    )
                )

                val json = JSONArray(messages)

                val response = gptModule.callAttr("get_gpt_response_by_query", json.toString())

                val gptAnswer = JSONObject(
                    JSONObject(
                        (JSONObject(response.toString()).get("alternatives") as JSONArray)[0].toString()
                    )
                        .get("message").toString()
                ).get("text").toString()


                messages.add(
                    mapOf(
                        "role" to "assistant",
                        "text" to gptAnswer
                    )
                )

                return@withContext gptAnswer
            }

            getAnswer.invoke(answer)
        }
    }

    fun checkUserResponse(response: String, answer: String) {

        viewModelScope.launch {

            val out = withContext(Dispatchers.IO) {
                val messages = listOf(
                    mapOf(
                        "role" to "system",
                        "text" to "Ты профессионально проверяешь похожи ли две фразы по значению. Тебе будут даны" +
                                "две фразы и необходимо ответить близки ли они друг к другу по смыслу. Первая фраза: " +
                                "\"$response\". Вторая: \"$answer\". Скажи одним словом да или нет несут ли они в себе одно значение."
                    )
                )

                val json = JSONArray(messages)

                val response = gptModule.callAttr("get_gpt_response_by_query", json.toString())

                val gptAnswer = JSONObject(
                    JSONObject(
                        (JSONObject(response.toString()).get("alternatives") as JSONArray)[0].toString()
                    )
                        .get("message").toString()
                ).get("text").toString()


                if (gptAnswer.lowercase().substring(0, 2) == "да") {
                    Thread.sleep(5000)
                    return@withContext true
                }

                return@withContext false
            }

            getCheckAnswer.invoke(out)
        }
    }
}