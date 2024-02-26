package co.tiagoaguiar.course.instagram.home.data

import android.os.Handler
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.base.RequestCallback
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post

class HomeFakeRemoteDataSource : HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val feeds = Database.feeds[userUUID]

            callback.onSuccess(feeds?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)
    }
}