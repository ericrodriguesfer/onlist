package com.ufc.mobile.onlist

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
import com.ufc.mobile.onlist.adapter.ListItemProductInListAdapter
import com.ufc.mobile.onlist.data.ProductInListData
import com.ufc.mobile.onlist.util.ToastCustom
import kotlinx.android.synthetic.main.activity_add_product_list.*

class RegisterProductInListActivity: AppCompatActivity() {

    private lateinit var listsProductInListData: ArrayList<ProductInListData>
    private lateinit var listLists: ListView
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_list)

        this.listLists = findViewById(R.id.listViewProductsInList)
        this.listsProductInListData = ArrayList()

        for (i in 1..20) {
            val productListData: ProductInListData = ProductInListData("Maça ${i}", (i * 1.0), (i * 2))
            this.listsProductInListData.add(productListData)
        }

        this.listViewProductsInList.isClickable = true
        this.listViewProductsInList.adapter = ListItemProductInListAdapter(this, listsProductInListData)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutAddProductOnListActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Nome da lista")
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
                    val toastCustom = ToastCustom(ToastCustom.WARNING, "Ainda será implementada na parte de sensores", this)
                    toastCustom.getToast().show()
                }

                R.id.nav_list_products -> {
                    val productsList = Intent(this, ListProductsActivity::class.java)
                    startActivity(productsList)
                }

                R.id.nav_list_shared -> {
                    val intentListsBuy = Intent(this, ListListsActivity::class.java)
                    startActivity(intentListsBuy)
                }

                R.id.nav_logout -> {
                    val intentLogin = Intent(this, LoginActivity::class.java)
                    startActivity(intentLogin)
                }
            }

            true
        }
    }

    fun addProductInList (view: View) {
        val productsList = Intent(this, ListProductsActivity::class.java)
        startActivity(productsList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}