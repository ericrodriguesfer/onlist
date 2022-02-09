package com.ufc.mobile.onlist.ui.registers

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.benasher44.uuid.uuid4
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.dto.MarketplaceDTO
import com.ufc.mobile.onlist.model.User
import com.ufc.mobile.onlist.services.AuthUserService
import com.ufc.mobile.onlist.services.MarketplaceService
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.lists.*
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import com.ufc.mobile.onlist.util.ToastCustom
import java.io.FileInputStream
import java.io.ObjectInputStream

class RegisterMarketplaceActivity: AppCompatActivity() {
    private var context: Context = this
    private lateinit var marketplaceService: MarketplaceService
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var inputNameRegisterMarket: EditText
    private lateinit var inputStreetRegisterMarket: EditText
    private lateinit var inputLatitudeRegisterMarket: EditText
    private lateinit var inputLongitudeRegisterMarket: EditText
    private lateinit var inputDistrictRegisterMarket: EditText
    private lateinit var inputCityRegisterMarket: EditText
    private lateinit var inputCepRegisterMarket: EditText
    private lateinit var inputNumberRegisterMarket: EditText
    private lateinit var userLoged: User
    private lateinit var client: FusedLocationProviderClient
    private lateinit var authUserService: AuthUserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_market)

        this.authUserService = AuthUserService()
        this.marketplaceService = MarketplaceService()
        this.getUserLoged()
        this.client = LocationServices.getFusedLocationProviderClient(this.context as RegisterMarketplaceActivity)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutRegisterMarket)
        val navView : NavigationView = findViewById(R.id.nav_view)

        this.inputNameRegisterMarket = findViewById(R.id.inputNameMarketFormRegisterMarket)
        this.inputStreetRegisterMarket = findViewById(R.id.inputNameStreetRegisterMarket)
        this.inputLatitudeRegisterMarket = findViewById(R.id.inputNameLatitudeRegisterMarket)
        this.inputLongitudeRegisterMarket = findViewById(R.id.inputLongitudeRegisterMarket)
        this.inputDistrictRegisterMarket = findViewById(R.id.inputNameDistrictRegisterMarket)
        this.inputCityRegisterMarket = findViewById(R.id.inputNameCityRegisterMarket)
        this.inputCepRegisterMarket = findViewById(R.id.inputcepFormRegisterMarket)
        this.inputNumberRegisterMarket = findViewById(R.id.inputNumberFormRegisterMarket)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Cadastro de Mercado")
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

    override fun onResume() {
        super.onResume()

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        this.client.lastLocation.addOnSuccessListener { location ->
            if (location !== null) {
                this.inputLatitudeRegisterMarket.setText(location.latitude.toString())
                this.inputLongitudeRegisterMarket.setText(location.longitude.toString())
            } else {
                var toast = ToastCustom(ToastCustom.WARNING, "Falha ao pegar a localização", this.context as RegisterMarketplaceActivity)
                toast.getToast().show()
            }
        }.addOnFailureListener { error ->
            var toast = ToastCustom(ToastCustom.ERROR, "Erro com a localização!", this.context as RegisterMarketplaceActivity)
            toast.getToast().show()
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

    fun registerMarket (view: View) {
        if (this.inputNameRegisterMarket.text.isEmpty() || this.inputStreetRegisterMarket.text.isEmpty() || this.inputLatitudeRegisterMarket.text.isEmpty() || this.inputLongitudeRegisterMarket.text.isEmpty() || this.inputDistrictRegisterMarket.text.isEmpty() || this.inputCityRegisterMarket.text.isEmpty() || this.inputCepRegisterMarket.text.isEmpty() || this.inputNumberRegisterMarket.text.isEmpty()) {
            val toast = ToastCustom(ToastCustom.WARNING, "Por favor, preencha todos os campos!", this.context as RegisterMarketplaceActivity)
            toast.getToast().show()
        } else {
            var market = MarketplaceDTO(uuid4().toString(), this.userLoged.id.toString(), this.inputNameRegisterMarket.text.toString(), this.inputStreetRegisterMarket.text.toString(), this.inputLatitudeRegisterMarket.text.toString().toDouble(), this.inputLongitudeRegisterMarket.text.toString().toDouble(), this.inputDistrictRegisterMarket.text.toString(), this.inputCityRegisterMarket.text.toString(), this.inputCepRegisterMarket.text.toString().toInt(), this.inputNumberRegisterMarket.text.toString().toInt())

            this.marketplaceService.register(market) { resultSuccessful ->
                if (resultSuccessful) {
                    val intentHome = Intent(this.context as RegisterMarketplaceActivity, ListMarketplacesActivity::class.java)
                    startActivity(intentHome)
                } else {
                    var toast = ToastCustom(ToastCustom.ERROR, "Falha ao cadastrar mercado!", this.context as RegisterMarketplaceActivity)
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