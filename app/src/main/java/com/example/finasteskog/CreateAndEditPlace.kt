package com.example.finasteskog

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.IOException

const val PLACE_POSITION_KEY = "PLACE_POSITION"
const val POSITION_NOT_SET = -1

class CreateAndEditPlace : AppCompatActivity() {


    lateinit var placeEditText: EditText
    lateinit var locationEditText: EditText

    lateinit var cityEditText : EditText
    lateinit var cordTextView: TextView
    lateinit var addInfoTextView : TextView
    lateinit var latLngImageButton: ImageButton
    lateinit var gpsImageButton: ImageButton

    lateinit var db : FirebaseFirestore
    lateinit var nameView : EditText
    lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_and_edit_place)



        cityEditText = findViewById(R.id.cityEditText)
        cordTextView = findViewById(R.id.cordTextView)
        addInfoTextView = findViewById(R.id.addInfoTextView)
        latLngImageButton = findViewById(R.id.latLngImageButton)
        gpsImageButton = findViewById(R.id.gpsImageButton)

        auth = Firebase.auth
        db = Firebase.firestore
        nameView = findViewById(R.id.cityEditText)

        //val button = findViewById<Button>(R.id.saveButton)
//        button.setOnClickListener {
//            saveItem()
//        }




        gpsImageButton.setOnClickListener {
            gotToGPSActivity()
        }


        latLngImageButton.setOnClickListener {
            buttonGetCoordinates()


        }


        val placePosition = intent.getIntExtra(PLACE_POSITION_KEY, POSITION_NOT_SET )

        val saveButton = findViewById<ImageButton>(R.id.saveImageButton2)
        if(placePosition != POSITION_NOT_SET) { // edit place
            displayPlace(placePosition)
            saveButton.setOnClickListener {
                editPlace(placePosition)
            }

        }else {                                 // create place
            saveButton.setOnClickListener {
                addNewPlace()
                saveItem()


            }
        }


    }


    fun gotToGPSActivity() {
        val intent = Intent(this, GPS::class.java)
        startActivity(intent)
    }



    fun displayPlace(position : Int) {
        val place = DataManager.places1[position]

        cityEditText.setText(place.name)
        cordTextView.setText(place.location)
        addInfoTextView.setText(place.information)


    }

    fun editPlace(position: Int) {
        DataManager.places1[position].name = cityEditText.text.toString()
        DataManager.places1[position].location = cordTextView.text.toString()
        DataManager.places1[position].information = addInfoTextView.text.toString()


        finish()
    }


    fun addNewPlace() {
        val place = cityEditText.text.toString()
        val location = cordTextView.text.toString()
        val infomation = addInfoTextView.text.toString()

        val placee = Place(place, location, infomation)
        DataManager.places1.add(placee)

        finish()
    }

    fun buttonGetCoordinates() {
        val geocoder = Geocoder(this)
        var addressList: MutableList<Address>


        try {
            addressList = geocoder.getFromLocationName(cityEditText.text.toString(), 1)

            if (addressList != null) {
                val doubleLat = addressList[0].latitude
                val doubleLong = addressList[0].longitude

                cordTextView.text = "Latitude   : ${doubleLat}\nLongitude:${doubleLong}"



            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun saveItem() {

        val name = nameView.text.toString()
        val location = cordTextView.text.toString()
        val information = addInfoTextView.text.toString()
        val item = Place(name = name, location = location, information = information)


        nameView.setText("")
        cordTextView.setText("")

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
//                var location : String? = null,
//                var nature : Boolean = false)












