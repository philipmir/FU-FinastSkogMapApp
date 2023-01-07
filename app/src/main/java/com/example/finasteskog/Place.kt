package com.example.finasteskog

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentId

data class Place(@DocumentId var documentId: String? = null,
                 var place: String? = null,
                 var location: String? = null,
                 var nature: Boolean = false,
                 var information: String? = null,
                 var image: Int? = null,

                 var latDouble: Double? = null,
                 var longDouble: Double? = null,
                var bothLatLong : LatLng? = null
                 )






