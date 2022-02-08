package com.ufc.mobile.onlist.ui.lists

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.adapter.ListItemProductAdapter
import com.ufc.mobile.onlist.data.ProductData
import com.ufc.mobile.onlist.model.Marketplace
import com.ufc.mobile.onlist.services.ProductService
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.registers.RegisterProductActivity
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import com.ufc.mobile.onlist.util.ToastCustom
import kotlinx.android.synthetic.main.activity_list_products.*
import java.io.FileInputStream
import java.io.ObjectInputStream

class ListProductsActivity: AppCompatActivity() {
    private var context: Context = this
    private lateinit var productsDataList: ArrayList<ProductData>
    private lateinit var listProducts: ListView
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var marketSelected: Marketplace
    private lateinit var productService: ProductService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_products)

        this.getMarketplace()

        this.productService = ProductService()
        this.listProducts = findViewById(R.id.listViewProducts)
        this.productsDataList = ArrayList()
        this.getAllProducts()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutListProductsActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(this.marketSelected.name + " - Lista de Produtos")
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
                    val marketsListForProducts = Intent(this, ListMarketplacesForProductActivity::class.java)
                    startActivity(marketsListForProducts)
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
    }

    private fun getMarketplace() {
        val fileName = "market_selected"
        val file = this.getFileStreamPath(fileName)
        val fileInputStream = FileInputStream(file)
        val objectInputStream = ObjectInputStream(fileInputStream)
        this.marketSelected = objectInputStream.readObject() as Marketplace

        fileInputStream.close()
        objectInputStream.close()
    }

    private fun getAllProducts() {
        this.productService.listAllProductByMarket(this.marketSelected.id.toString()) { produtList, result ->
            if (result) {
                this.listViewProducts.isClickable = true
                this.listViewProducts.adapter = ListItemProductAdapter(this.context as ListProductsActivity, produtList)
                this.listProducts.setOnItemClickListener { parent, view, position, id ->
                    Toast.makeText(this, productsDataList.get(position).name, Toast.LENGTH_SHORT).show()
                }
            } else {
                var toast = ToastCustom(ToastCustom.WARNING, "Falha ao carregar os produtos!", this.context as ListProductsActivity)
                toast.getToast().show()
            }
        }
    }

    fun addProduct (view: View) {
        val intentRegisterProduct = Intent(this, RegisterProductActivity::class.java)
        startActivity(intentRegisterProduct)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}