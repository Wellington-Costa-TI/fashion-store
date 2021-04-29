package com.example.fashion_store.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion_store.R
import com.example.fashion_store.entity.Produto
import com.example.fashion_store.ui.productdetail.ProductAdapter
import com.example.fashion_store.ui.productdetail.ProductDetailActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        fetchProducts(root)
        return root
    }

    private fun fetchProducts(root: View) {
        val ref = FirebaseDatabase.getInstance().getReference("/product")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var listaDeProduto : MutableList<Produto> = mutableListOf()
                val rv_product_itens =  root.findViewById<RecyclerView>(R.id.rv_product_itens)
                snapshot.children.forEach{
                    val product = it.getValue(Produto::class.java)
                   if (product!= null)
                    listaDeProduto.add(product)
                }

                rv_product_itens.adapter = ProductAdapter(listaDeProduto)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}

