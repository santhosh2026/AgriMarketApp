package com.example.demo1

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat

class Ecommerce : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var rootView: View
    private lateinit var cartButton: ImageView
    private lateinit var productsLayout: LinearLayout
    private val products: List<Product> = listOf(
        Product("cotton", "68/-", R.drawable.cotton, "Cotton seeds are essential for cotton production, serving as the source for cotton fiber used in textiles. They require warm climates and well-drained soil to thrive.\n"+
                " Cotton seeds are rich in oil, used in various industries including food and cosmetics.Green World Hybrid Cotton/Kapas Seeds for Agriculture (250grams seeds)"),
        Product("pesticide", "2000/-", R.drawable.pestcide,"Pesticides are chemicals used to control pests and diseases in agriculture, protecting crops from damage."),
        Product("maizeseeds", "400/-", R.drawable.maizeseeds,"nuziveedu maize Seed | Green Manure Seed for Cultivation | Crotalaria Juncea (1Kg)  Cultivation of these seeds increases soil fertility, organic matter, and water-holding capacity. 2. Rotavating the field after 45 days old crop in moist conditions produces better preparation of soil for all other successive crops"),
        Product("paddy seeds", "1500/-", R.drawable.paddyseeds,"jaya pradhan | Pusa Basmati Paddy Seeds 1718 | Dhan, Rice & Paddy Seeds For Farming & Agriculture (Kheti) | Pure Pusa Basmati Rice Seeds | 1Kg Item Weight1 Kilograms\n" +
                "Item FormGrain\n" +
                "Package Weight8.82 Pounds\n" +
                "VarietyBasmati,Basmati Rice\n"
                ),
        Product("urea", "1000/-", R.drawable.urea,""),
        Product("plant booster","500/-",R.drawable.plantboost,"Plant Growth Booster Liquid Fertilizer for Plants(450 ml) - Plant Growth Supplement With Plant Food Micronutrients and Speed Growth Booster - Best for Indoor & Outdoor Plants forHealthyPlants"),
        Product("DAP","500/-",R.drawable.mahadan,"STONEFUR Organic Dap Fertilizers for Plants, Organic DAP Fertilizer for Crops, All Purpose Bio DAP Fertilizer for Home Plants & Gardening(1kg)"),
        Product("Weedicide","700/-",R.drawable.weedicide,"Sansar Agro® Weeds Remover Spray, Premium Essential Liquid Spray for Removing all types of Wildweeds(200ml)"),
        Product("red chilli seeds","100/-",R.drawable.redchilli,"About this item\n" +
                "For Quick, Better Germination Sow Seeds With Extra Care, Not Fully Inside-partial Cover and Less Water Always - During Summer Partial Sunlight and During Winters Morning Day Sunlight is Good for Good Germination\n" +
                "Non_ Gmo Seeds Best for Pots, Terrace Farming and Professional Use\n" +
                "Organic Plant Seed\n" +
                "Seed For: CHILLI (MIRCHI) SEED\n" +
                "Quantity:40perpacket"),
        Product("flower seeds","150/-",R.drawable.hollyhook,"About this item\n" +
                "\uD83C\uDF43 A Pack of High-Quality Hybrid & Organic Holly Hock Flower Seeds, Perfect Flower Seeds for Gardening & Planting in Pots & Patio.\n" +
                "\uD83C\uDF3B Holly Hock Flower Seeds Has Been Handpicked by Our Expert's Team to Provide the Most Complete & Fresh Flower Seeds Which Offer the Advantages of Both Productivity & Disease Resistance, Ensuring a Successful & Abundant Flowers.\n" +
                "\uD83C\uDFF5 Holly Hock Flower Seeds Guarantee that Your Home Garden will be free from Synthetic Pesticides & Chemicals, Promoting a Healthy, Fresh, Colorful & Sustainable Environment.\n" +
                "\uD83C\uDF39 Specifically Selected Holly Hock Flower Seeds that are Ideal For Home Gardening Pots - Patio, Kitchen Gardens & Backyard Gardening Projects."),
        Product("sunflower seeds","400/-",R.drawable.sunflower,"About this item\n" +
                "A native of North America, but commercialized in Russia\n" +
                "Rich in healthy fats, beneficial plant compounds and several vitamins and minerals.\n" +
                "Sunflower seeds are a good source of beneficial plant compounds, including phenolic acids and flavonoids\n" +
                "First domesticated by the preemptive American-Indian into a single headed plant with a variety of seed colors."),
        Product("Groundnut seeds","4000/-",R.drawable.groundnutseeds,"About this item\n" +
                "15 Kgs pack\n" +
                "Stable yields (15-20 q/ha) even under severe drought.\n" +
                "Oil percent 51 %\n" +
                "Multiple resistant for drought, pests and diseases.\n" +
                "Each Plant gives 160 to 180 pods."),
        Product("organic fertilizer","500/-",R.drawable.potash,"About this item\n" +
                "Improves Size of Flowers, Fruits and Vegetables.\n" +
                "Organic Potash is a necessary nutrient required for growth of plants.Potash helps in improving the photosynthesis efficiency as it regulates CO2 intake.\n" +
                "Solves the problem of falling leaves and improves root resistance to water deficiency and pests."),
        Product("antgel","200/-",R.drawable.antgel,"Item Form\treday to use\n" +
                "Active Ingredients\tSugar , Honey , Orange oil ,mudulea suberosa leaves extract & solvant\n" +
                "Brand\tAmish\n" +
                "Target Species\tAnt"),
        Product("insecticide","500/-",R.drawable.insecticide,"About this item\n" +
                "Manufacturer Warranty\n" +
                "Dose -1 ml Moskitos per m2 water area spray\n" +
                "Has 20 day managment of Mosquito larvae")
    )
    private val cartItems: MutableList<Product> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_ecommerce, container, false)
        productsLayout = rootView.findViewById(R.id.products_layout)
        cartButton = rootView.findViewById(R.id.cart)
        displayProducts()

        cartButton.setOnClickListener {
            navigateToCart()
        }
        return rootView
    }

    private fun displayProducts() {
        productsLayout.removeAllViews()
        for (product in products) {
            val productView = layoutInflater.inflate(R.layout.product_item, null)

            val productName = productView.findViewById<TextView>(R.id.product_name)
            val productPrice = productView.findViewById<TextView>(R.id.product_price)
            val productImage = productView.findViewById<ImageView>(R.id.product_image)
            val addToCartButton = productView.findViewById<Button>(R.id.add_to_cart_button)

            productName.text = product.name
            productPrice.text = "Price: ${product.price}"
            productImage.setImageResource(product.imageResId)

            addToCartButton.tag = product // Set the product as the tag to access in click listener

            addToCartButton.setOnClickListener { view ->
                val selectedProduct = view.tag as Product
                addToCart(selectedProduct)
            }
            productImage.setOnClickListener {
                showProductDescription(product)
            }
            productsLayout.addView(productView)
        }
    }
    private fun addToCart(product: Product) {
        cartItems.add(product)
        Toast.makeText(context, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
    }
    private fun navigateToCart() {
        val intent = Intent(context, CartActivity::class.java)
        intent.putParcelableArrayListExtra("cart_items", ArrayList(cartItems))
        startActivity(intent)
    }
    private fun showProductDescription(product: Product) {
        // Create an AlertDialog to display product description
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(product.name)
        builder.setMessage(product.description)
        builder.setPositiveButton("OK") { dialog, which ->
            // Handle positive button click if needed
        }
        builder.show()
    }

}
data class Product(val name: String, val price: String, val imageResId: Int,val description:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeInt(imageResId)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}

