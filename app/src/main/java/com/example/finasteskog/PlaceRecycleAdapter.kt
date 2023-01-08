package com.example.finasteskog

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finasteskog.DataManager.places1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.model.Document
import com.google.firebase.ktx.Firebase

class PlaceRecycleAdapter(


    val context: Context, places1: MutableList<Place>,
                           ) :


    RecyclerView.Adapter<PlaceRecycleAdapter.ViewHolder>() {


    var layoutInflater = LayoutInflater.from(context)



    lateinit var db : FirebaseFirestore
    lateinit var auth : FirebaseAuth







    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        var locationTextView = itemView.findViewById<TextView>(R.id.locationTextVIew)
        var natureButton = itemView.findViewById<CheckBox>(R.id.checkBox)
        var deleteButton = itemView.findViewById<ImageButton>(R.id.deleteButton)

//        var addMapButton = itemView.findViewById<ImageButton>(R.id.addInfoButton)
        var placePosition = 0



        init {
            itemView.setOnClickListener {
                val intent = Intent(context, CreateAndEditPlace::class.java)
                intent.putExtra(PLACE_POSITION_KEY, placePosition)
                context.startActivity(intent)
            }


            natureButton.setOnClickListener {
                DataManager.places1[placePosition].nature = natureButton.isChecked
            }

            deleteButton.setOnClickListener {
                removePlace(placePosition)






            }

//            addMapButton.setOnClickListener {
//                val intent = Intent(context, AddItemActivity::class.java)
//                context.startActivity(intent)
//            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_place, parent, false)

        return  ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places1[position]

        holder.nameTextView.text = place.place
        holder.locationTextView.text = "Latitude: ${place.latDouble} \nLongitude: ${place.longDouble}"
        holder.natureButton.isChecked = place.nature
        holder.placePosition = position
    }

    override fun getItemCount() = places1.size

    fun removePlace(position: Int) {
        DataManager.places1.removeAt(position)



//
//        val user = auth.currentUser
//        if (user == null) {
//            return
//        }
//
//        db.collection("users").document(user.uid)
//            .collection("items").document().delete()



        notifyDataSetChanged()
    }





}