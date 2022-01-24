package com.ufc.mobile.onlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ufc.mobile.onlist.util.ToastCustom

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickLogin (view: View) {
        val intentHome = Intent(this, ListMarketplacesActivity::class.java)
        startActivity(intentHome)
    }


    fun clickLinkRegisterUser (view: View) {
        val intentRegisterUser = Intent(this, RegisterUserActivity::class.java)
        startActivity(intentRegisterUser)
    }
}