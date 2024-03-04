package co.tiagoaguiar.course.instagram.search.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallback
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FireSearchRemoteDataSource : SearchDataSource {

    override fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .whereGreaterThanOrEqualTo("name", name)
            .whereLessThanOrEqualTo("name", name + "\uf8ff")
            .whereNotEqualTo("uuid", FirebaseAuth.getInstance().uid)
            .get()
            .addOnSuccessListener { res ->
                val users = mutableListOf<User>()

                for (document in res.documents) {
                    val user = document.toObject(User::class.java)

                    user?.let {
                        users.add(it)
                    }
                }

                callback.onSuccess(users)
            }
            .addOnFailureListener { e ->
                callback.onFailure(e.message ?: "Erro ao buscar usuários")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }

    }
}