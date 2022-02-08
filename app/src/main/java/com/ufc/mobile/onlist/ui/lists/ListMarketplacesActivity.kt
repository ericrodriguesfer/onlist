package com.ufc.mobile.onlist.ui.lists

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.adapter.ListItemMarketplaceAdapter
import com.ufc.mobile.onlist.data.MarketplaceData
import com.ufc.mobile.onlist.model.User
import com.ufc.mobile.onlist.services.AuthUserService
import com.ufc.mobile.onlist.services.MarketplaceService
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.registers.RegisterMarketplaceActivity
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import com.ufc.mobile.onlist.util.ToastCustom
import kotlinx.android.synthetic.main.activity_list_marketplaces.*
import java.io.FileInputStream
import java.io.ObjectInputStream

class ListMarketplacesActivity: AppCompatActivity() {
    private var context: Context = this
    private lateinit var marketplaceService: MarketplaceService
    private lateinit var authUserService: AuthUserService
    private lateinit var listMarketplaces: ListView
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var userLoged: User

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_marketplaces)

        this.authUserService = AuthUserService()
        this.marketplaceService = MarketplaceService()

        this.getUserLoged()

        this.listMarketplaces = findViewById(R.id.listViewMarketplaces)

        this.getAllMarketplaces()

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
                    this.logout()
                }
            }

            true
        }
    }

    fun craateNewMarket (view: View) {
        val intentNewMarket = Intent(this, RegisterMarketplaceActivity::class.java)
        startActivity(intentNewMarket)
    }

    private fun getAllMarketplaces() {
        this.marketplaceService.listAllMarketsByIdUser(this.userLoged.id.toString()) { marketList, result ->
            if (result) {
                this.listViewMarketplaces.isClickable = true
                this.listViewMarketplaces.adapter = ListItemMarketplaceAdapter(this, marketList)
                this.listMarketplaces.setOnItemClickListener { parent, view, position, id ->
                    val intentListsBuy = Intent(this, ListListsActivity::class.java)
                    startActivity(intentListsBuy)
                }
            } else {
                var toast = ToastCustom(ToastCustom.WARNING, "Falha ao carregar os mercados!", this.context as ListMarketplacesActivity)
                toast.getToast().show()
            }
        }
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

    private fun logout() {
        this.authUserService.logout()
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}