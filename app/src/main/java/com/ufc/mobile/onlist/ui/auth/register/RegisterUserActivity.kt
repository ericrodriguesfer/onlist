package com.ufc.mobile.onlist.ui.auth.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.dto.UserDTO
import com.ufc.mobile.onlist.services.AuthUserService
import com.ufc.mobile.onlist.ui.auth.login.LoginActivity
import com.ufc.mobile.onlist.util.ToastCustom

class RegisterUserActivity: AppCompatActivity() {
    private var context: Context = this
    lateinit var authUserService: AuthUserService

    private lateinit var inputNameUserRegister: EditText
    private lateinit var inputEmailUserRegister: EditText
    private lateinit var inputPasswordUserRegister: EditText
    private lateinit var inputConfirmPasswordUserRegister: EditText
    private lateinit var inputTelephoneUserRegister: EditText
    private lateinit var inputInitialsUserRegister: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        supportActionBar?.setTitle("OnList - Registro")

        this.authUserService = AuthUserService()

        this.inputNameUserRegister = findViewById(R.id.inputNameFormRegisterUser)
        this.inputEmailUserRegister = findViewById(R.id.inputEmailFormRegisterUser)
        this.inputPasswordUserRegister = findViewById(R.id.inputPasswordFormRegisterUser)
        this.inputConfirmPasswordUserRegister = findViewById(R.id.inputConfirmPasswordFormRegisterUser)
        this.inputTelephoneUserRegister = findViewById(R.id.inputTelephoneFormRegisterUser)
        this.inputInitialsUserRegister = findViewById(R.id.inputInitialsFormRegisterUser)
    }

    fun clickRegister (view: View) {
        if (this.inputNameUserRegister.text.isEmpty() || this.inputEmailUserRegister.text.isEmpty() || this.inputPasswordUserRegister.text.isEmpty() || this.inputConfirmPasswordUserRegister.text.isEmpty() || this.inputTelephoneUserRegister.text.isEmpty() || this.inputInitialsUserRegister.text.isEmpty()) {
            val toast = ToastCustom(ToastCustom.WARNING, "Por favor, preencha todos os campos!", this.context as RegisterUserActivity)
            toast.getToast().show()
        } else {
            if (this.inputPasswordUserRegister.text.toString() != this.inputConfirmPasswordUserRegister.text.toString()) {
                val toast = ToastCustom(ToastCustom.ERROR, "As senhas precisam ser iguais", this.context as RegisterUserActivity)
                toast.getToast().show()
            } else {
                var user = UserDTO(this.inputNameUserRegister.text.toString(), this.inputEmailUserRegister.text.toString(), this.inputPasswordUserRegister.text.toString(), this.inputTelephoneUserRegister.text.toString(), this.inputInitialsUserRegister.text.toString())

                this.authUserService.register(user) { resultSuccessful ->
                    if (resultSuccessful) {
                        var toast = ToastCustom(ToastCustom.SUCCESS, "Usuário registrado com sucesso!", this.context as RegisterUserActivity)
                        toast.getToast().show()

                        val intentLogin = Intent(this.context as RegisterUserActivity, LoginActivity::class.java)
                        startActivity(intentLogin)
                    } else {
                        var toast = ToastCustom(ToastCustom.ERROR, "Erro ao registrar usuário!", this.context as RegisterUserActivity)
                        toast.getToast().show()
                    }
                }
            }
        }
    }

    fun clickLinkLoginUser (view: View) {
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }
}
