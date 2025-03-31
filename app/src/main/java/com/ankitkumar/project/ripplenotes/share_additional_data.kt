package com.ankitkumar.project.ripplenotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class share_additional_data : AppCompatActivity() {

    private lateinit var name_edit: EditText;
    private lateinit var phone_edit: EditText;
    private lateinit var email_edit: EditText;
    private lateinit var bio_edit: EditText;
    private lateinit var saveBtn: Button;
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContentView(R.layout.activity_share_additional_data)


        name_edit = findViewById(R.id.display_name_edit)
        phone_edit = findViewById(R.id.phone_edit)
        email_edit = findViewById(R.id.edit_email)
        bio_edit = findViewById(R.id.edit_bio)
        saveBtn = findViewById(R.id.addition_data_save_btn)
        saveBtn.setOnClickListener {
            sendData();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun sendData() {
        auth = FirebaseAuth.getInstance()


        if(auth.currentUser==null){
            Toast.makeText(this, "Error 🤒", Toast.LENGTH_SHORT).show()
            return
        }
        val userId = auth.currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        val name: String = name_edit.text.toString();
        val profileUrl: String = "https://upload.wikimedia.org/wikipedia/commons/1/12/User_icon_2.svg"
        val bio: String = bio_edit.text.toString()
        val phone : String = phone_edit.text.toString()
        val user = hashMapOf(
            "name" to name,
            "bio" to bio, "profile-url" to profileUrl,"phone" to phone
        )
        userId?.let {
            db.collection("users").document(it).set(user).addOnSuccessListener {
                Toast.makeText(this, "Going to Homepage 🐽", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Error : ${e.localizedMessage} ", Toast.LENGTH_LONG).show()
            }
        }

    }
}