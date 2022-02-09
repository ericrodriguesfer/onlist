package com.ufc.mobile.onlist.services

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ufc.mobile.onlist.dto.ListDTO
import com.ufc.mobile.onlist.model.List

class ListService {
    var auth = Firebase.auth
    var database = Firebase.firestore

    fun listAllLists (marketId: String, setResultSuccessful: (listsList: ArrayList<List>, result: Boolean) -> Unit) {
        var listsList: ArrayList<List> = ArrayList()

        this.database.collection("lists").whereEqualTo("marketId", marketId).get().addOnSuccessListener { lists ->
            for (document in lists) {
                var list = List(document.data["id"].toString(), document.data["idMarket"].toString(), document.data["idViewer"].toString(), document.data["name"].toString())

                listsList.add(list)
            }

            setResultSuccessful(listsList, true)
        }.addOnFailureListener { error ->
            setResultSuccessful(listsList, false)
        }
    }

    fun listAllListToViewer (viewerId: String, setResultSuccessful: (listsList: ArrayList<List>, result: Boolean) -> Unit) {
        var listsList: ArrayList<List> = ArrayList()

        this.database.collection("lists").whereEqualTo("viewerId", viewerId).get().addOnSuccessListener { lists ->
            for (document in lists) {
                var list = List(document.data["id"].toString(), document.data["idMarket"].toString(), document.data["idViewer"].toString(), document.data["name"].toString())

                listsList.add(list)
            }

            setResultSuccessful(listsList, true)
        }.addOnFailureListener { error ->
            setResultSuccessful(listsList, false)
        }
    }

    fun create (listCreate: ListDTO, setResultSuccessful: (setResultSuccessful: Boolean) -> Unit) {
        this.database.collection("lists").add(listCreate).addOnSuccessListener { documentReference ->
            setResultSuccessful(true)
        }.addOnFailureListener { error ->
            setResultSuccessful(false)
        }
    }
}