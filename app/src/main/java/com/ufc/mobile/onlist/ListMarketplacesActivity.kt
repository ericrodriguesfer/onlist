package com.ufc.mobile.onlist

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.adapter.ListItemMarketplaceAdapter
import com.ufc.mobile.onlist.data.MarketplaceData
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
            Toast.makeText(this, marketplaceDataList.get(position).name, Toast.LENGTH_SHORT).show()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutListMarketplaceActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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