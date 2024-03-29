package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallback
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
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
                followingCounter(uid, follow)
                followersCounter(uid)
                updateFeed(userUUID, follow)
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
                            followingCounter(uid, follow)
                            followersCounter(uid)
                            updateFeed(userUUID, follow)
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

    private fun updateFeed(uid: String, follow: Boolean) {
        if (!follow) {
            FirebaseFirestore.getInstance()
                .collection("/feeds")
                .document(FirebaseAuth.getInstance().uid!!)
                .collection("posts")
                .whereEqualTo("publisher.uuid", uid)
                .get()
                .addOnSuccessListener { res ->
                    val documents = res.documents
                    for (document in documents) {
                        document.reference.delete()
                    }
                }
        } else {
            FirebaseFirestore.getInstance()
                .collection("/feeds")
                .document(uid)
                .collection("posts")
                .get()
                .addOnSuccessListener { res ->
                    val posts = res.toObjects(Post::class.java)
                    posts.lastOrNull()?.let {
                        FirebaseFirestore.getInstance()
                            .collection("/feeds")
                            .document(FirebaseAuth.getInstance().uid!!)
                            .collection("posts")
                            .document(it.uuid!!)
                            .set(it)
                    }
                }
        }
    }

    private fun followingCounter(uid: String, isFollow: Boolean) {
        val meRef = FirebaseFirestore.getInstance().collection("/users").document(uid)

        if (isFollow) {
            meRef.update("following", FieldValue.increment(1))
        } else {
            meRef.update("following", FieldValue.increment(-1))
        }
    }

    private fun followersCounter(uid: String) {
        val meRef = FirebaseFirestore.getInstance().collection("/users").document(uid)

        FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(uid)
            .get()
            .addOnSuccessListener { res ->
                if (res.exists()) {
                    val list = res.get("followers") as List<String>
                    meRef.update("followers", list.size)
                }
            }
    }
}