package com.example.fashion_store

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fashion_store.entity.Produto
import com.example.fashion_store.entity.User
import com.example.fashion_store.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvCadastreSe = findViewById<TextView>(R.id.tv_register)
        tvCadastreSe.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener{
            when{
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_login_email).text.toString().trim{ it <=' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Por favor, insira o email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_login_senha).text.toString().trim{ it <=' '}) -> {
                    android.widget.Toast.makeText(
                        this@LoginActivity,
                        "Por favor, insira a senha",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else ->{
                    val email : String = findViewById<EditText>(R.id.et_login_email).text.toString().trim{ it <=' '}
                    val senha : String = findViewById<EditText>(R.id.et_login_senha).text.toString().trim{ it <=' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                android.widget.Toast.makeText(
                                    this@LoginActivity,
                                    "Login realizado com sucesso!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent =
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


                                intent.putExtra("user_email", email)
                                startActivity(intent)
                                finish()
                            } else {
                                android.widget.Toast.makeText(
                                    this@LoginActivity,
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