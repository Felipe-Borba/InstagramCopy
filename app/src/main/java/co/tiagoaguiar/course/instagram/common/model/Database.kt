package co.tiagoaguiar.course.instagram.common.model

import java.util.UUID

object Database {
    val usersAuth = hashSetOf<UserAuth>()
    val photos = hashSetOf<Photo>()
    val posts = hashMapOf<String, MutableSet<Post>>()
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, MutableSet<String>>()

    var sessionAuth: UserAuth? = null

    init {
        val userA = UserAuth(
            uuid = UUID.randomUUID().toString(),
            email = "userA@email.com",
            password = "12345678",
            name = "userA"
        )
        val userB = UserAuth(
            uuid = UUID.randomUUID().toString(),
            email = "userB@email.com",
            password = "87654321",
            name = "userB"
        )

        usersAuth.add(userA)
        followers[userA.uuid] = hashSetOf()
        posts[userA.uuid] = hashSetOf()
        feeds[userA.uuid] = hashSetOf()

        usersAuth.add(userB)
        followers[userB.uuid] = hashSetOf()
        posts[userB.uuid] = hashSetOf()
        feeds[userB.uuid] = hashSetOf()

        sessionAuth = usersAuth.first()
    }
}