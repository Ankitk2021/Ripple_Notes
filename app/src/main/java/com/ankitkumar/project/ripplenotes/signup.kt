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
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signupBtn: Button
    private lateinit var emailEdit: EditText
    private lateinit var passEdit: EditText
    private lateinit var bundle: Bundle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        FirebaseApp.initializeApp(this)

        auth = FirebaseAuth.getInstance()


        signupBtn = findViewById(R.id.signup_btn_go)
        emailEdit = findViewById(R.id.editText)
        passEdit = findViewById(R.id.editText2)


//Go login page
        val buttonLogInGo = findViewById<Button>(R.id.log_in_btn)
        buttonLogInGo.setOnTouchListener { view, motionEvent ->
            startActivity(Intent(this, signup::class.java))
            true
        }

        signupBtn.setOnClickListener {
            registerNewUser();
            Toast.makeText(this, "Already Logged in 😃", Toast.LENGTH_SHORT).show()
            login()

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun login() {

        val email = emailEdit.text.toString()
        val pass = passEdit.text.toString()

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(this, "Logging in 🚌.....", Toast.LENGTH_SHORT).show()
                val user_email: String = task.result.user?.email.toString()
                val user_name: String = task.result.user?.displayName.toString()



                bundle = Bundle()
                bundle.putString("user_name", user_name)
                bundle.putString("user_email", user_email)


                val intent: Intent = Intent(this, share_additional_data::class.java)
                intent.putExtras(bundle)
                startActivity(intent)


            } else {
                Toast.makeText(
                    this,
                    task.exception?.localizedMessage.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun registerNewUser() {


        val email = emailEdit.text.toString()
        val pass = passEdit.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Email is empty 😏", Toast.LENGTH_SHORT).show()
            return
        }
        if (pass.isEmpty()) {

            Toast.makeText(this, "Password is empty 😏", Toast.LENGTH_SHORT).show()
            return
        }


        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Registered Successfully ☺️", Toast.LENGTH_SHORT).show()
                login()
            } else {
                Toast.makeText(this, task.exception?.localizedMessage.toString(), Toast.LENGTH_LONG)
                    .show()

            }
        }


    }
}