package com.example.fashion_store

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fashion_store.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val enterText = findViewById<TextView>(R.id.tv_login)

        enterText.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }

        val btnRegister = findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener{
            when{
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_email_address).text.toString().trim{ it <=' '}) -> {
                  Toast.makeText(
                    this@RegisterActivity,
                      "Por favor, insira o email",
                      Toast.LENGTH_SHORT
                    ).show()
                }
                 TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_password).text.toString().trim{ it <=' '}) -> {
                android.widget.Toast.makeText(
                    this@RegisterActivity,
                    "Por favor, insira a senha",
                    Toast.LENGTH_SHORT
                ).show()
            }
                else ->{
                    val email : String = findViewById<EditText>(R.id.et_register_email_address).text.toString().trim{ it <=' '}
                    val senha : String = findViewById<EditText>(R.id.et_register_password).text.toString().trim{ it <=' '}
                    val nome : String = findViewById<EditText>(R.id.et_register_name).text.toString().trim{ it <=' '}
                    val celular : String = findViewById<EditText>(R.id.et_register_phone).text.toString().trim{ it <=' '}
                    val dataNascimento : String = findViewById<EditText>(R.id.et_register_date).text.toString().trim{ it <=' '}
                    val usuario = User(nome,celular,email,dataNascimento)

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                android.widget.Toast.makeText(
                                    this@RegisterActivity,
                                    "Cadastrado com sucesso!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                    val bancoDeDados = FirebaseDatabase.getInstance()
                                    val mBanco = bancoDeDados.getReference("user")
                                        mBanco.child(firebaseUser.uid).setValue(usuario)

                                val intent =
                                    Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_name", nome)
                                intent.putExtra("user_email", email)
                                startActivity(intent)
                                finish()
                            } else {
                                android.widget.Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message!!.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
}