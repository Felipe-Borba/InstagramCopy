package co.tiagoaguiar.course.instagram.profile.data

import android.os.Handler
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.base.RequestCallback
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import java.lang.RuntimeException

class FireProfileRemoteDataSource : ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<User, Boolean?>>) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .document(userUUID)
            .get()
            .addOnSuccessListener { res ->
                val user = res.toObject(User::class.java)
                if (user == null) {
                    callback.onFailure("Usuário não encontrado")
                } else if (user.uuid == FirebaseAuth.getInstance().uid) {
                    callback.onSuccess(Pair(user, null))
                } else {
                    FirebaseFirestore.getInstance()
                        .collection("/followers")
                        .document(userUUID)
                        .get()
                        .addOnSuccessListener { response ->
                            if (!response.exists()) {
                                callback.onSuccess(Pair(user, false))
                            } else {
                                val list = response.get("followers") as List<String>
                                callback.onSuccess(Pair(user, list.contains(FirebaseAuth.getInstance().uid)))
                            }
                        }
                        .addOnFailureListener { e ->
                            callback.onFailure(e.message ?: "Falha ao buscar os seguidores")
                        }
                        .addOnCompleteListener {
                            callback.onComplete()
                        }
                }
            }
            .addOnFailureListener { e ->
                callback.onFailure(e.message ?: "Falha ao buscar o usuário")
            }
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        FirebaseFirestore.getInstance()
            .collection("posts")
            .document(userUUID)
            .collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { res ->
                val posts = mutableListOf<Post>()
                for (document in res.documents) {
                    val post = document.toObject(Post::class.java)
                    post?.let {
                        posts.add(it)
                    }
                }
                callback.onSuccess(posts)
            }
            .addOnFailureListener { e ->
                callback.onFailure(e.message ?: "Falha ao buscar posts")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun followUser(userUUID: String, follow: Boolean, callback: RequestCallback<Boolean>) {
        val uid = FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuário não logado")

        FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(userUUID)
            .update("followers", if (follow) FieldValue.arrayUnion(uid) else FieldValue.arrayRemove(uid))
            .addOnSuccessListener { res ->
                callback.onSuccess(true)
            }
            .addOnFailureListener { e ->
                val err = e as? FirebaseFirestoreException
                if (err?.code == FirebaseFirestoreException.Code.NOT_FOUND) {
                    FirebaseFirestore.getInstance()
                        .collection("/followers")
                        .document(userUUID)
                        .set(
                            hashMapOf(
                                "followers" to listOf(uid)
                            )
                        )
                        .addOnSuccessListener { res ->
                            callback.onSuccess(true)
                        }
                        .addOnFailureListener { e ->
                            callback.onFailure(e.message ?: "Falha ao criar seguidor")
                        }

                } else {
                    callback.onFailure(e.message ?: "Falha ao atualizar seguidor")
                }
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }
}