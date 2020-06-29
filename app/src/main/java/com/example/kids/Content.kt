package com.example.kids

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class Content : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        getContent()
    }

    private fun fillAreas(content: ContentData) {
        val titleTextView: TextView? = findViewById(R.id.contentTitle)

        if (titleTextView != null) {
            titleTextView.text = content.title_tr
        }
    }

    private fun getContent() {
        val uuid: String? = intent.getStringExtra("uuid")

        val url = "http:10.0.2.2:8000/api/tales/content/$uuid/tr"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val res: List<ContentData>? = parseToArray(response)
                println(res?.get(0))
                if (res != null) {
                    fillAreas(res[0])
                }
            },
            Response.ErrorListener { error -> println(error) })

        queue.add(stringRequest)
    }

    private fun parseToArray(jsonStr: String): List<ContentData>? {
        return Klaxon().parseArray( jsonStr )
    }

    data class ContentData(
        val uuid: String,
        val text_tr: String?,
        val title_tr: String?,
        val isActive: Int,
        val created_at: String,
        val updated_at: String
    )
}