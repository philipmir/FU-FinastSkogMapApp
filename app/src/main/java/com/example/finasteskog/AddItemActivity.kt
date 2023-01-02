package com.example.finasteskog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddItemActivity : AppCompatActivity() {

    lateinit var db : FirebaseFirestore
    lateinit var infoView : EditText
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)


        auth = Firebase.auth
        db = Firebase.firestore
        infoView = findViewById(R.id.itemNameView)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            saveItem()
        }

    }

    fun saveItem() {
        val item = Place( information = infoView.text.toString(), location = null, image = null)
        infoView.setText("")

        val user = auth.currentUser
        if (user == null) {
            return
        }

        db.collection("users").document(user.uid)
            .collection("items").add(item)
    }
}

//data class Item(@DocumentId var documentId: String? = null,
//                var name : String? = null,
//                var done : Boolean = false)