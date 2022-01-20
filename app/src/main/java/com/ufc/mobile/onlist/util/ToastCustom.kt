package com.ufc.mobile.onlist.util

import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ufc.mobile.onlist.R

class ToastCustom {
    private val toast: Toast

    constructor (typeToast: Int, messageToast: String, context: AppCompatActivity) {
        this.toast = Toast(context)

        val view = context.findViewById<ViewGroup>(R.id.container_toast)
        val v = context.layoutInflater.inflate(R.layout.custom_toast, view)

        val imgMessage: ImageView = v.findViewById<ImageView>(R.id.img_toast)
        val textMessage: TextView = v.findViewById<TextView>(R.id.txt_message)

        when (typeToast) {
            SUCCESS -> {
                v.background = ContextCompat.getDrawable(context, R.drawable.toast_success)
                imgMessage.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.icon_check_toast))
                textMessage.setTextColor(context.resources.getColor(R.color.color_success_toast))
            }
            WARNING -> {
                v.background = ContextCompat.getDrawable(context, R.drawable.toast_warning)
                imgMessage.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.icon_warning_toast))
                textMessage.setTextColor(context.resources.getColor(R.color.color_warning_toast))
            }
            ERROR -> {
                v.background = ContextCompat.getDrawable(context, R.drawable.toast_error)
                imgMessage.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.icon_error_toast))
                textMessage.setTextColor(context.resources.getColor(R.color.color_error_toast))
            }
        }

        textMessage.text = messageToast
        toast.view = v
        toast.duration = Toast.LENGTH_SHORT
    }

    fun getToast(): Toast {
        return this.toast
    }

    companion object {
        const val SUCCESS = 1
        const val WARNING = 2
        const val ERROR = 3
    }
}

