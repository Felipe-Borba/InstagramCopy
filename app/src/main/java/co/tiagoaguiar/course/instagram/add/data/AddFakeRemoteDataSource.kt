package co.tiagoaguiar.course.instagram.add.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.base.RequestCallback
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post
import java.util.UUID

class AddFakeRemoteDataSource : AddDataSource {
    override fun createPost(userUUID: String, uri: Uri, caption: String, callback: RequestCallback<Boolean>) {
        Handler(Looper.getMainLooper()).postDelayed({
            var posts = Database.posts[userUUID]

            if (posts == null) {
                posts = mutableSetOf()
                Database.posts[userUUID] = posts
            }

            val post = Post(
                uuid = UUID.randomUUID().toString(),
                uri = uri,
                caption = caption,
                timestamp = System.currentTimeMillis(),
                publisher = Database.sessionAuth!!
            )
            posts.add(post)

            var followers = Database.followers[userUUID]

            if (followers == null) {
                followers = mutableSetOf()
                Database.followers[userUUID] = followers
            } else {
                for (follower in followers) {
                    Database.feeds[follower]?.add(post)
                }
                Database.feeds[userUUID]?.add(post)
            }

            callback.onSuccess(true)
            callback.onComplete()
        }, 1000)
    }
}