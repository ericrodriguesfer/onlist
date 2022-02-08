package com.ufc.mobile.onlist.ui.registers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.google.protobuf.LazyStringArrayList
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.model.Marketplace
import com.ufc.mobile.onlist.model.User
import com.ufc.mobile.onlist.services.MarketplaceService
import com.ufc.mobile.onlist.services.UserService
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.lists.ListListsActivity
import com.ufc.mobile.onlist.ui.lists.ListMarketplacesActivity
import com.ufc.mobile.onlist.ui.lists.ListMarketplacesForProductActivity
import com.ufc.mobile.onlist.ui.lists.ListProductsActivity
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import com.ufc.mobile.onlist.util.ToastCustom
import java.io.FileInputStream
import java.io.ObjectInputStream


class RegisterListActivity: AppCompatActivity() {
    private var context: Context = this
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var marketSelected: Marketplace
    private lateinit var listUsers: ArrayList<User>
    private lateinit var listMarkets: ArrayList<Marketplace>
    private lateinit var inputNameRegisterList: EditText
    private lateinit var inputMarketRegisterList: AutoCompleteTextView
    private lateinit var inputViewerRegigsterList: AutoCompleteTextView
    private lateinit var marketplaceService: MarketplaceService
    private lateinit var userService: UserService
    private lateinit var userLoged: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_list)

        this.marketplaceService = MarketplaceService()
        this.userService = UserService()

        this.getMarketplace()
        this.getUserLoged()

        this.listUsers = ArrayList()
        this.listMarkets = ArrayList()

        this.inputNameRegisterList = findViewById(R.id.inputNameFormRegisterList)
        this.inputMarketRegisterList = findViewById(R.id.inputMarketFormRegisterList)
        this.inputViewerRegigsterList = findViewById(R.id.inputViewerFormRegisterList)

        this.loadAllMarkets()
        this.loadAllUsers()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(this.marketSelected.name + " - Cadastro de Lista")
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

    private fun loadAllMarkets () {
        this.marketplaceService.listAllMarketsByIdUser(this.userLoged.id.toString()) { marketList, result ->
            if (result) {
                var listNamesMarket = Array<String>(marketList.size) { "it = $it" }

                for (i in 0 until marketList.size) {
                    listNamesMarket[i] = marketList.get(i).name
                }

                var adapterMarketSugestions = ArrayAdapter<String>(this.context as RegisterListActivity, android.R.layout.simple_dropdown_item_1line, listNamesMarket)
                this.inputMarketRegisterList.setAdapter(adapterMarketSugestions)
                this.inputMarketRegisterList.setText(this.marketSelected.name)
            } else {
                var toast = ToastCustom(ToastCustom.WARNING, "Falha ao carregar os mercados!", this.context as RegisterListActivity)
                toast.getToast().show()
            }
        }
    }

    private fun loadAllUsers () {
        this.userService.listAllUsers { usersList, result ->
            if (result) {
                var listEmailsUsers = Array<String>(usersList.size) { "it = $it" }

                for (i in 0 until usersList.size) {
                    if (usersList.get(i).email != this.userLoged.email) {
                        listEmailsUsers[i] = usersList.get(i).email
                    }
                }

                var adapterViewersSugestions =
                    ArrayAdapter<String>(this.context as RegisterListActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        listEmailsUsers)
                this.inputViewerRegigsterList.setAdapter(adapterViewersSugestions)
            } else {
                var toast = ToastCustom(ToastCustom.WARNING,
                    "Falha ao carregar os visualizadores!",
                    this.context as RegisterListActivity)
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

    private fun getMarketplace() {
        val fileName = "market_selected"
        val file = this.getFileStreamPath(fileName)
        val fileInputStream = FileInputStream(file)
        val objectInputStream = ObjectInputStream(fileInputStream)
        this.marketSelected = objectInputStream.readObject() as Marketplace

        fileInputStream.close()
        objectInputStream.close()
    }

    fun registerList (view: View) {
        val intentListsBuy = Intent(this, ListListsActivity::class.java)
        startActivity(intentListsBuy)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}