package co.tiagoaguiar.course.instagram.common.model

import android.net.Uri
import java.io.File
import java.util.UUID

object Database {
    val usersAuth = mutableListOf<UserAuth>()
    val posts = hashMapOf<String, MutableSet<Post>>()
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, MutableSet<String>>()

    var sessionAuth: UserAuth? = null

    init {
        val userA = UserAuth(
            uuid = UUID.randomUUID().toString(),
            email = "userA@email.com",
            password = "12345678",
            name = "userA",
            photo = Uri.fromFile(
                File("/storage/self/primary/Android/media/co.tiagoaguiar.course.instagram/Instagram/2024-03-02-00-34-03-647.jpg")
            )
        )
        val userB = UserAuth(
            uuid = UUID.randomUUID().toString(),
            email = "userB@email.com",
            password = "87654321",
            name = "userB",
            photo = null
        )

        usersAuth.add(userA)
        followers[userA.uuid] = hashSetOf()
        posts[userA.uuid] = hashSetOf()
        feeds[userA.uuid] = hashSetOf()

        for (i in 0..30) {
            usersAuth.add(UserAuth(UUID.randomUUID().toString(), "User$i", "user$i@gmail.com", "123123123", null))
        }

        usersAuth.add(userB)
        followers[userB.uuid] = hashSetOf()
        posts[userB.uuid] = hashSetOf()
        feeds[userB.uuid] = hashSetOf()

        sessionAuth = userA
    }
}