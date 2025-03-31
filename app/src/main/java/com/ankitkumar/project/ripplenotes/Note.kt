package com.ankitkumar.project.ripplenotes

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Note : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note)


        val bundle = intent.extras
        val title = bundle?.getString("title","Not found.")
        val desc = bundle?.getString("desc","Not found.")


        val textViewTitle = findViewById<TextView>(R.id.note_title_activity_note)
        textViewTitle.setText("$title")
        val textViewDesc = findViewById<TextView>(R.id.note_desc_activity_note)
        textViewDesc.setText("$desc")


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}