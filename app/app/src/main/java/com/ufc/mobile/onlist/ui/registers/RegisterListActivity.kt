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
import com.benasher44.uuid.uuid4
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.dto.ListDTO
import com.ufc.mobile.onlist.model.Marketplace
import com.ufc.mobile.onlist.model.User
import com.ufc.mobile.onlist.services.AuthUserService
import com.ufc.mobile.onlist.services.ListService
import com.ufc.mobile.onlist.services.MarketplaceService
import com.ufc.mobile.onlist.services.UserService
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.lists.*
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import com.ufc.mobile.onlist.util.ToastCustom
import java.io.FileInputStream
import java.io.ObjectInputStream


class RegisterListActivity: AppCompatActivity() {
    private var context: Context = this
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var marketSelected: Marketplace
    private lateinit var listUsers: ArrayList<User>
    private lateinit var inputNameRegisterList: EditText
    private lateinit var inputMarketRegisterList: AutoCompleteTextView
    private lateinit var inputViewerRegigsterList: AutoCompleteTextView
    private lateinit var marketplaceService: MarketplaceService
    private lateinit var userService: UserService
    private lateinit var userLoged: User
    private lateinit var listService: ListService
    private lateinit var authUserService: AuthUserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_list)

        this.authUserService = AuthUserService()
        this.marketplaceService = MarketplaceService()
        this.userService = UserService()
        this.listService = ListService()

        this.getMarketplace()
        this.getUserLoged()

        this.listUsers = ArrayList()

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
                this.listUsers = usersList
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
        if (this.inputNameRegisterList.text.isEmpty()) {
            val toast = ToastCustom(ToastCustom.WARNING, "Por favor, preencha pelo menos o nome da lista!", this.context as RegisterListActivity)
            toast.getToast().show()
        } else {
            var userViewerId: String? = null

            if (!this.inputViewerRegigsterList.text.isEmpty()) {
                for (user in this.listUsers) {
                    if (user.email == this.inputViewerRegigsterList.text.toString()) {
                        userViewerId = user.id
                    }
                }
            }

            var listCreate = ListDTO(uuid4().toString(), this.marketSelected.id, this.inputNameRegisterList.text.toString(), userViewerId)

            this.listService.create(listCreate) { setResultSuccessful ->
                if (setResultSuccessful) {
                    var toast = ToastCustom(ToastCustom.SUCCESS, "Lista cadastrada com sucesso!", this.context as RegisterListActivity)
                    toast.getToast().show()

                    val intentListLists = Intent(this.context as RegisterListActivity, ListListsActivity::class.java)
                    startActivity(intentListLists)
                } else {
                    var toast = ToastCustom(ToastCustom.ERROR, "Erro ao cadastrar a lista!", this.context as RegisterListActivity)
                    toast.getToast().show()
                }
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