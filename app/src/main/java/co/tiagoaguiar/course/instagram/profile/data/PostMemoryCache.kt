package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.Cache
import co.tiagoaguiar.course.instagram.common.model.Post

object PostMemoryCache : Cache<List<Post>> {
    private var post: List<Post>? = null

    override fun isCached(): Boolean {
        return post != null
    }

    override fun get(key: String): List<Post>? {
        return post
    }

    override fun put(data: List<Post>?) {
        post = data
    }
}