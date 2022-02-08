package com.ufc.mobile.onlist.services

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ufc.mobile.onlist.dto.AuthDTO
import com.ufc.mobile.onlist.dto.UserDTO
import com.ufc.mobile.onlist.model.User
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class AuthUserService {
    var auth = Firebase.auth
    var database = Firebase.firestore

    fun login(auth: AuthDTO, context: AppCompatActivity, setResultSuccessful: (setResultSuccessful: Boolean) -> Unit) {
        this.auth.signInWithEmailAndPassword(auth.email, auth.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userLogin = this.auth.currentUser

                this.database.collection("users").whereEqualTo("email", userLogin?.email).get().addOnCompleteListener { result ->
                    this.database.collection("users").whereEqualTo("email", this.auth.currentUser?.email).get().addOnSuccessListener { documents ->
                        for (document in documents) {
                            val user = User(document.data["id"] as String, document.data["name"] as String, document.data["email"] as String, document.data["password"] as String, document.data["telephone"] as String, document.data["initials"] as String)

                            val fileName = "user_loged"
                            val file = context.getFileStreamPath(fileName)
                            val fileOutputStream = FileOutputStream(file)
                            val objectOutputStream = ObjectOutputStream(fileOutputStream)

                            objectOutputStream.writeObject(user)
                            objectOutputStream.close()
                            fileOutputStream.close()
                        }
                        setResultSuccessful(true)
                    }
                }.addOnFailureListener { error ->
                    setResultSuccessful(false)
                }
            } else {
                setResultSuccessful(false)
            }
        }.addOnFailureListener { error ->
            setResultSuccessful(false)
        }
    }

    fun register(createUser: UserDTO, setResultSuccessful: (setResultSuccessful: Boolean) -> Unit) {
        this.auth.createUserWithEmailAndPassword(createUser.email, createUser.password).addOnCompleteListener {task: Task<AuthResult> ->
            if (task.isSuccessful) {
                val user: User = User(auth.currentUser?.uid.toString(), createUser.name, createUser.email, createUser.password, createUser.telephone, createUser.initials)

                database.collection("users").add(user).addOnSuccessListener { documentReference ->
                    setResultSuccessful(true)
                }
                .addOnFailureListener { error ->
                    setResultSuccessful(false)
                }
            } else {
                setResultSuccessful(false)
            }
        }
    }

    fun logout() {
        this.auth.signOut();
    }

}