package com.example.fashion_store.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion_store.*
import com.example.fashion_store.entity.Produto
import com.example.fashion_store.ui.productdetail.ProductAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

        val fabAddProduct = root.findViewById<FloatingActionButton>(R.id.fab_add_product)
        fabAddProduct.setOnClickListener{
            val intent =
                Intent(activity, RegisterProductActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    private fun fetchProducts(root: View) {
        val ref = FirebaseDatabase.getInstance().getReference("/product")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var listaDeProduto : MutableList<Produto> = mutableListOf()
                val rv_product_itens =  root.findViewById<RecyclerView>(R.id.rv_product_itens)
                snapshot.children.forEach{
                    it.key
                    val product = it.getValue(Produto::class.java)
                   if (product!= null)
                    listaDeProduto.add(product)
                }
                rv_product_itens.layoutManager = GridLayoutManager(context, 2,GridLayoutManager.VERTICAL,false)
                rv_product_itens.adapter = ProductAdapter(listaDeProduto)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}

