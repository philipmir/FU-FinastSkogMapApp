package com.example.finasteskog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.example.finasteskog.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    lateinit var auth : FirebaseAuth
    lateinit var emailView : EditText
    lateinit var passwordView : EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        auth = Firebase.auth
        emailView = findViewById(R.id.emailEditText)
        passwordView = findViewById(R.id.passwordEditText)




        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            signUp()
        }

        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener {
            signIn()
        }

        if (auth.currentUser != null) {
            gotToAddActivity()
        }






    }

    fun gotToAddActivity() {
        val intent = Intent(this, RecyclerActivity::class.java)
        startActivity(intent)
    }




    fun signIn() {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("!!!", "create success")
                    gotToAddActivity()
                } else {
                    Log.d("!!!", "user not created ${task.exception}")
                }

            }
    }




    fun signUp() {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("!!!", "signed in")
                    gotToAddActivity()
                } else {
                    Log.d("!!!", "user not signed in ${task.exception}")
                }

            }
    }


}