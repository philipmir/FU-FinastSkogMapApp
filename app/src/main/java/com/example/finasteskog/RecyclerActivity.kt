package com.example.finasteskog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RecyclerActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

//    var places = mutableListOf<Place>(
//        Place("Stockholm", 55),
//        Place("Eskilstuna", 33),
//        Place("Gavle", 22),
//        Place("Umea", 11)
//    )

    lateinit var db : FirebaseFirestore
    lateinit var auth : FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)



        auth = Firebase.auth
        db = Firebase.firestore







        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PlaceRecycleAdapter(this, DataManager.places1)


        val user = auth.currentUser
        if (user == null) {
            return
        }

        val docRef = db.collection("users").document(user.uid).collection("items")


        docRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null) {
                DataManager.places1.clear()
                for (docuument in snapshot.documents) {
                    val place = docuument.toObject<Place>()
                    if( place != null) {
                        DataManager.places1.add(place)
                    }
                }

                printItemListTest()

            }
            onResume()


        }



        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(this, CreateAndEditPlace::class.java)
            startActivity(intent)
        }
        val fab2 = findViewById<FloatingActionButton>(R.id.mapFloatingActionButton)
        fab2.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }






//        val adapter = PlaceRecycleAdapter(this, places)
//
//        recycleView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()


        recyclerView.adapter?.notifyDataSetChanged()

    }


    fun printItemListTest() {
        for (item in DataManager.places1) {
            Log.d("!!!", "${item.name}")
        }
    }


}
