package com.example.fashion_store

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.fashion_store.entity.Produto
import com.example.fashion_store.entity.User
import com.example.fashion_store.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mFirebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mFirebaseAuth = FirebaseAuth.getInstance()


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_pessoais, R.id.nav_endereco, R.id.nav_carrinho, R.id.nav_pedidos), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onStart() {
        super.onStart()
        val userName = intent.getStringExtra("user_name")
        val userEmail = intent.getStringExtra("user_email")
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val nav_header = navigationView.getHeaderView(0)
        val username = nav_header.findViewById<TextView>(R.id.tv_user_name)
        val cadastrese = nav_header.findViewById<TextView>(R.id.tv_user_name3)
        val ou = nav_header.findViewById<TextView>(R.id.tv_user_name2)
        //val btnLoginOrLogout = nav_header.findViewById<Button>(R.id.btn_login_or_logout)

        val mFirebaseUser : FirebaseUser? = mFirebaseAuth.currentUser



        if (mFirebaseUser == null){
            username.text = "Entre"
            username.setOnClickListener{
                val intent =
                        Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            cadastrese.setOnClickListener{
                val intent =
                        Intent(this@MainActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }

        }else{
            val mDatabase = FirebaseDatabase.getInstance().reference
            val userId = FirebaseAuth.getInstance().currentUser.uid
            mDatabase.child("user").child(userId).get().addOnSuccessListener {
                val user = it.getValue(User :: class.java )
                if (user != null) {
                    username.text = user.nomeCompleto.toString()
                }
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

            cadastrese.visibility = TextView.INVISIBLE
            ou.visibility = TextView.INVISIBLE
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout){
            FirebaseAuth.getInstance().signOut()
            val intent =
                Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}