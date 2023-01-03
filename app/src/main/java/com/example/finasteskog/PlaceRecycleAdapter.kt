package com.example.finasteskog

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.finasteskog.DataManager.places1

class PlaceRecycleAdapter(
    val context: Context, places1: MutableList<Place>,
                           ) :
    RecyclerView.Adapter<PlaceRecycleAdapter.ViewHolder>() {


    var layoutInflater = LayoutInflater.from(context)


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        var ageTextView = itemView.findViewById<TextView>(R.id.ageTextVIew)
        var presentButton = itemView.findViewById<CheckBox>(R.id.checkBox)
        var deleteButton = itemView.findViewById<ImageButton>(R.id.deleteButton)
//        var addMapButton = itemView.findViewById<ImageButton>(R.id.addInfoButton)
        var placePosition = 0

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, CreateAndEditPlace::class.java)
                intent.putExtra(PLACE_POSITION_KEY, placePosition)
                context.startActivity(intent)
            }


            presentButton.setOnClickListener {
                DataManager.places1[placePosition].nature = presentButton.isChecked
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

        holder.nameTextView.text = place.name
        holder.ageTextView.text = place.location.toString()
        holder.presentButton.isChecked = place.nature
        holder.placePosition = position
    }

    override fun getItemCount() = places1.size

    fun removePlace(position: Int) {
        DataManager.places1.removeAt(position)
        notifyDataSetChanged()
    }





}