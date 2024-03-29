package co.tiagoaguiar.course.instagram.register.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.Data
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class FireRegisterDataSource : RegisterDataSource {
    override fun validate(email: String, callback: RegisterCallback) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    callback.onSuccess()
                } else {
                    callback.onFailure("Usuário já cadastrado")
                }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro interno no servidor")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid

                if (uid == null) {
                    callback.onFailure("Erro interno no servidor")
                    return@addOnSuccessListener
                }

                FirebaseFirestore.getInstance()
                    .collection("/users")
                    .document(uid)
                    .set(
                        hashMapOf(
                            "uuid" to uid,
                            "name" to name,
                            "email" to email,
                            "followers" to 0,
                            "following" to 0,
                            "postCount" to 0,
                            "photoUrl" to null,
                        )
                    )
                    .addOnSuccessListener {
                        callback.onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        callback.onFailure(exception.message ?: "Erro interno no servidor")
                    }
                    .addOnCompleteListener {
                        callback.onComplete()
                    }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro interno no servidor")
            }
    }

    override fun updateUser(photoUri: Uri, callback: RegisterCallback) {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null || photoUri.lastPathSegment == null) {
            callback.onFailure("Usuário não encontrado")
            return
        }

        val storageRef = FirebaseStorage.getInstance().reference
        val imgRef = storageRef.child("images/")
            .child(uid)
            .child(photoUri.lastPathSegment!!)

        imgRef.putFile(photoUri)
            .addOnSuccessListener { result ->
                imgRef.downloadUrl
                    .addOnSuccessListener { res ->
                        val userRef = FirebaseFirestore.getInstance().collection("/users").document(uid)
                        userRef.get()
                            .addOnSuccessListener { document ->
                                val user = document.toObject(User::class.java)
                                val newUser = user?.copy(photoUrl = res.toString())

                                if (newUser != null) {
                                    userRef.set(newUser)
                                        .addOnSuccessListener {
                                            callback.onSuccess()
                                        }
                                        .addOnFailureListener { exception ->
                                            callback.onFailure(exception.message ?: "Erro interno no servidor")
                                        }
                                        .addOnCompleteListener {
                                            callback.onComplete()
                                        }
                                } else {
                                    callback.onFailure("Erro interno no servidor")
                                }
                            }
                    }
                    .addOnFailureListener { exception ->
                        callback.onFailure(exception.message ?: "Erro interno no servidor")
                    }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro interno no servidor")
            }
    }
}
