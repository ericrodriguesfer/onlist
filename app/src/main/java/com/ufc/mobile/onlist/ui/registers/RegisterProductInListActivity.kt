package com.ufc.mobile.onlist.ui.registers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.benasher44.uuid.uuid4
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.adapter.ListItemProductAdapter
import com.ufc.mobile.onlist.dto.AddProductInListDTO
import com.ufc.mobile.onlist.model.List
import com.ufc.mobile.onlist.model.Marketplace
import com.ufc.mobile.onlist.services.AuthUserService
import com.ufc.mobile.onlist.services.ProductService
import com.ufc.mobile.onlist.services.ProductsInListService
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.lists.*
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import com.ufc.mobile.onlist.util.ToastCustom
import kotlinx.android.synthetic.main.activity_list_products.*
import java.io.FileInputStream
import java.io.ObjectInputStream

class RegisterProductInListActivity: AppCompatActivity() {
    private var context: Context = this
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var listProductsForAddInList: ListView
    private lateinit var marketSelected: Marketplace
    private lateinit var listSelected: List
    private lateinit var productService: ProductService
    private lateinit var productsInListService: ProductsInListService
    private lateinit var authUserService: AuthUserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_products_in_list)

        this.productService = ProductService()
        this.productsInListService = ProductsInListService()
        this.authUserService = AuthUserService()

        this.getMarketplace()
        this.getList()

        this.listProductsForAddInList = findViewById(R.id.listViewProductsForList)

        this.getAllProducts()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutAddProductsInListActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(this.marketSelected.name + " - " + this.listSelected.name)
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

    private fun getAllProducts() {
        this.productService.listAllProductByMarket(this.marketSelected.id.toString()) { produtList, result ->
            if (result) {
                this.listProductsForAddInList.isClickable = true
                this.listProductsForAddInList.adapter = ListItemProductAdapter(this.context as RegisterProductInListActivity, produtList)
                this.listProductsForAddInList.setOnItemClickListener { parent, view, position, id ->
                    val addProduct = AddProductInListDTO(uuid4().toString(), this.listSelected.id, produtList.get(position).name, produtList.get(position).price, false)

                    this.productsInListService.addProductInList(addProduct) { setResultSuccessful ->
                        if (setResultSuccessful) {
                            var toast = ToastCustom(ToastCustom.SUCCESS, "Produto adicionado a lista com sucesso!", this.context as RegisterProductInListActivity)
                            toast.getToast().show()

                            val intentListProductsInList = Intent(this.context as RegisterProductInListActivity, ListProductsInListActivity::class.java)
                            startActivity(intentListProductsInList)
                        } else {
                            var toast = ToastCustom(ToastCustom.WARNING, "Falha ao adicionar produto a lista!", this.context as RegisterProductInListActivity)
                            toast.getToast().show()
                        }
                    }
                }
            } else {
                var toast = ToastCustom(ToastCustom.WARNING, "Falha ao carregar os produtos!", this.context as RegisterProductInListActivity)
                toast.getToast().show()
            }
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

    private fun getList () {
        val fileName = "selected_list"
        val file = this.getFileStreamPath(fileName)
        val fileInputStream = FileInputStream(file)
        val objectInputStream = ObjectInputStream(fileInputStream)
        this.listSelected = objectInputStream.readObject() as List

        fileInputStream.close()
        objectInputStream.close()
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