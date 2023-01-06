package com.example.finasteskog

import android.R.string
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.TextUtils.split
import androidx.appcompat.app.AppCompatActivity
import com.example.finasteskog.databinding.ActivityMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback {



    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding

    lateinit var placeList: MutableList<PlaceInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val adapter = PlacesInfoAdapter(this)
        mMap.setInfoWindowAdapter(adapter)


//        createMarkers()

        createPlaces()

//        markFromLatLng()










    }



    fun createMarkers() {
        var sthlm = LatLng(59.3, 18.0)

        var marker1 = mMap.addMarker(
            MarkerOptions()
                .position(sthlm)
                .title("Stockholm")
                .snippet("Fint här")
        )

        var marker2 = mMap.addMarker(
            MarkerOptions()
                .position(LatLng(60.0,19.0))
                .title("plats2")
                .snippet("Fint här")
        )

        var marker3 = mMap.addMarker(
            MarkerOptions()
                .position(LatLng(58.0, 17.0))
                .title("plats3")
                .snippet("Fint här")
        )




    }

    fun createPlaces()    {



//                val p2 = PlaceInfo("Borta", "bra", LatLng(getLatLong().first,getLatLong().second), R.drawable.ic_baseline_house_24)
                //val p3 = PlaceInfo("Borta", "bra", LatLng(), R.drawable.ic_baseline_house_24)



//        for(place in PlaceInfo) {
//            val marker = mMap.addMarker(MarkerOptions().position(LatLng())
//            marker?.tag = place
//        }
        val geocoder = Geocoder(this)


        for (place in DataManager.places1) {
            val placeName = place.name.toString()
            val addresses: List<Address> = geocoder.getFromLocationName(placeName, 1)
            if (addresses != null && !addresses.isEmpty()) {
                val address = addresses[0]
                val latitude = address.latitude
                val longitude = address.longitude
                val latLng = LatLng(latitude, longitude)
                val marker = mMap.addMarker(MarkerOptions().position(latLng))
                marker!!.tag = place
            }
        }




    }

    fun markFromLatLng() {



//                val p2 = PlaceInfo("Borta", "bra", LatLng(getLatLong().first,getLatLong().second), R.drawable.ic_baseline_house_24)
        //val p3 = PlaceInfo("Borta", "bra", LatLng(), R.drawable.ic_baseline_house_24)



        for(place in DataManager.places1) {
            val marker = mMap.addMarker(MarkerOptions().position(GPS().lattyOnlyLatLng))
            marker?.tag = place
        }
//        val geocoder = Geocoder(this)


//
//        for (place in DataManager.places1) {
//            val parts: Array<String> = string.split(",")
//            val latitude = parts[0].toDouble()
//            val longitude = parts[1].toDouble()
//
//
//            val placeLocationLatLng: LatLng? = LatLng(latitude, longitude)
//            val addresses = geocoder.getFromLocation(
//                placeLocationLatLng!!.latitude, placeLocationLatLng!!.longitude, 1
//            )
//            if (addresses != null && !addresses.isEmpty()) {
//                val address = addresses[0]
//                val latitude = address.latitude
//                val longitude = address.longitude
//                val latLng = LatLng(latitude, longitude)
//                val marker = mMap.addMarker(MarkerOptions().position(latLng))
//                marker!!.tag = place
//            }
//        }








    }



}


data class PlaceInfo(val name: String, val info: String, val image: Int )