package com.example.finasteskog

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class WelcomeActivity : AppCompatActivity() {

    lateinit var cuteButton : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        cuteButton = findViewById(R.id.cuteStartImageButton)



        cuteButton.setOnClickListener {
            gotToAddActivity()
        }




    }

    fun gotToAddActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}