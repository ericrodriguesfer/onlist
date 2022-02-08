package com.ufc.mobile.onlist.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.model.Marketplace
import com.ufc.mobile.onlist.model.User
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.lists.ListListsActivity
import com.ufc.mobile.onlist.ui.lists.ListMarketplacesActivity
import com.ufc.mobile.onlist.ui.lists.ListMarketplacesForProductActivity
import com.ufc.mobile.onlist.ui.lists.ListProductsActivity
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import java.io.FileInputStream
import java.io.ObjectInputStream

class HomeMarketplaceActivity: AppCompatActivity() {
    private var context: Context = this
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var userLoged: User
    private lateinit var marketSelected: Marketplace

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_marketplace)

        this.getUserLoged()
        this.getMarketplace()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutHomeMarketActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(this.marketSelected.name + " - Home")
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

    fun clickLinkProductsList (view: View) {
        var intentListProducts = Intent(this.context as HomeMarketplaceActivity, ListProductsActivity::class.java)
        startActivity(intentListProducts)
    }
    fun clickListListsList (view: View) {
        var intentListsList = Intent(this.context as HomeMarketplaceActivity, ListListsActivity::class.java)
        startActivity(intentListsList)
    }

    private fun getUserLoged() {
        val fileName = "user_loged"
        val file = this.getFileStreamPath(fileName)
        val fileInputStream = FileInputStream(file)
        val objectInputStream = ObjectInputStream(fileInputStream)
        this.userLoged = objectInputStream.readObject() as User

        fileInputStream.close()
        objectInputStream.close()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}