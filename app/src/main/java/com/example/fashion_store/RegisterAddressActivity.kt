package com.example.fashion_store

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fashion_store.entity.Endereco
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_address)

        val btnRegisterAddress = findViewById<Button>(R.id.btn_register_address)
        btnRegisterAddress.setOnClickListener {
            when{
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_address_state).text.toString().trim{ it <=' '}) -> {
                    Toast.makeText(
                            this@RegisterAddressActivity,
                            "Por favor, insira o nome do Estado",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_address_address).text.toString().trim{ it <=' '}) -> {
                    android.widget.Toast.makeText(
                            this@RegisterAddressActivity,
                            "Por favor, insira a descrição do produto",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_address_cep).text.toString().trim{ it <=' '}) -> {
                    android.widget.Toast.makeText(
                            this@RegisterAddressActivity,
                            "Por favor, insira a descrição do produto",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_address_city).text.toString().trim{ it <=' '}) -> {
                    android.widget.Toast.makeText(
                            this@RegisterAddressActivity,
                            "Por favor, insira a descrição do produto",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_address_number).text.toString().trim{ it <=' '}) -> {
                    android.widget.Toast.makeText(
                            this@RegisterAddressActivity,
                            "Por favor, insira a descrição do produto",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_address_complement).text.toString().trim{ it <=' '}) -> {
                    android.widget.Toast.makeText(
                            this@RegisterAddressActivity,
                            "Por favor, insira a descrição do produto",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_address_recipient).text.toString().trim{ it <=' '}) -> {
                    android.widget.Toast.makeText(
                            this@RegisterAddressActivity,
                            "Por favor, insira a descrição do produto",
                            Toast.LENGTH_SHORT
                    ).show()

                }
                else -> {

                    addAddress()
                    val intent =
                            Intent(this@RegisterAddressActivity, MainActivity::class.java)
                    intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()

                }
            }
        }

    }

    private fun addAddress() {
        val destinatario: String =
                findViewById<EditText>(R.id.et_register_address_recipient).text.toString()
                        .trim { it <= ' ' }
        val cep: String =
                findViewById<EditText>(R.id.et_register_address_cep).text.toString()
                        .trim { it <= ' ' }
        val endereco: String =
                findViewById<EditText>(R.id.et_register_address_address).text.toString()
                        .trim { it <= ' ' }
        val complemento: String =
                findViewById<EditText>(R.id.et_register_address_complement).text.toString()
                        .trim { it <= ' ' }
        val numero: String =
                findViewById<EditText>(R.id.et_register_address_number).text.toString()
                        .trim { it <= ' ' }
        val cidade: String =
                findViewById<EditText>(R.id.et_register_address_city).text.toString()
                        .trim { it <= ' ' }
        val estado: String =
                findViewById<EditText>(R.id.et_register_address_state).text.toString()
                        .trim { it <= ' ' }
        val pontoReferencia: String =
                findViewById<EditText>(R.id.et_register_address_reference_point).text.toString()
                        .trim { it <= ' ' }

        val address = Endereco(destinatario, cep, endereco, complemento, numero, cidade, estado, pontoReferencia)

        persistAddress(address)

    }

    private fun persistAddress(address: Endereco) {
        val userId = FirebaseAuth.getInstance().currentUser.uid
        val bancoDeDados = FirebaseDatabase.getInstance()
        val mBanco = bancoDeDados.getReference("address").child(userId)
        val keyid : String? = mBanco.push().key
        if (keyid != null) {
            mBanco.child(keyid).setValue(address)
        }
    }
}