package co.tiagoaguiar.course.instagram.register.data

import android.os.Handler
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import java.util.UUID

class FakeRegisterDataSource : RegisterDataSource {
    override fun validate(email: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull() {
                email == it.email
            }

            if (userAuth == null) {
                callback.onSuccess()
            } else {
                callback.onFailure("Usuário já cadastrado")
            }


            callback.onComplete()
        }, 2000)
    }

    override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull() {
                email == it.email
            }

            if (userAuth != null) {
                callback.onFailure("Usuário já cadastrado")
            } else {
                val user = UserAuth(
                    uuid = UUID.randomUUID().toString(),
                    name = name,
                    email = email,
                    password = password
                )

                val created = Database.usersAuth.add(user)

                if (created) {
                    Database.sessionAuth = user
                    callback.onSuccess()
                } else {
                    callback.onFailure("Erro interno no servidor.")
                }
            }


            callback.onComplete()
        }, 2000)
    }
}
