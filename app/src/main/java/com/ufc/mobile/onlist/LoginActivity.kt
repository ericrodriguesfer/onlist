package com.ufc.mobile.onlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.buttonActionLoginForm).setOnClickListener { showToast(SUCCESS, "Deu certo caraio, funfou") }
    }

    private fun showToast(type: Int, message: String) {
        val view = findViewById<ViewGroup>(R.id.container_toast)
        val v = layoutInflater.inflate(R.layout.custom_toast, view)

        val imgMessage: ImageView = v.findViewById<ImageView>(R.id.img_toast)
        val textMessage: TextView = v.findViewById<TextView>(R.id.txt_message)

        when (type) {
            SUCCESS -> {
                v.background = ContextCompat.getDrawable(this, R.drawable.toast_success)
                imgMessage.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.icon_check_toast))
                textMessage.setTextColor(resources.getColor(R.color.color_success_toast))
            }
            WARNING -> {
                v.background = ContextCompat.getDrawable(this, R.drawable.toast_warning)
                imgMessage.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.icon_warning_toast))
                textMessage.setTextColor(resources.getColor(R.color.color_warning_toast))
            }
            ERROR -> {
                v.background = ContextCompat.getDrawable(this, R.drawable.toast_error)
                imgMessage.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.icon_error_toast))
                textMessage.setTextColor(resources.getColor(R.color.color_error_toast))
            }
        }

        textMessage.text = message
        val toast = Toast(this)
        toast.view = v
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }

    companion object {
        public const val SUCCESS = 1
        public const val WARNING = 2
        public const val ERROR = 3
    }
}