package com.example.kids

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.kids.MainActivity.Companion.lang


class Content : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        getContent()
    }

    private fun fillAreas(content: ContentData) {
        val titleTextView: TextView? = findViewById(R.id.contentTitle)
        val contentTextView: TextView? = findViewById(R.id.contentText)
        val current = resources.configuration.locale

        if (titleTextView != null) {
            if (lang == "tr") {
                titleTextView.text = content.title_tr
            } else {
                titleTextView.text = content.title_en
            }

            titleTextView.setBackgroundColor(Color.parseColor("#73000000"));
        }

        if (contentTextView != null) {
            if (lang == "tr") {
                contentTextView.text = content.text_tr
            } else {
                contentTextView.text = content.text_en
            }
            contentTextView.setBackgroundColor(Color.parseColor("#73000000"));
        }
    }

    private fun getContent() {
        val uuid: String? = intent.getStringExtra("uuid")

        val url = "http:10.0.2.2:8000/api/tales/content/$lang/$uuid"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val res: List<ContentData>? = parseToArray(response)
                if (res != null) {
                    findViewById<ProgressBar>(R.id.progressBar2)?.visibility = View.GONE;
                    fillAreas(res[0])
                }
            },
            Response.ErrorListener { error -> println(error) })


        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int {
                return 50000
            }

            override fun getCurrentRetryCount(): Int {
                return 50000
            }

            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {
            }
        }

        queue.add(stringRequest)
    }

    private fun parseToArray(jsonStr: String): List<ContentData>? {
        return Klaxon().parseArray( jsonStr )
    }

    data class ContentData(
        val uuid: String,
        val text_tr: String = "",
        val title_tr: String = "",
        val title_en: String = "",
        val text_en: String = "",
        val isActive: Int,
        val created_at: String,
        val updated_at: String
    )
}