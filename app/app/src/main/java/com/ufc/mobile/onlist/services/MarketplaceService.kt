package com.ufc.mobile.onlist.services

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ufc.mobile.onlist.dto.MarketplaceDTO
import com.ufc.mobile.onlist.model.Marketplace

class MarketplaceService {
    var auth = Firebase.auth
    var database = Firebase.firestore

    fun listAllMarketsByIdUser(userId: String, setResultSuccessful: (markets: ArrayList<Marketplace>, result: Boolean) -> Unit) {
        val marketplaceList: ArrayList<Marketplace> = ArrayList()

        this.database.collection("marketplaces").whereEqualTo("userId", userId).get().addOnSuccessListener { markets ->
            for (document in markets) {
                var marketplace = Marketplace(document.data["id"].toString(), document.data["userId"].toString(), document.data["name"].toString(), document.data["street"].toString(), document.data["latitude"].toString().toDouble(), document.data["longitude"].toString().toDouble(), document.data["district"].toString(), document.data["city"].toString(), document.data["cep"].toString().toInt(), document.data["number"].toString().toInt())

                marketplaceList.add(marketplace)
            }

            setResultSuccessful(marketplaceList, true)
        }.addOnFailureListener { error ->
            setResultSuccessful(marketplaceList, false)
        }
    }

    fun register(market: MarketplaceDTO, setResultSuccessful: (setResultSuccessful: Boolean) -> Unit) {
        this.database.collection("marketplaces").add(market)
            .addOnSuccessListener { documentReference ->
                setResultSuccessful(true)
            }.addOnFailureListener { error ->
            setResultSuccessful(false)
        }
    }
}