package com.example.demo1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demo1.databinding.ActivitySignupPageBinding
import com.google.firebase.auth.FirebaseAuth

class Signup_page : AppCompatActivity() {
    private lateinit var binding:ActivitySignupPageBinding
    private lateinit var firebase:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupPageBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebase=FirebaseAuth.getInstance()
        binding.signup.setOnClickListener {
            var email = binding.email.text.toString()
            var passw = binding.password.text.toString()
            if(email.isEmpty() || passw.isEmpty())
            {
                Toast.makeText(this,"every field should be filled",Toast.LENGTH_SHORT).show()
            }
            else{
                firebase.createUserWithEmailAndPassword(email,passw).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent= Intent(this@Signup_page,MainActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}