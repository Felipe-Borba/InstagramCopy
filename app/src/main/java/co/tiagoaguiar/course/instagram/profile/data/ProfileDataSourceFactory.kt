package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.Cache
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.UserAuth

class ProfileDataSourceFactory(
    private val profileCache: Cache<UserAuth>,
    private val postsCache: Cache<List<Post>>
) {
    fun createLocalDataSource(): ProfileDataSource {
        return ProfileLocalDataSource(profileCache, postsCache)
    }

    fun createFromUser(uuid: String?): ProfileDataSource {
        if (uuid != null) {
            return ProfileFakeRemoteDataSource() //TODO shouldn't this be dependency injected?
        }

        if (profileCache.isCached()) {
            return ProfileLocalDataSource(profileCache, postsCache)
        }

        return ProfileFakeRemoteDataSource() //TODO shouldn't this be dependency injected?
    }

    fun createFromPosts(uuid: String?): ProfileDataSource {
        if (uuid != null) {
            return ProfileFakeRemoteDataSource() //TODO shouldn't this be dependency injected?
        }

        if (postsCache.isCached()) {
            return ProfileLocalDataSource(profileCache, postsCache)
        }

        return ProfileFakeRemoteDataSource() //TODO shouldn't this be dependency injected?
    }
}