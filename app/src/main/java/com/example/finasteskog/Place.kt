package com.example.finasteskog

import android.media.Image
import com.google.firebase.firestore.DocumentId

data class Place(@DocumentId var documentId: String? = null,
                 var name: String? = null,
                 var location: String? = null,
                 var nature: Boolean = false,
                 var information: String? = null,
                 var image: Image? = null
                 )






