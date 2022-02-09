package com.ufc.mobile.onlist.ui.maps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.data.LocalizationData
import com.ufc.mobile.onlist.model.Marketplace
import com.ufc.mobile.onlist.model.User
import com.ufc.mobile.onlist.services.AuthUserService
import com.ufc.mobile.onlist.services.MarketplaceService
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.lists.*
import com.ufc.mobile.onlist.ui.updaters.UpdateUserActivity
import com.ufc.mobile.onlist.util.ToastCustom
import java.io.FileInputStream
import java.io.ObjectInputStream

class MapActivity: AppCompatActivity(), OnMapReadyCallback {
    private var context: Context = this
    private lateinit var map: GoogleMap
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var marketplaceService: MarketplaceService
    private lateinit var listMarketplaces: ArrayList<Marketplace>
    private lateinit var userLoged: User
    private lateinit var localization: LocalizationData
    private lateinit var client: FusedLocationProviderClient
    private lateinit var authUserService: AuthUserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        this.authUserService = AuthUserService()
        this.marketplaceService = MarketplaceService()
        this.listMarketplaces = ArrayList()
        this.localization = LocalizationData(-4.979087, -39.056499)
        this.client = LocationServices.getFusedLocationProviderClient(this.context as MapActivity)

        this.getUserLoged()
        this.populateMarketList()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutMapActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Mapa")
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
                this.localization = LocalizationData(location.latitude, location.longitude)
            } else {
                this.localization = LocalizationData(-4.979087, -39.056499)
            }
        }.addOnFailureListener { error ->
            var toast = ToastCustom(ToastCustom.ERROR, "Erro com a localização!", this.context as MapActivity)
            toast.getToast().show()
        }
    }

    private fun populateMarketList() {
        this.marketplaceService.listAllMarketsByIdUser(this.userLoged.id.toString()) { marketList, result ->
            if (result) {
                for (market in marketList) {
                    this.listMarketplaces.add(market)
                }
            } else {
                var toast = ToastCustom(ToastCustom.WARNING, "Falha ao carregar os mercados no mapa!", this.context as MapActivity)
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

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setOnMapLoadedCallback {
            for (market in this.listMarketplaces) {
                val localizationMarket = LatLng(market.latitude, market.longitude)

                map.addMarker(MarkerOptions()
                    .position(localizationMarket)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .title(market.name))

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizationMarket, 17F))
            }

            val localizationUser = LatLng(this.localization.latitude, this.localization.longitude)

            map.addMarker(MarkerOptions()
                .position(localizationUser)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .title(this.userLoged.name))

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizationUser, 17F))
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