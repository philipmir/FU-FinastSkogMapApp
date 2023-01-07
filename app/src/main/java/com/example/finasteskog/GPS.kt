package com.example.finasteskog

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GPS : AppCompatActivity() {

    private val REQUEST_LOCATION = 1
    lateinit var locationProvider : FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var lattyOnlyLatLng: LatLng
    lateinit var latNlongy : LatLng
     var latty : Double = 0.0
     var longy : Double = 0.0

    lateinit var db : FirebaseFirestore
    lateinit var auth : FirebaseAuth


    lateinit var infoGpsEditText: EditText
    lateinit var latTextView: TextView
    lateinit var longTextView: TextView
    lateinit var saveImageButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        infoGpsEditText = findViewById(R.id.infoGpsEditText)
        latTextView = findViewById(R.id.latTextView)
        longTextView = findViewById(R.id.longTextView)
        saveImageButton = findViewById(R.id.saveImageButton)


        auth = Firebase.auth
        db = Firebase.firestore

        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(2000).build()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    Log.d("!!!", "lat: ${location.latitude}, lng: ${location.longitude}}")

                    var latty = "Your GPS Location Is:\n\n lat: ${location.latitude} \n lng: ${location.longitude}}"
                    var lattyOnlyLatLng = LatLng(location.latitude, location.longitude)
                    var lat = location.latitude
                    var long = location.longitude
                    var latNlong = LatLng(lat, long)
//                    gpsTextView.setText(latty)

                    latTextView.setText(lat.toString())
                    longTextView.setText(long.toString())





                }


            }
        }






        if ( ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
              != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
                )

        }

        saveImageButton.setOnClickListener {
            addNewGpsPlace()



        }

    }

    fun addNewGpsPlace() {

//        val place = cityEditText.text.toString()
        latty = latTextView.text.toString().toDouble()
        longy = longTextView.text.toString().toDouble()
        latNlongy = LatLng(latty, longy)
        val infomation = infoGpsEditText.text.toString()

        val placee = Place(null, infomation, latNlongy.toString(), false, infomation, R.drawable.ic_baseline_house_24, latty, longy)
        DataManager.places1.add(placee)

        val user = auth.currentUser
        if (user == null) {
            return
        }

        db.collection("users").document(user.uid)
            .collection("items").add(placee)

        finish()
    }


    override fun onResume() {
        super.onResume()
        startLocationUpdate()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    fun stopLocationUpdates() {
        locationProvider.removeLocationUpdates(locationCallback)
    }

    fun startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED)
        locationProvider.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdate()
            }
        }
    }




}