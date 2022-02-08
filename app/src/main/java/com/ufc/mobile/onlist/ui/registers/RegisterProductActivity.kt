package com.ufc.mobile.onlist.ui.registers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.benasher44.uuid.uuid4
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.dto.ProductDTO
import com.ufc.mobile.onlist.model.Marketplace
import com.ufc.mobile.onlist.services.ProductService
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

class RegisterProductActivity: AppCompatActivity() {
    private var context: Context = this
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var marketSelected: Marketplace
    private lateinit var inputNameProductRegister: EditText
    private lateinit var inputPriceProductRegister: EditText
    private lateinit var productService: ProductService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_product)

        this.getMarketplace()
        this.productService = ProductService()
        this.inputNameProductRegister = findViewById(R.id.inputNameFormRegisterProduct)
        this.inputPriceProductRegister = findViewById(R.id.inputPriceFormRegisterProduct)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutRegisterProductActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(this.marketSelected.name +  " - Cadastro de Produtos")
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

    fun registerProduct (view: View) {
        if (this.inputNameProductRegister.text.isEmpty() || this.inputPriceProductRegister.text.isEmpty()) {
            val toast = ToastCustom(ToastCustom.WARNING, "Por favor, preencha todos os campos!", this.context as RegisterProductActivity)
            toast.getToast().show()
        } else {
            var createProduct = ProductDTO(uuid4().toString(), this.marketSelected.id.toString(), this.inputNameProductRegister.text.toString(), this.inputPriceProductRegister.text.toString().toDouble())

            this.productService.create(createProduct) { resultSuccessful ->
                if (resultSuccessful) {
                    var toast = ToastCustom(ToastCustom.SUCCESS, "Produto cadastrado com sucesso!", this.context as RegisterProductActivity)
                    toast.getToast().show()

                    val intentListProduct = Intent(this.context as RegisterProductActivity, ListProductsActivity::class.java)
                    startActivity(intentListProduct)
                } else {
                    var toast = ToastCustom(ToastCustom.ERROR, "Erro ao cadastrar o produto!", this.context as RegisterProductActivity)
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
}