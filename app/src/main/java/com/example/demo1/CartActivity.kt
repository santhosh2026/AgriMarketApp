package com.example.demo1

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val cartItemsLayout: LinearLayout = findViewById(R.id.cart_items_layout)
        val totalPriceTextView: TextView = findViewById(R.id.total_price)
        val cartItems = intent.getParcelableArrayListExtra<Product>("cart_items")
        var totalPrice = 0.0
        if (cartItems != null) {
            for (product in cartItems) {
                val cartItemView = layoutInflater.inflate(R.layout.cart_item, null)

                val productName = cartItemView.findViewById<TextView>(R.id.cart_item_name)
                val productPrice = cartItemView.findViewById<TextView>(R.id.cart_item_price)

                productName.text = product.name
                productPrice.text = product.price
                val price = product.price.replace("/-", "").toDouble()
                totalPrice += price
                cartItemsLayout.addView(cartItemView)
            }
        }
        totalPriceTextView.text = "${totalPrice}/-"
    }
}