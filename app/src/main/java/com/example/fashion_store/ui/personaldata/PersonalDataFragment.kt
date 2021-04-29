package com.example.fashion_store.ui.personaldata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fashion_store.R
import com.example.fashion_store.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class PersonalDataFragment : Fragment() {

    private lateinit var personalDataViewModel: PersonalDataViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        personalDataViewModel =
                ViewModelProvider(this).get(PersonalDataViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pessoais, container, false)

        getUserFromDatabase(root)


        return root
    }

    private fun getUserFromDatabase(root: View?) {
        val nomeCompleto = root?.findViewById<TextView>(R.id.et_show_name)
        val dataNascimento = root?.findViewById<TextView>(R.id.et_show_date)
        val celular = root?.findViewById<TextView>(R.id.et_show_phone)
        val email = root?.findViewById<TextView>(R.id.tv_show_email_address)

        val mDatabase = FirebaseDatabase.getInstance().reference
        val userId = FirebaseAuth.getInstance().currentUser.uid
        mDatabase.child("user").child(userId).get().addOnSuccessListener {
            val user = it.getValue(User :: class.java )
            if (user != null) {
                email?.text = user.email
                nomeCompleto?.text = user.nomeCompleto
                dataNascimento?.text = user.dataNascimento
                celular?.text = user.celular
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}