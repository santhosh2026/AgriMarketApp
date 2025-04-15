package com.example.demo1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class homeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var districtSpinner: Spinner
    private lateinit var cropTextView: TextView
    private lateinit var soilTextView: TextView
    private lateinit var landTextView: TextView

    private val districts = listOf("select district","Adilabad", "Hyderabad", "Nizamabad","Karimnagar","warangal","Khammam","Mahabubnagar","Medak","Rangareddy")
    private val districtInfo = mapOf(
        "select district" to DistrictInfo("","",""),
        "Adilabad" to DistrictInfo("Cotton", "Black Soil", "Significant area under cultivation due to fertile soil"),
        "Hyderabad" to DistrictInfo("Rice", "Red Soil", " Urbanization has significantly reduced agricultural land"),
        "Nizamabad" to DistrictInfo("Wheat", "Alluvial Soil", "Known for its fertile lands and diverse crop cultivation"),
        "Karimnagar" to DistrictInfo("Rice, maize, groundnut, cotton","Red soil, black soil"," Extensive agricultural land due to favorable soil conditions"),
        "warangal" to DistrictInfo("Rice, cotton, turmeric, maize","Red soil, black soil, sandy loam","Rich agricultural land suitable for multiple crops."),
        "Khammam" to DistrictInfo("Rice, cotton, chillies, tobacco","Red soil, sandy loam","Fertile lands supporting diverse agricultural activities."),
        "Mahabubnagar" to DistrictInfo("Paddy, cotton, maize, groundnut","Red soil, black soil","Large area under cultivation due to fertile soil."),
        "Medak" to DistrictInfo("Paddy, maize, cotton, sugarcane"," Black soil, sandy loam","Productive agricultural area with good irrigation facilities."),
        "Rangareddy" to DistrictInfo("Paddy, maize, pulses, vegetables","Red soil, sandy loam","Varied agricultural activities supported by fertile soils")

    )
    data class DistrictInfo(val crop: String, val soil: String, val land: String)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_home, container, false)
        districtSpinner = view.findViewById(R.id.district_spinner)
        cropTextView = view.findViewById(R.id.crop_text_view)
        soilTextView = view.findViewById(R.id.soil_text_view)
        landTextView = view.findViewById(R.id.land_text_view)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, districts)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        districtSpinner.adapter = adapter
        districtSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDistrict = districts[position]
                val info = districtInfo[selectedDistrict]
                if (info != null) {
                    cropTextView.text = "Major Crop: ${info.crop}"
                    soilTextView.text = "Type of Soil: ${info.soil}"
                    landTextView.text = "Acres of Agricultural Land: ${info.land}"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        // Find the ImageView by its ID
        val imageView = view.findViewById<ImageView>(R.id.methods) // Replace R.id.methods with the ID of your ImageView

        // Set OnClickListener for the ImageView
        imageView.setOnClickListener {
            // Start the second activity when the ImageView is clicked
            val intent = Intent(activity, Methods::class.java)
            startActivity(intent)
        }
        val imageView1 = view.findViewById<ImageView>(R.id.diseases) // Replace R.id.methods with the ID of your ImageView

        // Set OnClickListener for the ImageView
        imageView1.setOnClickListener {
            // Start the second activity when the ImageView is clicked
            val intent = Intent(activity, Diseases::class.java)
            startActivity(intent)
        }
     return  view

    }

}