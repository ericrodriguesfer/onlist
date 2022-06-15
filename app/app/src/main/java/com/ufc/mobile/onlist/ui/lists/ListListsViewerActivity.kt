package com.ufc.mobile.onlist.ui.lists

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.adapter.ListItemListAdapter
import com.ufc.mobile.onlist.model.Marketplace
import com.ufc.mobile.onlist.model.User
import com.ufc.mobile.onlist.services.AuthUserService
import com.ufc.mobile.onlist.services.ListService
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import com.ufc.mobile.onlist.util.ToastCustom
import kotlinx.android.synthetic.main.activity_list_lists.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ListListsViewerActivity: AppCompatActivity() {
    private var context: Context = this
    private lateinit var listLists: ListView
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var marketSelected: Marketplace
    private lateinit var listService: ListService
    private lateinit var authUserService: AuthUserService
    private lateinit var userLoged: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lists_viewer)

        this.listService = ListService()
        this.authUserService = AuthUserService()
        this.listLists = findViewById(R.id.listViewListsViewer)

        this.getUserLoged()
        this.getMarketplace()
        this.loadAllLists()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutListListsViewerActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Listas compartilhadas com você")
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_market -> {
                    val intentListMarkets = Intent(this, ListMarketplacesActivity::class.java)
                    startActivity(intentListMarkets)
                }

                R.id.nav_list_buy -> {
                    val intentMarketsListForList = Intent(this, ListMarketplacesForListsActivity::class.java)
                    startActivity(intentMarketsListForList)
                }

                R.id.nav_map_markets -> {
                    val intentMap = Intent(this, MapActivity::class.java)
                    startActivity(intentMap)
                }

                R.id.nav_list_products -> {
                    val intentMarketsListForProducts = Intent(this, ListMarketplacesForProductActivity::class.java)
                    startActivity(intentMarketsListForProducts)
                }

                R.id.nav_list_shared -> {
                    val intentListsViewer = Intent(this, ListListsViewerActivity::class.java)
                    startActivity(intentListsViewer)
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

    private fun loadAllLists () {
        this.listService.listAllListToViewer(this.userLoged.id.toString()) { listsList, result ->
            if (result) {
                this.listLists.isClickable = true
                this.listLists.adapter = ListItemListAdapter(this.context as ListListsViewerActivity, this.marketSelected.name, listsList)
                this.listLists.setOnItemClickListener { parent, view, position, id ->
                    val fileName = "selected_list"
                    val file = this.context.getFileStreamPath(fileName)
                    val fileOutputStream = FileOutputStream(file)
                    val objectOutputStream = ObjectOutputStream(fileOutputStream)

                    objectOutputStream.writeObject(listsList.get(position))
                    objectOutputStream.close()
                    fileOutputStream.close()

                    val intentProductsInListViewer = Intent(this.context as ListListsViewerActivity, ListProductsInListViewerActivity::class.java)
                    startActivity(intentProductsInListViewer)
                }
            } else {
                var toast = ToastCustom(ToastCustom.WARNING, "Falha ao carregar as listas!", this.context as ListListsViewerActivity)
                toast.getToast().show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        this.authUserService.logout()
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }

}