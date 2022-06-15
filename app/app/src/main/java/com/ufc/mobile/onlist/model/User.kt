package com.ufc.mobile.onlist.model

import java.io.Serializable

class User(id: String? = null, name: String, email: String, password: String, telephone: String, initials: String): Serializable {
    val id = id
    val email = email
    val name = name
    val password = password
    val telephone = telephone
    val initials = initials
}
