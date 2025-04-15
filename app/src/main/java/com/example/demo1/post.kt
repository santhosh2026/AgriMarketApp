package com.example.demo1

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo1.Adapters.ImageAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class post : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var imageList: MutableList<String>
    private lateinit var storage: FirebaseStorage

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        val add = view.findViewById<ImageView>(R.id.imageView2)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        imageList = mutableListOf()
        imageAdapter = ImageAdapter(imageList,this::deleteImage)
        recyclerView.adapter = imageAdapter
        add.setOnClickListener{
           val intent=Intent(activity,Imageuploader::class.java)
            startActivity(intent)
        }

        fetchImageUrls()
       return view
    }
    private fun fetchImageUrls() {
        val db = FirebaseFirestore.getInstance()
        db.collection("images")
            .get()
            .addOnSuccessListener { result ->
                imageList.clear()
                for (document in result) {
                    val url = document.getString("url")
                    if (url != null) {
                        imageList.add(url)
                    }
                }
                imageAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity, "Error fetching images: " + exception.message, Toast.LENGTH_SHORT).show()
            }
    }
     private fun deleteImage(imageUrl: String) {
        // Find the document ID by the image URL
        val db = FirebaseFirestore.getInstance()
        db.collection("images")
            .whereEqualTo("url", imageUrl)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    db.collection("images").document(document.id).delete()
                        .addOnSuccessListener {
                            Log.d("Firestore", "DocumentSnapshot successfully deleted!")
                            // Delete from storage
                            val storageRef = storage.getReferenceFromUrl(imageUrl)
                            storageRef.delete()
                                .addOnSuccessListener {
                                    Log.d("Storage", "Image successfully deleted!")
                                    // Remove the image from the list and notify adapter
                                    imageList.remove(imageUrl)
                                    imageAdapter.notifyDataSetChanged()
                                }
                                .addOnFailureListener { exception ->
                                    Log.e("Storage", "Error deleting image: " + exception.message)
                                }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Firestore", "Error deleting document: " + exception.message)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error finding document: " + exception.message)
            }
    }
    class ImageAdapter(private val imageList: List<String>,private val onDelete: (String) -> Unit) :
        RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

        class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imageView: ImageView = view.findViewById(R.id.imageView)
            val deleteButton: Button = view.findViewById(R.id.deleteButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
            return ImageViewHolder(view)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            val imageUrl = imageList[position]
            Glide.with(holder.imageView.context)
                .load(imageUrl)
                .into(holder.imageView)
            holder.deleteButton.setOnClickListener {
                onDelete(imageUrl)
            }
        }

        override fun getItemCount(): Int {
            return imageList.size
        }
    }



}