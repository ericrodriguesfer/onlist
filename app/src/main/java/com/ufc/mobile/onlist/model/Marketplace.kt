package com.ufc.mobile.onlist.model

import java.io.Serializable

class Marketplace(id: String, userId: String, name: String, street: String, latitude: Double, longitude: Double, district: String, city: String, cep: Int, number: Int): Serializable {
    val id = id
    val userId = userId
    val name = name
    val street= street
    val latitude = latitude
    val longitude = longitude
    val district = district
    val city = city
    val cep = cep
    val number = number
}