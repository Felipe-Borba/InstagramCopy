package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallback
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth

class ProfileRepository(private val dataSourceFactory: ProfileDataSourceFactory) {
    fun fetchUserProfile(uuid: String?, callback: RequestCallback<Pair<User, Boolean?>>) {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val userId = uuid ?: localDataSource.fetchSession()
        val dataSource = dataSourceFactory.createFromUser(uuid)

        dataSource.fetchUserProfile(userId, object : RequestCallback<Pair<User, Boolean?>> {
            override fun onSuccess(data: Pair<User, Boolean?>) {
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
        val userId = uuid ?: localDataSource.fetchSession()
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


    fun followUser(userUUID: String?, follow: Boolean, callback: RequestCallback<Boolean>) {
        val dataSource = dataSourceFactory.createRemoteDataSource()
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val userId = userUUID ?: localDataSource.fetchSession()

        dataSource.followUser(userId, follow, object : RequestCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
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
        localDataSource.putUser(null)
    }
}