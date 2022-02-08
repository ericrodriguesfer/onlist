package com.ufc.mobile.onlist.ui.registers

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.lists.ListListsActivity
import com.ufc.mobile.onlist.ui.lists.ListMarketplacesActivity
import com.ufc.mobile.onlist.ui.lists.ListProductsActivity
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import kotlinx.android.synthetic.main.activity_register_product.*

class RegisterProductActivity: AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_product)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutRegisterProductActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Cadastro de Produtos")
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_market -> {
                    val intentListMarkets = Intent(this, ListMarketplacesActivity::class.java)
                    startActivity(intentListMarkets)
                }

                R.id.nav_list_buy -> {
                    val intentListsBuy = Intent(this, ListListsActivity::class.java)
                    startActivity(intentListsBuy)
                }

                R.id.nav_map_markets -> {
                    val intentMap = Intent(this, MapActivity::class.java)
                    startActivity(intentMap)
                }

                R.id.nav_list_products -> {
                    val productsList = Intent(this, ListProductsActivity::class.java)
                    startActivity(productsList)
                }

                R.id.nav_list_shared -> {
                    val intentListsBuy = Intent(this, ListListsActivity::class.java)
                    startActivity(intentListsBuy)
                }

                R.id.nav_list_edit -> {
                    val intentUpdatePerfil = Intent(this, UpdateUserActivity::class.java)
                    startActivity(intentUpdatePerfil)
                }

                R.id.nav_logout -> {
                    val intentLogin = Intent(this, LoginActivity::class.java)
                    startActivity(intentLogin)
                }
            }

            true
        }

        registerProductAction.setOnClickListener({
            val nome = inputEmailFormRegisterProduct.text.toString();
            val preco: Float = inputPriceFormRegisterProduct.text.toString().toFloat();

            if(nome.isEmpty() || preco.isNaN()){
                Toast.makeText(this, "Por favor insira todos os dados", Toast.LENGTH_SHORT).show();
            }
            saveFirestore(nome, preco);
        })
    }

    fun registerProduct (view: View) {
        val intentListProducts = Intent(this, ListProductsActivity::class.java)
        startActivity(intentListProducts)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun saveFirestore(nome: String, preco: Float){
        val db = FirebaseFirestore.getInstance();
        val product: MutableMap<String, Any> = HashMap();
        product["nome"] = nome;
        product["preco"] = preco;

        db.collection("products")
            .add(product)
            .addOnSuccessListener {
                Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao cadastrar o produto", Toast.LENGTH_SHORT).show();
            }
    }
}