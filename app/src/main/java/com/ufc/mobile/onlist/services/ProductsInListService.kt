package com.ufc.mobile.onlist.services

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ufc.mobile.onlist.dto.AddProductInListDTO
import com.ufc.mobile.onlist.model.Product
import com.ufc.mobile.onlist.model.ProductInList

class ProductsInListService {
    var auth = Firebase.auth
    var database = Firebase.firestore
    private val productsInListCollectionReference = Firebase.firestore.collection("productsInList")

    fun listAllProductsByList (listId: String, setResultSuccessful: (productList: ArrayList<ProductInList>, result: Boolean) -> Unit) {
        val listProductsInList: ArrayList<ProductInList> = ArrayList()

        this.database.collection("productsInList").whereEqualTo("listId", listId).get().addOnSuccessListener { products ->
            for (document in products) {
                var product = ProductInList(document.data["id"].toString(), document.data["listId"].toString(), document.data["name"].toString(), document.data["price"].toString().toDouble(), document.data["buied"].toString().toBoolean())

                listProductsInList.add(product)
            }

            setResultSuccessful(listProductsInList, true)
        }.addOnFailureListener { error ->
            setResultSuccessful(listProductsInList, false)
        }
    }

    fun addProductInList (addProduct: AddProductInListDTO, setResultSuccessful: (setResultSuccessful: Boolean) -> Unit) {
        this.database.collection("productsInList").add(addProduct).addOnSuccessListener { documentReference ->
            setResultSuccessful(true)
        }.addOnFailureListener { error ->
            setResultSuccessful(false)
        }
    }

    fun updateProductBuied (productBuied: ProductInList, setResultSuccessful: (setResultSuccessful: Boolean) -> Unit) {
        val mapedProductBuied = mutableMapOf<String, Any>()

        mapedProductBuied["id"] = productBuied.id
        mapedProductBuied["listId"] = productBuied.listId
        mapedProductBuied["name"] = productBuied.name
        mapedProductBuied["price"] = productBuied.price

        mapedProductBuied["buied"] = !productBuied.buied

        this.productsInListCollectionReference.whereEqualTo("id", productBuied.id).get().addOnSuccessListener { document ->
            for (product in document) {
                this.productsInListCollectionReference.document(product.id).set(mapedProductBuied, SetOptions.merge()).addOnSuccessListener { result ->
                    setResultSuccessful(true)
                }.addOnFailureListener { error ->
                    setResultSuccessful(false)
                }
            }
        }.addOnFailureListener { error ->
            setResultSuccessful(false)
        }
    }
}