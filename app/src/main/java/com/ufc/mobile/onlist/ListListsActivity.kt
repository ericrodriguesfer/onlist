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
import com.ufc.mobile.onlist.adapter.ListItemListAdapter
import com.ufc.mobile.onlist.data.ListData
import com.ufc.mobile.onlist.util.ToastCustom
import kotlinx.android.synthetic.main.activity_list_lists.*

class ListListsActivity: AppCompatActivity() {

    private lateinit var listsDataList: ArrayList<ListData>
    private lateinit var listLists: ListView
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lists)

        this.listLists = findViewById(R.id.listViewLists)
        this.listsDataList = ArrayList()

        for (i in 1..20) {
            val listData: ListData = ListData("Lista de número ${i}", "Mercaso seu Zé")
            this.listsDataList.add(listData)
        }

        this.listViewLists.isClickable = true
        this.listViewLists.adapter = ListItemListAdapter(this, listsDataList)
        this.listLists.setOnItemClickListener { parent, view, position, id ->
            val intentProductsInList = Intent(this, RegisterProductInListActivity::class.java)
            startActivity(intentProductsInList)
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutListListsActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Lista de Compras")
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

                R.id.nav_logout -> {
                    val intentLogin = Intent(this, LoginActivity::class.java)
                    startActivity(intentLogin)
                }
            }

            true
        }
    }

    fun createNewList (view: View) {
        val intentNewList = Intent(this, RegisterListActivity::class.java)
        startActivity(intentNewList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}