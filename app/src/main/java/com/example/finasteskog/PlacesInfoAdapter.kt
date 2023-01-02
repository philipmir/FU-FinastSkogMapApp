package com.example.finasteskog

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class PlacesInfoAdapter(val context: Context) : GoogleMap.InfoWindowAdapter {

    val layoutInflater = LayoutInflater.from(context)


    override fun getInfoContents(p0: Marker): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker): View? {
        val infoWindow = layoutInflater.inflate(R.layout.info_window, null)

        val locationImageView = infoWindow.findViewById<ImageView>(R.id.locationImageView)
        val locationTextView = infoWindow.findViewById<TextView>(R.id.locationTextView)
        val factTextView = infoWindow.findViewById<TextView>(R.id.factTextView)

        val place = marker.tag as? Place

        locationTextView.text = place?.name
        factTextView.text = place?.information
        if (place != null) {
            place.image?.let { locationImageView.setImageResource(it) }
        }




        return infoWindow


    }


}

private fun ImageView.setImageResource(image: Image) {

}
