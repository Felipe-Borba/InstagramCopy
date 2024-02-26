package co.tiagoaguiar.course.instagram.common.model

import java.util.UUID

object Database {
    val usersAuth = hashSetOf<UserAuth>()
    val photos = hashSetOf<Photo>()
    val posts = hashMapOf<String, Set<Post>>()
    val feeds = hashMapOf<String, Set<Post>>()

    var sessionAuth: UserAuth? = null

    init {
        usersAuth.add(
            UserAuth(
                uuid = UUID.randomUUID().toString(),
                email = "userA@email.com",
                password = "12345678",
                name = "userA"
            )
        )
        usersAuth.add(
            UserAuth(
                uuid = UUID.randomUUID().toString(),
                email = "userB@email.com",
                password = "87654321",
                name = "userB"
            )
        )
        sessionAuth = usersAuth.first()
    }
}