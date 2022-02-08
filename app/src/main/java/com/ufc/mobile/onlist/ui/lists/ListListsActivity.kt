package com.ufc.mobile.onlist.ui.lists

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.adapter.ListItemListAdapter
import com.ufc.mobile.onlist.data.ListData
import com.ufc.mobile.onlist.model.Marketplace
import com.ufc.mobile.onlist.model.User
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.registers.RegisterListActivity
import com.ufc.mobile.onlist.ui.registers.RegisterProductInListActivity
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import kotlinx.android.synthetic.main.activity_list_lists.*
import java.io.FileInputStream
import java.io.ObjectInputStream

class ListListsActivity: AppCompatActivity() {
    private var context: Context = this
    private lateinit var listsDataList: ArrayList<ListData>
    private lateinit var listLists: ListView
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var marketSelected: Marketplace

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lists)

        this.listLists = findViewById(R.id.listViewLists)
        this.listsDataList = ArrayList()

        this.getMarketplace()

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
        supportActionBar?.setTitle(this.marketSelected.name + " - Lista de Compras")
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