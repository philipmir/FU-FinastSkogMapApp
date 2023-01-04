package com.example.finasteskog

import android.content.pm.PackageManager
import android.location.Geocoder
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
import com.google.android.gms.maps.model.MarkerOptions

class GPS : AppCompatActivity() {

    private val REQUEST_LOCATION = 1
    lateinit var locationProvider : FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var lattyOnlyLatLng: LatLng

    lateinit var infoGpsEditText: EditText
    lateinit var gpsTextView: TextView
    lateinit var saveImageButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        infoGpsEditText = findViewById(R.id.infoGpsEditText)
        gpsTextView = findViewById(R.id.gpsTextView)
        saveImageButton = findViewById(R.id.saveImageButton)

        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(2000).build()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    Log.d("!!!", "lat: ${location.latitude}, lng: ${location.longitude}}")
                    var latty = "Your GPS Location Is:\n\n lat: ${location.latitude} \n lng: ${location.longitude}}"
                    var lattyOnlyLatLng = LatLng(location.latitude, location.longitude)
                    gpsTextView.setText(latty)



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
        val location = gpsTextView.text.toString()
        val infomation = infoGpsEditText.text.toString()

        val placee = Place(location, infomation)
        DataManager.places1.add(placee)

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