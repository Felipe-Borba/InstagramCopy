package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.Cache
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth

object ProfileMemoryCache : Cache<Pair<User, Boolean?>> {
    private var userAuth: Pair<User, Boolean?>? = null

    override fun isCached(): Boolean {
        return userAuth != null
    }

    override fun get(key: String): Pair<User, Boolean?>? {
        return if (userAuth?.first?.uuid == key) userAuth else null
    }

    override fun put(data: Pair<User, Boolean?>?) {
        userAuth = data
    }
}