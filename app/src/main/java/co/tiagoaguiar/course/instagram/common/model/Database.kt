package co.tiagoaguiar.course.instagram.common.model

import java.util.UUID

object Database {
    val usersAuth = hashSetOf<UserAuth>()

    var sessionAuth: UserAuth? = null

    init {
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "userA@email.com", "12345678"))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "userB@email.com", "87654321"))
    }
}