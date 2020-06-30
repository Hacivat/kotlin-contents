package com.example.kids

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        var lang: String = "tr"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getItems();
    }

     private fun mapItems(titles: String) {
        println(parseToArray(titles))

        val listTitles: List<Title>? = parseToArray(titles)

         if (listTitles != null) {
             for (item in listTitles) {
                 //image config
                 val imageView = ImageView(this)
                 val lpImage = RelativeLayout.LayoutParams(
                     RelativeLayout.LayoutParams.MATCH_PARENT,
                     RelativeLayout.LayoutParams.WRAP_CONTENT
                 )
                 lpImage.setMargins(-25, 25, 0, 0)
                 imageView.layoutParams = lpImage
                 imageView.adjustViewBounds = true
                 val imgResId = R.drawable.item
                 imageView.setImageResource(imgResId)

                 //text config
                 val textView = TextView(this)
                 val lpText = RelativeLayout.LayoutParams(
                     RelativeLayout.LayoutParams.WRAP_CONTENT,
                     RelativeLayout.LayoutParams.WRAP_CONTENT
                 )
                 val displayMetrics = DisplayMetrics()
                 windowManager.defaultDisplay.getMetrics(displayMetrics)
                 var width = displayMetrics.widthPixels
                 var height = displayMetrics.heightPixels

                 var padded: Boolean = false
                 if (height <= 800) {
                     textView.setPadding(20, 63,0,0)
                     padded = true
                 }
                 if (height <= 2100 && !padded) {
                     textView.setPadding(20, 120,0,0)
                     padded = true
                 }
                 if (!padded) { textView.setPadding(20, 140,0,0) }

                 textView.layoutParams = lpText
                 textView.text = item.title
                 textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F);

                 //item layout config
                 val relativeLayout = RelativeLayout(this);
                 val lpRelativeLayout = RelativeLayout.LayoutParams(
                     RelativeLayout.LayoutParams.MATCH_PARENT,
                     RelativeLayout.LayoutParams.WRAP_CONTENT
                 )
                 relativeLayout.layoutParams = lpRelativeLayout
                 relativeLayout.addView(imageView)
                 relativeLayout.addView(textView)

                 relativeLayout.isClickable = true

                 relativeLayout.setOnClickListener {
                     startActivity(
                         Intent(
                             this,
                             Content::class.java
                         ).apply { putExtra("uuid", item.uuid) }
                     )
                 }

                 linearLayout?.addView(relativeLayout)
             }
         }
    }

    private fun parseToArray(jsonStr: String): List<Title>? {
        return Klaxon().parseArray( jsonStr )
    }
    data class Title(val uuid: String, val title: String)

    private fun getItems() {
        val url = "http:10.0.2.2:8000/api/tales/titles/$lang"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                findViewById<ProgressBar>(R.id.progressBar1)?.visibility = View.GONE;
                mapItems(response)
            },
            Response.ErrorListener { error -> println(error) })

        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int { return 50000 }
            override fun getCurrentRetryCount(): Int { return 50000 }
            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) { }
        }

        queue.add(stringRequest)
    }

    fun search(view: View) {
        val searchEditText: EditText? = findViewById(R.id.searchEditText)
        val keyword: String? = searchEditText?.text.toString()
        val url = "http:10.0.2.2:8000/api/tales/titles/$lang/$keyword"
        val queue = Volley.newRequestQueue(this)

        findViewById<LinearLayout>(R.id.linearLayout)?.removeAllViews();
        findViewById<ProgressBar>(R.id.progressBar1)?.visibility = View.VISIBLE;

        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                findViewById<ProgressBar>(R.id.progressBar1)?.visibility = View.GONE;
                mapItems(response)
            },
            Response.ErrorListener { error -> println(error) })

        queue.add(stringRequest)
    }
}
