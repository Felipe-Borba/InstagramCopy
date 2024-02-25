package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.model.UserAuth

object ProfileMemoryCache : ProfileCache<UserAuth> {
    private var userAuth: UserAuth? = null

    override fun isCached(): Boolean {
        return userAuth != null
    }

    override fun get(key: String): UserAuth? {
        return if (userAuth?.uuid == key) userAuth else null
    }

    override fun put(data: UserAuth) {
        userAuth = data
    }
}