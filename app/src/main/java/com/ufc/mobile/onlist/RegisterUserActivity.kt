package com.ufc.mobile.onlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class RegisterUserActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
    }

    fun clickRegister (view: View) {
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }

    fun clickLinkLoginUser (view: View) {
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }
}