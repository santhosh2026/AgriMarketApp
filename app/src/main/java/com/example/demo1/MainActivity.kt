package com.example.demo1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demo1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var b:Button
    private lateinit var b1:Button
    private lateinit var binding1: ActivityMainBinding
    private lateinit var firebase2: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding1=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding1.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebase2=FirebaseAuth.getInstance()
        binding1.loginButton.setOnClickListener {
            var email = binding1.email.text.toString()
            var passw = binding1.password.text.toString()
            if(email.isEmpty() || passw.isEmpty())
            {
                Toast.makeText(this,"every field should be filled", Toast.LENGTH_SHORT).show()
            }
            else{
                firebase2.signInWithEmailAndPassword(email,passw).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent= Intent(this@MainActivity,Home::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        b1=findViewById(R.id.button2)
        b1.setOnClickListener {
            val intent1 = Intent(this@MainActivity,Signup_page::class.java)
            startActivity(intent1)
        }
    }
}