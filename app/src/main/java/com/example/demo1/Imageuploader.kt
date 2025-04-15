package com.example.demo1

import android.content.Intent
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.example.demo1.databinding.ActivityImageuploaderBinding
import com.google.firebase.Firebase
import java.util.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Imageuploader : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private lateinit var storageReference: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imageuploader)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val selectImageButton: Button = findViewById(R.id.select)
        val uploadImageButton: Button = findViewById(R.id.upload)
        storageReference = FirebaseStorage.getInstance().reference

        selectImageButton.setOnClickListener {
            chooseImage()
        }

        uploadImageButton.setOnClickListener {
            uploadImage()
        }

    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageReference.child("images/" + UUID.randomUUID().toString())
            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        saveImageUrl(uri.toString())
                    }
                    Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed: " + e.message, Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
        }
    }
    private fun saveImageUrl(url: String) {
        val db = FirebaseFirestore.getInstance()
        val image = hashMapOf(
            "url" to url
        )
        db.collection("images")
            .add(image)
            .addOnSuccessListener {
                Toast.makeText(this, "URL saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving URL: " + e.message, Toast.LENGTH_SHORT).show()
            }
    }

}


