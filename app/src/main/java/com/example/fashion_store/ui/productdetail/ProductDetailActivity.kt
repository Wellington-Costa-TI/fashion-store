package com.example.fashion_store.ui.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.fashion_store.R
import com.example.fashion_store.entity.Pedido
import com.example.fashion_store.entity.Produto
import com.example.fashion_store.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val productName = intent.getStringExtra("product_name")
        val productDescription = intent.getStringExtra("product_description")
        val productPrice = intent.getStringExtra("product_price")
        val productImagePath = intent.getStringExtra("product_image_path")
        val productId = intent.getStringExtra("product_id")

        val cancelButton = findViewById<Button>(R.id.btn_cancel_add_product_to_cart)
        val addToCartButton = findViewById<Button>(R.id.btn_add_product_to_cart)

        findViewById<TextView>(R.id.tv_show_product_name).text = productName
        findViewById<TextView>(R.id.tv_show_product_description).text = productDescription
        findViewById<TextView>(R.id.tv_show_product_price).text = productPrice
        Picasso.get().load(productImagePath).into(findViewById<ImageView>(R.id.iv_show_product_image))

        cancelButton.setOnClickListener {
           onBackPressed()
        }

        addToCartButton.setOnClickListener{


            addToCart(productId.toString())
        }
    }

    private fun addToCart(productId: String) {
    }
}