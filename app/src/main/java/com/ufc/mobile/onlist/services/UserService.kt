package com.ufc.mobile.onlist.services

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ufc.mobile.onlist.model.User
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class UserService {
    var auth = Firebase.auth
    var database = Firebase.firestore
    private val userCollectionReference = Firebase.firestore.collection("users")

    fun listAllUsers(setResultSuccessful: (usersList: ArrayList<User> , result: Boolean) -> Unit) {
        var usersList: ArrayList<User> = ArrayList()

        this.database.collection("users").get().addOnSuccessListener { users ->
            for (document in users) {
                var user = User(document.data["id"] as String, document.data["name"] as String, document.data["email"] as String, document.data["password"] as String, document.data["telephone"] as String, document.data["initials"] as String)

                usersList.add(user)
            }

            setResultSuccessful(usersList, true)
        }.addOnFailureListener { error ->
            setResultSuccessful(usersList, false)
        }
    }

    fun update(userUpdated: User, context: AppCompatActivity, setResultSuccessful: (setResultSuccessful: Boolean) -> Unit) {
        val mapedUserUpdated = mutableMapOf<String, Any>()

        mapedUserUpdated["id"] = userUpdated.id.toString()
        mapedUserUpdated["name"] = userUpdated.name
        mapedUserUpdated["email"] = userUpdated.email
        mapedUserUpdated["password"] = userUpdated.password
        mapedUserUpdated["telephone"] = userUpdated.telephone
        mapedUserUpdated["initials"] = userUpdated.initials

        this.userCollectionReference.whereEqualTo("id", userUpdated.id).get().addOnSuccessListener { document ->
            for (user in document) {
                this.userCollectionReference.document(user.id).set(mapedUserUpdated, SetOptions.merge()).addOnSuccessListener { result ->
                    val userUpdate = User(mapedUserUpdated["id"] as String, mapedUserUpdated["name"] as String, mapedUserUpdated["email"] as String, mapedUserUpdated["password"] as String, mapedUserUpdated["telephone"] as String, mapedUserUpdated["initials"] as String)

                    val fileName = "user_loged"
                    val file = context.getFileStreamPath(fileName)
                    val fileOutputStream = FileOutputStream(file)
                    val objectOutputStream = ObjectOutputStream(fileOutputStream)

                    objectOutputStream.writeObject(userUpdate)
                    objectOutputStream.close()
                    fileOutputStream.close()

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