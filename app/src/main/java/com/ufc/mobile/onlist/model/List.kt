package com.ufc.mobile.onlist.model

import java.io.Serializable

class List(id: String, idMarket: String, idViewer: String? = null, name: String): Serializable {
    val id = id
    val idMarket = idMarket
    val idViewer = idViewer
    val name = name
}