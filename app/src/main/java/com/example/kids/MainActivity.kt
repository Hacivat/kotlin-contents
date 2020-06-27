package com.example.kids

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapItems();
    }

     private fun mapItems() {
        for (i in 0..9) {
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
            textView.text = "Test"
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

    private fun getTitles() {
        val url = "http://localhost:8000/tales/titles/tr"

        AsyncTaskHandleJson().execute(url)
    }


    inner class AsyncTaskHandleJson: AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use{ reader->reader.readText() } }
            } finally {
                connection.disconnect()
            }

            return text
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }

        private fun handleJson(jsonString: String?) {
            val jsonArray = JSONArray(jsonString)
        }

    }




    fun openTalePage(view: View) {
        val intent = Intent(this, Tales::class.java)
        startActivity(intent)
    }
}

