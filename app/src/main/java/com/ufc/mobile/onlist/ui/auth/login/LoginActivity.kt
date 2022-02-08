package com.ufc.mobile.onlist.ui.auth.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.dto.AuthDTO
import com.ufc.mobile.onlist.services.AuthUserService
import com.ufc.mobile.onlist.ui.auth.register.RegisterUserActivity
import com.ufc.mobile.onlist.ui.lists.ListMarketplacesActivity
import com.ufc.mobile.onlist.util.ToastCustom

class LoginActivity : AppCompatActivity() {
    private var context: Context = this
    private lateinit var authUserService: AuthUserService

    private lateinit var inputEmailUserLogin: EditText
    private lateinit var inputPasswordUserLogin: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setTitle("OnList - Login")

        this.authUserService = AuthUserService()

        this.inputEmailUserLogin = findViewById(R.id.inputEmailFormLogin)
        this.inputPasswordUserLogin = findViewById(R.id.inputPasswordFormLogin)
    }

    fun clickLogin (view: View) {
        if (this.inputEmailUserLogin.text.isEmpty() || this.inputEmailUserLogin.text.isEmpty()) {
            val toast = ToastCustom(ToastCustom.WARNING, "Por favor, preencha todos os campos!", this.context as LoginActivity)
            toast.getToast().show()
        } else {
            var login = AuthDTO(this.inputEmailUserLogin.text.toString(), this.inputPasswordUserLogin.text.toString())

            this.authUserService.login(login, this.context as LoginActivity) { resultSuccessful ->
                if (resultSuccessful) {
                    val intentHome = Intent(this.context as LoginActivity, ListMarketplacesActivity::class.java)
                    startActivity(intentHome)
                } else {
                    var toast = ToastCustom(ToastCustom.ERROR, "Falha no login!", this.context as LoginActivity)
                    toast.getToast().show()
                }
            }
        }
    }

    fun clickLinkRegisterUser (view: View) {
        val intentRegisterUser = Intent(this, RegisterUserActivity::class.java)
        startActivity(intentRegisterUser)
    }
}