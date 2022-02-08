package com.ufc.mobile.onlist.services

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ufc.mobile.onlist.dto.ProductDTO
import com.ufc.mobile.onlist.model.Product

class ProductService {
    var auth = Firebase.auth
    var database = Firebase.firestore

    fun listAllProductByMarket(marketId: String, setResultSuccessful: (products: ArrayList<Product>, result: Boolean) -> Unit) {
        val productsList: ArrayList<Product> = ArrayList()

        this.database.collection("products").whereEqualTo("idMarket", marketId).get().addOnSuccessListener { products ->
            for (document in products) {
                var product = Product(document.data["id"].toString(), document.data["name"].toString(), document.data["price"].toString().toDouble())

                productsList.add(product)
            }

            setResultSuccessful(productsList, true)
        }.addOnFailureListener { error ->
            setResultSuccessful(productsList, false)
        }
    }

    fun create(product: ProductDTO, setResultSuccessful: (setResultSuccessful: Boolean) -> Unit) {
        this.database.collection("products").add(product).addOnSuccessListener { documentReference ->
            setResultSuccessful(true)
        }.addOnFailureListener { error ->
            setResultSuccessful(false)
        }
    }
}