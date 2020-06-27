package com.example.kids

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
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
                 lpText.setMargins(75, 120, 0, 0)
                 textView.layoutParams = lpText
                 textView.text = item.title
                 textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F);

                 //item layout config
                 val relativeLayout = RelativeLayout(this);
                 val lpRelativeLayout = RelativeLayout.LayoutParams(
                     RelativeLayout.LayoutParams.MATCH_PARENT,
                     RelativeLayout.LayoutParams.WRAP_CONTENT
                 )
                 relativeLayout.layoutParams = lpRelativeLayout
                 relativeLayout.addView(imageView)
                 relativeLayout.addView(textView)

                 linearLayout?.addView(relativeLayout)
             }
         }
    }

    private fun parseToArray(jsonStr: String): List<Title>? {
        return Klaxon().parseArray( jsonStr )
    }
    data class Title(val uuid: String, val title: String)

    private fun getItems() {
        val url = "http:10.0.2.2:8000/api/tales/titles/tr"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                mapItems(response)
            },
            Response.ErrorListener { error -> println(error) })

        queue.add(stringRequest)
    }

    fun openTalePage(view: View) {
        val intent = Intent(this, Tales::class.java)
        startActivity(intent)
    }
}

