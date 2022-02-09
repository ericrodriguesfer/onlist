package com.ufc.mobile.onlist.ui.updaters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.model.User
import com.ufc.mobile.onlist.services.AuthUserService
import com.ufc.mobile.onlist.services.UserService
import com.ufc.mobile.onlist.ui.maps.MapActivity
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.ui.auth.register.RegisterUserActivity
import com.ufc.mobile.onlist.ui.lists.*
import com.ufc.mobile.onlist.util.ToastCustom
import java.io.FileInputStream
import java.io.ObjectInputStream

class UpdateUserActivity: AppCompatActivity() {
    private var context: Context = this
    private lateinit var userService: UserService
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var userLoged: User
    private lateinit var inputNameUserUpdate: EditText
    private lateinit var inputEmailUserUpdate: EditText
    private lateinit var inputPasswordUserUpdate: EditText
    private lateinit var inputConfirmPasswordUserUpdate: EditText
    private lateinit var inputTelephoneUserUpdate: EditText
    private lateinit var inputInitialsUserUpdate: EditText
    private lateinit var authUserService: AuthUserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        this.authUserService = AuthUserService()
        this.userService = UserService()
        this.getUserLoged()

        this.inputNameUserUpdate = findViewById(R.id.inputNameFormUpdateUser)
        this.inputEmailUserUpdate = findViewById(R.id.inputEmailFormUpdateUser)
        this.inputPasswordUserUpdate = findViewById(R.id.inputPasswordFormUpdateUser)
        this.inputConfirmPasswordUserUpdate = findViewById(R.id.inputConfirmPasswordFormUpdateUser)
        this.inputTelephoneUserUpdate = findViewById(R.id.inputTelephoneFormUpdateUser)
        this.inputInitialsUserUpdate = findViewById(R.id.inputInitialsFormUpdateUser)

        this.getDataUserLoged()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutUpdateUserActivity)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Edição De Perfil")
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

    fun clickUpdateUser (view: View) {
        if (this.inputNameUserUpdate.text.isEmpty() || this.inputEmailUserUpdate.text.isEmpty() || this.inputPasswordUserUpdate.text.isEmpty() || this.inputConfirmPasswordUserUpdate.text.isEmpty() || this.inputTelephoneUserUpdate.text.isEmpty() || this.inputInitialsUserUpdate.text.isEmpty()) {
            val toast = ToastCustom(ToastCustom.WARNING, "Por favor, preencha todos os campos!", this.context as UpdateUserActivity)
            toast.getToast().show()
        } else {
            if (this.inputPasswordUserUpdate.text.toString() != this.inputConfirmPasswordUserUpdate.text.toString()) {
                val toast = ToastCustom(ToastCustom.ERROR, "As senhas precisam ser iguais", this.context as UpdateUserActivity)
                toast.getToast().show()
            } else {
                var userUpdated = User(this.userLoged.id, this.inputNameUserUpdate.text.toString(), this.inputEmailUserUpdate.text.toString(), this.inputPasswordUserUpdate.text.toString(), this.inputTelephoneUserUpdate.text.toString(), this.inputInitialsUserUpdate.text.toString())

                this.userService.update(userUpdated, this.context as UpdateUserActivity) { setResultSuccessful ->
                    if (setResultSuccessful) {
                        val toast = ToastCustom(ToastCustom.SUCCESS, "Usuário atualizado com sucesso", this.context as UpdateUserActivity)
                        toast.getToast().show()

                        val intentListMarkets = Intent(this.context as UpdateUserActivity, ListMarketplacesActivity::class.java)
                        startActivity(intentListMarkets)
                    } else {
                        val toast = ToastCustom(ToastCustom.ERROR, "Erro ao atualizar usuário", this.context as UpdateUserActivity)
                        toast.getToast().show()
                    }
                }
            }
        }
    }

    private fun getDataUserLoged() {
        this.inputNameUserUpdate.setText(this.userLoged.name)
        this.inputEmailUserUpdate.setText(this.userLoged.email)
        this.inputPasswordUserUpdate.setText(this.userLoged.password)
        this.inputConfirmPasswordUserUpdate.setText(this.userLoged.password)
        this.inputTelephoneUserUpdate.setText(this.userLoged.telephone)
        this.inputInitialsUserUpdate.setText(this.userLoged.initials)
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