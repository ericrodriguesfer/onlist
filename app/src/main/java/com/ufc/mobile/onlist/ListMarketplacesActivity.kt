package com.ufc.mobile.onlist

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.adapter.ListItemMarketplaceAdapter
import com.ufc.mobile.onlist.data.MarketplaceData
import com.ufc.mobile.onlist.util.ToastCustom
import kotlinx.android.synthetic.main.activity_list_marketplaces.*

class ListMarketplacesActivity: AppCompatActivity() {

    private lateinit var marketplaceDataList: ArrayList<MarketplaceData>
    private lateinit var listMarketplaces: ListView
    lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_marketplaces)

        this.listMarketplaces = findViewById(R.id.listViewMarketplaces)
        this.marketplaceDataList = ArrayList()

        for (i in 1..20) {
            val marketplaceData: MarketplaceData = MarketplaceData("Mercado ${i}")
            this.marketplaceDataList.add(marketplaceData)
        }

        this.listViewMarketplaces.isClickable = true
        this.listViewMarketplaces.adapter = ListItemMarketplaceAdapter(this, marketplaceDataList)
        this.listMarketplaces.setOnItemClickListener { parent, view, position, id ->
            val intentListsBuy = Intent(this, ListListsActivity::class.java)
            startActivity(intentListsBuy)
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutListMarketplaceActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Lista de Mercados")
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

    fun craateNewMarket (view: View) {
        val intentNewMarket = Intent(this, RegisterMarketplaceActivity::class.java)
        startActivity(intentNewMarket)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}