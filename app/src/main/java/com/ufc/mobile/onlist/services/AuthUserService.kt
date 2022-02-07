package com.ufc.mobile.onlist.services

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ufc.mobile.onlist.dto.UserDTO
import com.ufc.mobile.onlist.model.User

class AuthUserService {
    var auth = Firebase.auth
    var database = Firebase.firestore

    fun register(createUser: UserDTO, setResultSuccessful: (setResultSuccessful: Boolean) -> Unit) {
        println("Aqui service 1")
        println(createUser.email)
        println(createUser.password)

        this.auth.createUserWithEmailAndPassword(createUser.email.toString(), createUser.password.toString()).addOnCompleteListener {task: Task<AuthResult> ->
            println(task)
            println(task.isSuccessful)

            if (task.isSuccessful) {
                val user: User = User(auth.currentUser?.uid.toString(), createUser.name, createUser.email, createUser.password, createUser.telephone, createUser.initials)

                println("Aqui service 2")

                database.collection("users").add(user).addOnSuccessListener { documentReference ->
                    println("Aqui service 2")
                    setResultSuccessful(true)
                }
                .addOnFailureListener { error ->
                    println("Aqui service 3")
                    setResultSuccessful(false)
                }
            } else {
                println("Aqui service 4")
                setResultSuccessful(false)
            }
        }
    }
}