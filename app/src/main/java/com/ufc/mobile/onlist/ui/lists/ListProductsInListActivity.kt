package com.ufc.mobile.onlist.ui.lists

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.adapter.ListItemProductInListAdapter
import com.ufc.mobile.onlist.model.List
import com.ufc.mobile.onlist.model.Marketplace
import com.ufc.mobile.onlist.model.ProductInList
import com.ufc.mobile.onlist.services.AuthUserService
import com.ufc.mobile.onlist.services.ProductsInListService
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.registers.RegisterProductInListActivity
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import com.ufc.mobile.onlist.util.ToastCustom
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.text.DecimalFormat

class ListProductsInListActivity: AppCompatActivity() {
    private var context: Context = this
    private lateinit var listsProductInList: ArrayList<ProductInList>
    private lateinit var listLists: ListView
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var marketSelected: Marketplace
    private lateinit var listSelected: List
    private lateinit var productsInListService: ProductsInListService
    private lateinit var textViewPriceList: TextView
    private lateinit var textViewQuantityProductsList: TextView
    private lateinit var authUserService: AuthUserService
    private var decimalFormat: DecimalFormat = DecimalFormat("#0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_list)

        this.authUserService = AuthUserService()
        this.productsInListService = ProductsInListService()

        this.getMarketplace()
        this.getList()

        this.textViewPriceList = findViewById(R.id.textViewInfoPriceList)
        this.textViewQuantityProductsList = findViewById(R.id.textViewInfoQuantityProducts)
        this.listLists = findViewById(R.id.listViewProductsInList)
        this.listsProductInList = ArrayList()

        this.loadAllProductsInList()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutAddProductOnListActivity)
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

    private fun loadAllProductsInList () {
        this.productsInListService.listAllProductsByList(this.listSelected.id) { productList, result ->
            if (result) {
                this.listLists.isClickable = true
                this.listLists.adapter = ListItemProductInListAdapter(this.context as ListProductsInListActivity, productList)
                this.listLists.setOnItemClickListener { parent, view, position, id ->
                    val productSelectd: ProductInList = productList.get(position)

                    this.productsInListService.updateProductBuied(productSelectd) { setResultSuccessful ->
                        if (setResultSuccessful) {
                            this.loadAllProductsInList()
                        } else {
                            var toast = ToastCustom(ToastCustom.WARNING, "Falha ao atualizar produto da lista!", this.context as ListProductsInListActivity)
                            toast.getToast().show()
                        }
                    }
                }

                this.textViewQuantityProductsList.setText("${productList.size} produtos")
                var sumPriceList: Double = 0.0

                for (product in productList) {
                    sumPriceList += product.price
                }

                this.textViewPriceList.setText("R$ ${decimalFormat.format(sumPriceList)}")
            } else {
                var toast = ToastCustom(ToastCustom.WARNING, "Falha ao carregar os produtos da lista!", this.context as ListProductsInListActivity)
                toast.getToast().show()
            }
        }
    }

    private fun getMarketplace () {
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

    fun addProductInList (view: View) {
        val addProductsList = Intent(this, RegisterProductInListActivity::class.java)
        startActivity(addProductsList)
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