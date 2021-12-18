package com.ufc.mobile.onlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ufc.mobile.onlist.util.ToastCustom

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.buttonActionLoginForm).setOnClickListener {
            val toastCustom = ToastCustom(ToastCustom.SUCCESS, "O Toast funcionou caraiu", this)
            toastCustom.getToast().show()
        }
    }
}