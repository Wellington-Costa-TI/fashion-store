package com.example.fashion_store

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.fashion_store.entity.Produto
import com.example.fashion_store.entity.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class RegisterProductActivity : AppCompatActivity() {
    var uriImagemSelecionada : Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_product)

        val btnRegisterProductPhoto = findViewById<Button>(R.id.btn_register_product_photo)
        btnRegisterProductPhoto.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type ="image/*"
            startActivityForResult(intent, 10)
        }

        val btnRegisterProduct = findViewById<Button>(R.id.btn_register_product)
        btnRegisterProduct.setOnClickListener{
            when{
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_product_name).text.toString().trim{ it <=' '}) -> {
                    Toast.makeText(
                        this@RegisterProductActivity,
                        "Por favor, insira o nome do Produto",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_product_description).text.toString().trim{ it <=' '}) -> {
                    android.widget.Toast.makeText(
                        this@RegisterProductActivity,
                        "Por favor, insira a descrição do produto",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    addUser()
                    val intent =
                        Intent(this@RegisterProductActivity, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent
                    )
                    finish()

                }
            }
        }
                }

    private fun addUser() {
        val nome: String =
                findViewById<EditText>(R.id.et_register_product_name).text.toString()
                        .trim { it <= ' ' }
        val descricao: String =
                findViewById<EditText>(R.id.et_register_product_description).text.toString()
                        .trim { it <= ' ' }

        val valor: Double =
                findViewById<EditText>(R.id.et_register_product_value).text.toString().toDouble()

        val estoque: Int =
                findViewById<EditText>(R.id.et_register_product_name_quantity).text.toString().toInt()

        val produto =  Produto(nome, descricao, valor, estoque, "","")
        saveNewProduct(produto)
    }

    private fun saveNewProduct(novoProduto : Produto) {
        var retorno: String = ""
        if (uriImagemSelecionada == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(uriImagemSelecionada!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    persistNewProduct(it.toString(), novoProduto)
                }
            }
    }

    private fun persistNewProduct(imageRemotePath: String, novoProduto: Produto) {



        val bancoDeDados = FirebaseDatabase.getInstance()
        val mBanco = bancoDeDados.getReference("product")
        val keyid : String? = mBanco.push().key
        if (keyid != null) {
            val produto = Produto(novoProduto.nome,novoProduto.descricao, novoProduto.valor, novoProduto.estoque, imageRemotePath, keyid)
            mBanco.child(keyid).setValue(produto)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null){
            uriImagemSelecionada = data.data
            findViewById<ImageView>(R.id.iv_register_product_image).setImageURI(uriImagemSelecionada)
        }
    }
}