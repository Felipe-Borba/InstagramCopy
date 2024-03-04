package co.tiagoaguiar.course.instagram.profile.data

import android.os.Handler
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.base.RequestCallback
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

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
                        .document(FirebaseAuth.getInstance().uid!!)
                        .collection("followers")
                        .whereEqualTo("uuid", userUUID)
                        .get()
                        .addOnSuccessListener { response ->
                            callback.onSuccess(Pair(user, !response.isEmpty))
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
        //TODO
    }
}