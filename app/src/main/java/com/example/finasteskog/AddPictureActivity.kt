package com.example.finasteskog

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finasteskog.databinding.ActivityAddPictureBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.grpc.Context.Storage
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class AddPictureActivity : AppCompatActivity() {

     var db = Firebase.firestore
     var FirebaseStorage = Firebase.storage

    lateinit var binding : ActivityAddPictureBinding
    lateinit var ImageUrl : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.selectImageBtn.setOnClickListener {

            selectImage()

        }


        binding.uploadImageBtn.setOnClickListener {

            uploadImage()

        }


    }

    private fun uploadImage() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Upload file ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getReference("images/$fileName")


        storageReference.putFile(ImageUrl).
            addOnSuccessListener {

                binding.firebaseImage.setImageURI(null)
                Toast.makeText(this@AddPictureActivity, "Successfuly uploaded", Toast.LENGTH_SHORT).show()
                if(progressDialog.isShowing) progressDialog.dismiss()

            }.addOnFailureListener {

                if(progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@AddPictureActivity, "Failed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun selectImage()  {

        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            ImageUrl = data?.data!!
            binding.firebaseImage.setImageURI(ImageUrl)
        }

    }

}