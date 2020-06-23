package com.example.kids

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = ImageView(this)

        for (i in 0..9) {
            val image = ImageView(this)
            imageView.layoutParams= LinearLayout.LayoutParams(400, 400)
            imageView.x= 20F // setting margin from left
            imageView.y= 20F // setting margin from top

            val imgResId = R.drawable.group
            var resId = imgResId
            // Adds the view to the layout
            imageView.setImageResource(resId)

            val layout = findViewById<ScrollView>(R.id.scrollView)

            layout?.addView(imageView)
        }
    }

    fun openTales(view: View) {
        val intent = Intent(this, Tales::class.java)
        startActivity(intent)
    }
}