package com.example.fashion_store.ui.address

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion_store.R
import com.example.fashion_store.RegisterAddressActivity
import com.example.fashion_store.entity.Endereco
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class AddressFragment : Fragment() {

    private lateinit var addressViewModel: AddressViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        addressViewModel =
                ViewModelProvider(this).get(AddressViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_endereco, container, false)
        fetchAdresses(root)

        val fabAddAdress = root.findViewById<FloatingActionButton>(R.id.fab_add_address)
        fabAddAdress.setOnClickListener{
            val intent =
                    Intent(activity, RegisterAddressActivity::class.java)
            startActivity(intent)
        }


        return root
    }

    private fun fetchAdresses(root: View) {

        val userId = FirebaseAuth.getInstance().currentUser.uid
        val ref = FirebaseDatabase.getInstance().getReference("/address/$userId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                val rv_address_itens =  root.findViewById<RecyclerView>(R.id.rv_address_itens)
                snapshot.children.forEach{
                    val address = it.getValue(Endereco::class.java)

                    if (address != null) {
                        Log.d("endereco",address.endereco)
                        adapter.add(adressItem(address))
                    }
                }
                rv_address_itens.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    class adressItem(val address: Endereco) : Item<GroupieViewHolder>() {
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.findViewById<TextView>(R.id.tv_endereco_destinatario).text = address.destinatario
            viewHolder.itemView.findViewById<TextView>(R.id.tv_endereco_endereco).text = address.endereco
            viewHolder.itemView.findViewById<TextView>(R.id.tv_endereco_numero).text = address.numero
            viewHolder.itemView.findViewById<TextView>(R.id.tv_endereco_cidade).text = address.cidade
            viewHolder.itemView.findViewById<TextView>(R.id.tv_endereco_estado).text = address.estado
            viewHolder.itemView.findViewById<TextView>(R.id.tv_endereco_cep).text = address.cep
            viewHolder.itemView.findViewById<TextView>(R.id.tv_endereco_complemento).text = address.complemento
            viewHolder.itemView.findViewById<TextView>(R.id.tv_endereco_ponto_de_referencia).text = address.pontoReferencia
        }

        override fun getLayout(): Int {
            return R.layout.item_address
        }
    }
}