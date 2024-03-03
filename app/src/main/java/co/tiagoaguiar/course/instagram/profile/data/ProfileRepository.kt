package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallback
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.UserAuth

class ProfileRepository(private val dataSourceFactory: ProfileDataSourceFactory) {
    fun fetchUserProfile(uuid: String?, callback: RequestCallback<UserAuth>) {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val userId = uuid ?: localDataSource.fetchSession().uuid
        val dataSource = dataSourceFactory.createFromUser(uuid)

        dataSource.fetchUserProfile(userId, object : RequestCallback<UserAuth> {
            override fun onSuccess(data: UserAuth) {
                if (uuid == null) {
                    localDataSource.putUser(data)
                }
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

            override fun onComplete() {
                callback.onComplete()
            }
        })
    }

    fun fetchUserPosts(uuid: String?, callback: RequestCallback<List<Post>>) {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val userId = uuid ?: localDataSource.fetchSession().uuid
        val dataSource = dataSourceFactory.createFromPosts(uuid)

        dataSource.fetchUserPosts(userId, object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                if (uuid == null) {
                    localDataSource.putPosts(data)
                }
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

            override fun onComplete() {
                callback.onComplete()
            }
        })
    }

    fun clearCache() {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        localDataSource.putPosts(null)
    }
}