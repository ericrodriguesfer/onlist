package com.ufc.mobile.onlist

import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.adapter.ListItemProductInListAdapter
import com.ufc.mobile.onlist.data.ProductInListData
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
        this.listLists.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, listsProductInListData.get(position).name, Toast.LENGTH_SHORT).show()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutAddProductOnListActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Nome da lista")
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_market -> Toast.makeText(applicationContext, "Clicked home", Toast.LENGTH_SHORT).show()
                R.id.nav_list_buy -> Toast.makeText(applicationContext, "Clicked home", Toast.LENGTH_SHORT).show()
                R.id.nav_map_markets -> Toast.makeText(applicationContext, "Clicked home", Toast.LENGTH_SHORT).show()
                R.id.nav_list_products -> Toast.makeText(applicationContext, "Clicked home", Toast.LENGTH_SHORT).show()
                R.id.nav_list_shared -> Toast.makeText(applicationContext, "Clicked home", Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> Toast.makeText(applicationContext, "Clicked home", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}