package com.example.kids

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapItems();
    }

    fun mapItems() {
        for (i in 0..9) {
            val textView = TextView(this)
            val imageView = ImageView(this)
            textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            val lpImage = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            lpImage.setMargins(-25, 25, 0, 0)
            imageView.layoutParams = lpImage
            imageView.adjustViewBounds = true

            val imgResId = R.drawable.item
            imageView.setImageResource(imgResId)

            val lpText = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lpText.setMargins(0, 0, 0, 0)
            textView.layoutParams = lpText
            textView.text = "Test"

            linearLayout?.addView(imageView)
            linearLayout?.addView(textView)
        }
    }

    fun openTales(view: View) {
        val intent = Intent(this, Tales::class.java)
        startActivity(intent)
    }
}