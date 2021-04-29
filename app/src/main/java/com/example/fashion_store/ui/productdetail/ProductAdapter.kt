package com.example.fashion_store.ui.productdetail

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion_store.R
import com.example.fashion_store.entity.Produto
import com.squareup.picasso.Picasso

class ProductAdapter(private val productList: List<Produto>): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val productName = itemView.findViewById<TextView>(R.id.tv_product_item_name)
        val productDescription = itemView.findViewById<TextView>(R.id.tv_product_item_description)
        val productPrice = itemView.findViewById<TextView>(R.id.tv_value_item_product)
        val productImage = itemView.findViewById<ImageView>(R.id.iv_product_item)
        val productImagePath = itemView.findViewById<TextView>(R.id.tv_image_path_product_item)

       init {
           itemView.setOnClickListener(this)
       }
        override fun onClick(v: View?) {
            val intent = Intent(v?.context,ProductDetailActivity::class.java)
            intent.putExtra("product_name",v?.findViewById<TextView>(R.id.tv_product_item_name)?.text)
            intent.putExtra("product_description", v?.findViewById<TextView>(R.id.tv_product_item_description)?.text)
            intent.putExtra("product_price",v?.findViewById<TextView>(R.id.tv_value_item_product)?.text)
            intent.putExtra("product_image_path",v?.findViewById<TextView>(R.id.tv_image_path_product_item)?.text)
            v?.context?.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        Picasso.get().load(currentProduct.imagePath).into(holder.productImage)
        holder.productName.text = currentProduct.nome
        holder.productDescription.text = currentProduct.descricao
        holder.productPrice.text = "R$ "+currentProduct.valor
        holder.productImagePath.text = currentProduct.imagePath
    }

    override fun getItemCount(): Int {
        return productList.size
    }

}