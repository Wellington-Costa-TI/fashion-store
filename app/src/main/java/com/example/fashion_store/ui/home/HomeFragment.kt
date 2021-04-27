package com.example.fashion_store.ui.home

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
import com.google.firebase.auth.FirebaseAuth
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
                val adapter = GroupAdapter<GroupieViewHolder>()
                val rv_product_itens =  root.findViewById<RecyclerView>(R.id.rv_product_itens)
                snapshot.children.forEach{
                    val product = it.getValue(Produto::class.java)
                   if (product!= null)
                    adapter.add(ProductItem(product))
                }
                rv_product_itens.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    class ProductItem(val product: Produto) : Item<GroupieViewHolder>(){
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            val imagepath = viewHolder.itemView.findViewById<ImageView>(R.id.iv_product_item)
          viewHolder.itemView.findViewById<TextView>(R.id.tv_product_item_name).text = product.nome
            viewHolder.itemView.findViewById<TextView>(R.id.tv_value_item_product).text = "R$ " + product.valor.toString()

            Picasso.get().load(product.imagePath).into(viewHolder.itemView.findViewById<ImageView>(R.id.iv_product_item))
        }

        override fun getLayout(): Int {
            return  R.layout.item_product
        }

    }


}