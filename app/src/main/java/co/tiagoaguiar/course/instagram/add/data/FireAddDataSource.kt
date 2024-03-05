package co.tiagoaguiar.course.instagram.add.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.base.RequestCallback
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.util.UUID

class FireAddDataSource : AddDataSource {
    override fun createPost(userUUID: String, uri: Uri, caption: String, callback: RequestCallback<Boolean>) {
        val uriLastPath = uri.lastPathSegment ?: throw IllegalArgumentException("Invalid img")

        val imgRef = FirebaseStorage.getInstance().reference
            .child("images/")
            .child(userUUID)
            .child(uriLastPath)

        imgRef.putFile(uri)
            .addOnSuccessListener { res ->
                imgRef.downloadUrl
                    .addOnSuccessListener { resDownload ->

                        val meRef = FirebaseFirestore.getInstance()
                            .collection("/users")
                            .document(userUUID)

                        meRef.get()
                            .addOnSuccessListener { resMe ->
                                val me = resMe.toObject(User::class.java)

                                val postRef = FirebaseFirestore.getInstance()
                                    .collection("/posts")
                                    .document(userUUID)
                                    .collection("posts")
                                    .document()

                                val post = Post(
                                    uuid = postRef.id,
                                    photoUrl = resDownload.toString(),
                                    caption = caption,
                                    timestamp = System.currentTimeMillis(),
                                    publisher = me
                                )

                                postRef.set(post)
                                    .addOnSuccessListener { resPost ->
                                        meRef.update("postCount", FieldValue.increment(1))
                                       
                                        FirebaseFirestore.getInstance()
                                            .collection("/feeds")
                                            .document(userUUID)
                                            .collection("posts")
                                            .document(postRef.id)
                                            .set(post)
                                            .addOnSuccessListener { resMyFeed ->

                                                FirebaseFirestore.getInstance()
                                                    .collection("/followers")
                                                    .document(userUUID)
                                                    .get()
                                                    .addOnSuccessListener { resFollowers ->
                                                        if (resFollowers.exists()) {
                                                            val list = resFollowers.get("followers") as List<String>
                                                            for (followerUUID in list) {
                                                                FirebaseFirestore.getInstance()
                                                                    .collection("/feeds")
                                                                    .document(followerUUID)
                                                                    .collection("posts")
                                                                    .document(postRef.id)
                                                                    .set(post)
                                                            }
                                                        }

                                                        callback.onSuccess(true)
                                                    }
                                                    .addOnFailureListener { e ->
                                                        callback.onFailure(
                                                            e.message ?: "Falha ao buscar meus seguidores"
                                                        )
                                                    }
                                                    .addOnCompleteListener {
                                                        callback.onComplete()
                                                    }
                                            }
                                            .addOnFailureListener { e ->
                                                callback.onFailure(e.message ?: "Falha ao inserir no feed")
                                            }

                                    }
                                    .addOnFailureListener { e ->
                                        callback.onFailure(e.message ?: "Falha ao inserir um post")
                                    }
                            }
                            .addOnFailureListener { e ->
                                callback.onFailure(e.message ?: "Falha ao buscar usuário logado")
                            }
                    }
                    .addOnFailureListener { e ->
                        callback.onFailure(e.message ?: "Falha ao baixar a foto")
                    }
            }
            .addOnFailureListener { e ->
                callback.onFailure(e.message ?: "Falha ao subir a foto")
            }
    }
}